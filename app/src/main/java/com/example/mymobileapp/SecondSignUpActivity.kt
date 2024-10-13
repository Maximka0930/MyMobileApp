package com.example.mymobileapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class SecondSignUpActivity : AppCompatActivity() {
    private lateinit var dateEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateEditText = findViewById(R.id.dateEditText)

        // Добавляем слушатель для иконки календаря
        dateEditText.setOnDrawableClickListener {
            showDatePickerDialog()
        }
    }

    fun goBack(view: View) {
        finish()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                dateEditText.setText(formattedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    fun openThirdSignUpActivity(view: View) {
        val intent = Intent(this,ThirdSignUpActivity::class.java)
        startActivity(intent)

    }
}

// Расширение для TextInputEditText, чтобы обрабатывать клики по иконке
fun TextInputEditText.setOnDrawableClickListener(onClick: () -> Unit) {
    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            val drawableStart = compoundDrawablesRelative[0]
            if (drawableStart != null) {
                val drawableStartWidth = drawableStart.intrinsicWidth
                val touchX = event.x.toInt()
                if (touchX <= drawableStartWidth) {
                    onClick()
                    return@setOnTouchListener true
                }
            }
        }
        false
    }
}
