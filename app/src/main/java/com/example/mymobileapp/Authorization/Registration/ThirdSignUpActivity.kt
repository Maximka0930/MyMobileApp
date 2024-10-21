package com.example.mymobileapp.Authorization.Registration

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mymobileapp.R
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ThirdSignUpActivity : AppCompatActivity() {
    private lateinit var dateEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateEditText = findViewById(R.id.dateEditTextForThird)
        dateEditText.setOnDrawableClickListener {
            showDatePickerDialog()
        }
    }

    @SuppressLint("DefaultLocale")
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

    @SuppressLint("SimpleDateFormat")
    fun openCongratulationsActivity(view: View) {

        val number = findViewById<TextInputEditText>(R.id.numberVU)
        val date = findViewById<TextInputEditText>(R.id.dateEditTextForThird)

        if (number.text.toString().length == 10 && date.text.toString() != "")
        {
            val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
            if (changeDateString(date.text.toString()) != currentDate.toString())
            {
                val intent = Intent(this, CongratulationsActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"Дата не может быть сегодняшней!", Toast.LENGTH_SHORT).show()
            }
        }
        else
        { Toast.makeText(this,"Были введены не все данные!", Toast.LENGTH_SHORT).show() }

    }



    fun goBack(view: View) {
        finish()
    }

    fun changeDateString(string: String): String {
        string.replace(string[2],'/')
        string.replace(string[5],'/')
        return string
    }

}

