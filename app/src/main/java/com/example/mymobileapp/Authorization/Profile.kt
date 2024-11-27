package com.example.mymobileapp.Authorization

import UserInfo
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mymobileapp.DataBase.MyApp
import com.example.mymobileapp.R
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale

val supabase = createSupabaseClient(
    supabaseUrl = "https://qzzziiipxgsyqcsruaml.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF6enppaWlweGdzeXFjc3J1YW1sIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEwNjUyOTYsImV4cCI6MjA0NjY0MTI5Nn0.lmCy2_M2Mrt0fNfAhTsMibNNMZAeXqTk7XdwdAiBebg"
) {
    install(Postgrest)
}



class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val app = application as MyApp

        val user_name_surname = findViewById<TextView>(R.id.UserNameSurname)
        val user_email = findViewById<TextView>(R.id.UserEmail)
        val user_gender = findViewById<TextView>(R.id.UserGender)
        val user_gmail = findViewById<TextView>(R.id.UserGmail)
        val user_date_of_registration = findViewById<TextView>(R.id.UserDateOfJoin)

        runBlocking {
            launch {
                try {

                    val user = supabase.from("user_info").select(columns = Columns.list("id","name","surname","gender","email","data_of_registration"))
                    {
                        filter {
                            app.userId?.let { eq("id", it) }
                        }

                    }.decodeSingleOrNull<UserInfo>()

                    Log.d("Проверка существования user:","${user}")


                    if (user != null) {
                        if (user.id != -1)//Если получили id пользователя
                        {
                            Log.d("Проверка, что пользователя найден.","User.id = ${user.id}")
                            Log.d("Данные пользователя:","User.name = ${user.name}, User.surname = ${user.surname}, User.email = ${user.email}, User.gender = ${user.gender}")
                            Log.d("Проверка, что пользователя найден.","User.id = ${user.id}")

                            Toast.makeText(this@Profile,"user.id =  ${user.id}", Toast.LENGTH_SHORT).show()
                            user_name_surname.text = user.name + " " + user.surname
                            user_email.text = user.email
                            user_gender.text = user.gender
                            user_gmail.text = user.email

                            val inputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale("ru"))
                            val outputFormat = SimpleDateFormat("LLLL yyyy", Locale("ru"))

                            val date = inputFormat.parse(user.date_of_registration)
                            val formattedDate = outputFormat.format(date)

                            user_date_of_registration.text = user.date_of_registration
                        }
                        else
                        {
                            Toast.makeText(this@Profile,"Как ты тут оказался?", Toast.LENGTH_SHORT).show()
                        }


                    } else {
                        Toast.makeText(this@Profile,"Не получилось найти данные о пользователе в базе данных", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@Profile,"Ошибка при выполнении запроса: ${e.message}", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

}