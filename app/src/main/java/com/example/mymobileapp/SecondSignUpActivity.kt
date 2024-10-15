package com.example.mymobileapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
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

        dateEditText = findViewById(R.id.dateEditTextForSecond)
        dateEditText.setOnDrawableClickListener {
            showDatePickerDialog()
        }
    }

    fun goBack(view: View) {
        finish()
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

    fun openThirdSignUpActivity(view: View) {

        val surname = findViewById<TextInputEditText>(R.id.surname_input)
        val name = findViewById<TextInputEditText>(R.id.name_input)
        val patronymic = findViewById<TextInputEditText>(R.id.patronymic_input)
        val date = findViewById<TextInputEditText>(R.id.dateEditTextForSecond)
        val firstRadioButton = findViewById<RadioButton>(R.id.radioButton1)
        val secondRadioButton = findViewById<RadioButton>(R.id.radioButton2)


        if (surname.text.toString() != "" && name.text.toString() != "" && /*patronymic.text.toString() != "" &&*/ date.text.toString() != "" )
   {
       if (firstRadioButton.isChecked || secondRadioButton.isChecked)
       {
           val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
           if (date.text.toString() != currentDate.toString())
           {
               val intent = Intent(this,ThirdSignUpActivity::class.java)
               startActivity(intent)
           }
           else
           {
               Toast.makeText(this,"Дата не может быть сегодняшней!", Toast.LENGTH_SHORT).show()
           }
       }
       else
       { Toast.makeText(this,"Выберите пол!", Toast.LENGTH_SHORT).show() }
   }
   else
        { Toast.makeText(this,"Были введены не все данные!", Toast.LENGTH_SHORT).show() }

    }


}



// Расширение для TextInputEditText, чтобы обрабатывать клики по иконке
@SuppressLint("ClickableViewAccessibility")
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
