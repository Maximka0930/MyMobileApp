package com.example.mymobileapp.MainPage.Fragments

import ProfileFragment
import UserInfo
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymobileapp.DataBase.MyApp
import com.example.mymobileapp.MainPage.Fragments.BookingСar.Activities.CarReservations
import com.example.mymobileapp.MainPage.Fragments.BookingСar.Activities.RegistrationOfTheLease
import com.example.mymobileapp.MainPage.MainPage
import com.example.mymobileapp.R
import com.squareup.picasso.Picasso
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var userEmail: TextView
    private lateinit var userNameSurname: TextView
    private lateinit var userAvatar: ImageView

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://qzzziiipxgsyqcsruaml.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF6enppaWlweGdzeXFjc3J1YW1sIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEwNjUyOTYsImV4cCI6MjA0NjY0MTI5Nn0.lmCy2_M2Mrt0fNfAhTsMibNNMZAeXqTk7XdwdAiBebg"
    ) {
        install(Postgrest)
        install(io.github.jan.supabase.storage.Storage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_settings, container, false)

// Настройка ImageView для перехода на дочерний фрагмент
        val imageView: ImageView = view.findViewById(R.id.UserIcon)
        imageView.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

        val openCarReservations: LinearLayout = view.findViewById(R.id.openCarReservations)
        openCarReservations.setOnClickListener{
            openCarReservations()
        }

        userNameSurname = view.findViewById(R.id.UserNameSurname)
        userEmail = view.findViewById(R.id.UserEmail)
        userAvatar = view.findViewById(R.id.UserIcon)

        loadUserData()
        return view

    }

    private fun replaceFragment(fragment: Fragment) {
        // Переход на новый фрагмент
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Добавляет текущий фрагмент в стек, чтобы можно было вернуться
            .commit()
    }

    private fun openCarReservations() {
        val intent = Intent(requireContext(), CarReservations::class.java) // Укажите целевую Activity
        startActivity(intent) // Запускаем новую Activity
    }


    private fun loadUserData() {
        val app = requireActivity().application as MyApp
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = supabase.from("user_info")
                    .select(columns = Columns.list("id", "name", "surname", "email")) {
                        filter {
                            app.userId?.let { eq("id", it) }
                        }
                    }.decodeSingleOrNull<UserInfo>()

                if (user != null) {
                    userNameSurname.text = "${user.name} ${user.surname}"
                    userEmail.text = user.email
                } else {
                    Toast.makeText(requireContext(), "Не удалось найти данные пользователя", Toast.LENGTH_SHORT).show()
                }

                val imageUrl = supabase.storage.from("UserImages").publicUrl("${app.userId}/user_avatar.jpg")
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.user_icon_in_settings)  // Показать изображение-заполнитель
                    .into(userAvatar)


            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Ошибка при запросе данных: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
