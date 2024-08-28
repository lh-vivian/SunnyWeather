package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class SunnyWeatherApplication :Application() {

    @SuppressLint("StaticFieldLeak")
    companion object{
        //获取全局唯一的context
        lateinit var context:Context

        const val TOKEN="OkcwFTRB6XQcQaRb"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }

}