package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    //泛型方法，接受一个 Class<T> 类型的参数（服务接口的类），并返回一个 Retrofit 创建的服务接口实例。
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)//?

    // reified 关键字允许在运行时获取泛型的实际类型。
    //T::class.java 是 Kotlin 中获取 T 的 Java Class 对象的方式，然后传递给上面的 create 方法。
    inline fun <reified T> create(): T = create(T::class.java)
}