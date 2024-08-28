package com.sunnyweather.android.logic.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place


object PlaceDao {

    //先获取SharedPreferences对象
    private fun sharedpreferences()=SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",MODE_PRIVATE)

    fun savePlace(place:Place){
        sharedpreferences().edit {
            putString("place", Gson().toJson(place))
        }

    }

    fun getSavedPlace():Place{
        val placeJson= sharedpreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved()= sharedpreferences().contains("place")




}