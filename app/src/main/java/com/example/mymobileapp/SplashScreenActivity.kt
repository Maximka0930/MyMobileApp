package com.example.mymobileapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen) // Убедитесь, что файл разметки существует

        // Устанавливаем задержку для Splash Screen
        Handler().postDelayed({
            // После задержки переходим на MainActivity
            val intent = Intent(this, MainActivity::class.java) // Убедитесь, что MainActivity существует
            startActivity(intent)
            finish() // Закрываем SplashScreenActivity
        }, 3000) // 3000 миллисекунд = 3 секунды
    }
}
