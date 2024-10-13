package com.example.mymobileapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Устанавливаем задержку для Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Проверяем подключение к интернету
            if (isInternetAvailable()) {
                // Интернет есть - переходим в MainActivity
                val intent = Intent(this, OnboardingActivity::class.java)
                startActivity(intent)
            } else {
                // Интернет отсутствует - переходим в NoConnectionActivity
                val intent = Intent(this, NoConnectionActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 3000)
    }

    // Функция для проверки подключения к интернету
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
