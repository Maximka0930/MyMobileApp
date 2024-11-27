package com.example.mymobileapp.MainPage.Fragments.BookingСar.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mymobileapp.R
class RegistrationOfTheLease : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registration_of_the_lease) // Используйте соответствующий макет

        val openFragmentButton: Button = findViewById(R.id.openSuccessFragment)
        openFragmentButton.setOnClickListener {
            openNewActivity()
        }
    }

    private fun openNewActivity() {
        val intent = Intent(this, SuccessfulCarBooking::class.java) // Укажите целевую Activity
        startActivity(intent) // Запускаем новую Activity
        finish()
    }



}