package com.example.mymobileapp.Settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import com.bumptech.glide.Glide
import com.example.mymobileapp.Authorization.Profile
import com.example.mymobileapp.CarSearch.HomePage
import com.example.mymobileapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Settings : AppCompatActivity() {

    private lateinit var userImageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*userImageView = findViewById(R.id.UserIcon) // Предполагается, что у вас есть ImageView с ID UserIcon
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userProfilePictures = userDataDao.getAllUserProfilePictures()
                if (userProfilePictures.isNotEmpty()) {
                    val imagePath = userProfilePictures[0]
                    runOnUiThread {
                        Glide.with(this@Settings)
                            .load(imagePath)
                            .into(userImageView)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // Логируем ошибку
            }
        }*/


    }

}