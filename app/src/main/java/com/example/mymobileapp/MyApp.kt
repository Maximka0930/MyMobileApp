package com.example.mymobileapp

import android.app.Application

class MyApp : Application() {
    var userId: Int? = null

    override fun onCreate() {
        super.onCreate()
        // Дополнительная инициализация, если нужно
    }
}
