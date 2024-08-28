package com.sunnyweather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    //刷新天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {

            coroutineScope {//协程作用域
                val deferredRealtime = async {//启动一个新的协程
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {//async 返回的 Deferred 对象提供了一个机制来异步地获取协程的结果，可以在需要时获取结果，而不会阻塞主线程
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }

                val realtimeResponse = deferredRealtime.await()//用来挂起当前协程直到 Deferred 对象所代表的异步操作完成，并返回结果。
                val dailyResponse = deferredDaily.await()

                Log.d("test",realtimeResponse.toString())//有问题
                Log.d("test",dailyResponse.toString())//没问题

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)//有问题
                    Result.success(weather)//返回值
                } else {
                    //返回值
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )

                }
            }//协程作用域结束

    }


    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
    }


    private fun <T> fire(context:CoroutineContext,block:suspend ()->Result<T>)=
        liveData<Result<T>>(context){
            val result=try{
                block()
            }catch (e:Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun savePlace(place:Place)=PlaceDao.savePlace(place)

    fun getSavedPlace()=PlaceDao.getSavedPlace()

    fun isPlaceSaved()=PlaceDao.isPlaceSaved()



































}