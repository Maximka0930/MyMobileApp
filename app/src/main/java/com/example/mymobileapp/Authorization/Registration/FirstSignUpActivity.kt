package com.example.mymobileapp.Authorization.Registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mymobileapp.R
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

class FirstSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun goBack(view: View) {
        finish()
    }

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    private fun isValid(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    private fun CharSequence?.isValidEmail() : Boolean {
        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+
            "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )
        return EMAIL_ADDRESS_PATTERN.matcher(this).matches()
    }


    fun openSecondSignUpActivity(view:View)
    {
        val emailRegistry = findViewById<TextInputEditText>(R.id.emailInput)
        val password = findViewById<TextInputEditText>(R.id.passwordInput)
        val repeatPassword = findViewById<TextInputEditText>(R.id.repeatPasswordInput)
        val checkBox = findViewById<CheckBox>(R.id.checkbox)

        if (password.text.toString() != "" && repeatPassword.text.toString() != "" && emailRegistry.text.toString() != "" )
        {
/*            if (emailRegistry.toString().isValidEmail())
            {*/
                if(checkBox.isChecked)
                {
                    if (password.text.toString() == repeatPassword.text.toString())
                    {
                        val intent = Intent(this, SecondSignUpActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this,"Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this,"Согласитесь с условиями пользования", Toast.LENGTH_SHORT).show()
                }
            /*}
            else
            {
                Toast.makeText(this,"Некорректный ввод почты", Toast.LENGTH_SHORT).show()
            }*/
        }
        else
        {
            Toast.makeText(this,"Были введены не все данные!", Toast.LENGTH_SHORT).show()
        }

    }


}