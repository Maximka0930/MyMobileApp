package com.example.mymobileapp.Authorization

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mymobileapp.R
import com.example.mymobileapp.Authorization.Registration.FirstSignUpActivity
import com.example.mymobileapp.MainPage.MainPage
import com.example.mymobileapp.DataBase.MyApp
import com.google.android.material.textfield.TextInputEditText
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun openFirstSignUpActivity(view: View) {

        val intent = Intent(this, FirstSignUpActivity::class.java)
        startActivity(intent)
    }

    val supabase = createSupabaseClient(
        supabaseUrl = "https://qzzziiipxgsyqcsruaml.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF6enppaWlweGdzeXFjc3J1YW1sIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEwNjUyOTYsImV4cCI6MjA0NjY0MTI5Nn0.lmCy2_M2Mrt0fNfAhTsMibNNMZAeXqTk7XdwdAiBebg"
    ) {
        install(Postgrest)
    }


    fun openMainPageActivity(view: View) {

        val user_email = findViewById<TextInputEditText>(R.id.auth_email).text.toString()
        val user_password = findViewById<TextInputEditText>(R.id.auth_password).text.toString()

        runBlocking {
            launch {
                try {

                val user = supabase.from("user_info").select(columns = Columns.list("id","email", "password"))
                    {
                        filter {
                            eq("email", user_email)
                        }

                    }.decodeSingle<UserInfo>()

                    Log.d("Email & Password Check", "dateOfBirth: ${user.email}, dateOfIssueDL: ${user.password}")

                    if (user != null && user.password == user_password) {

                        val app = application as MyApp
                        app.userId = user.id
                        val intent = Intent(this@LoginActivity, MainPage::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity,"Неверные email или пароль", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    println("Ошибка при выполнении запроса: ${e.message}")
                    Toast.makeText(this@LoginActivity,"Ошибка при выполнении запроса: ${e.message}", Toast.LENGTH_SHORT).show()

                }
            }
        }



    }

}
