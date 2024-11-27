package com.example.mymobileapp.MainPage.Fragments.BookingСar.Activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mymobileapp.MainPage.MainPage
import com.example.mymobileapp.R

class SuccessfulCarBooking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_successful_car_booking)

        // Метод для обработки нажатия кнопки возврата на главную страницу
        findViewById<View>(R.id.OpenHomePage).setOnClickListener {
            navigateToMainPage()
        }

        // Метод для обработки нажатия кнопки возврата на главную страницу
        findViewById<TextView>(R.id.OpenCarReservations).setOnClickListener {
            navigateToCarReservations()
        }

    }

    private fun navigateToMainPage() {
        val intent = Intent(this, MainPage::class.java) // Укажите вашу MainPageActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        //finish() // Завершает текущую Activity
    }

    private fun navigateToCarReservations() {
        val intent = Intent(this, CarReservations::class.java) // Укажите вашу MainPageActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Завершает текущую Activity
    }



}