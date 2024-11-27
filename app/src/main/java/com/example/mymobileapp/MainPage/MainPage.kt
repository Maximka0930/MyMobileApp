package com.example.mymobileapp.MainPage

import com.example.mymobileapp.MainPage.Fragments.FavoritesFragment
import com.example.mymobileapp.MainPage.Fragments.HomeFragment
import SearchResultFragment
import com.example.mymobileapp.MainPage.Fragments.SettingsFragment
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mymobileapp.R


class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val HomePage = findViewById<ImageView>(R.id.HomePage);
        val FavouritesPage = findViewById<ImageView>(R.id.FavouritesPage);
        val SettingsPage = findViewById<ImageView>(R.id.SettingsPage);


        // Установка начального фрагмента
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Настройка обработчиков нажатий на кнопки
        findViewById<ImageView>(R.id.HomePage).setOnClickListener {
            replaceFragment(HomeFragment())
            HomePage.setImageResource(R.drawable.home_choose_icon)
            FavouritesPage.setImageResource(R.drawable.bookmark_icon)
            SettingsPage.setImageResource(R.drawable.settings_unchoose_icon)
            HomePage.isEnabled = false // Отключение кнопки HomePage
            FavouritesPage.isEnabled = true // Отключение кнопки HomePage
            SettingsPage.isEnabled = true

        }

        findViewById<ImageView>(R.id.FavouritesPage).setOnClickListener {
            replaceFragment(FavoritesFragment())
            HomePage.setImageResource(R.drawable.home_icon)
            FavouritesPage.setImageResource(R.drawable.bookmark_icon)
            SettingsPage.setImageResource(R.drawable.settings_unchoose_icon)
            FavouritesPage.isEnabled = false // Отключение кнопки HomePage
            HomePage.isEnabled = true // Отключение кнопки HomePage
            SettingsPage.isEnabled = true


        }

        findViewById<ImageView>(R.id.SettingsPage).setOnClickListener {
            replaceFragment(SettingsFragment())
            HomePage.setImageResource(R.drawable.home_icon)
            FavouritesPage.setImageResource(R.drawable.bookmark_icon)
            SettingsPage.setImageResource(R.drawable.settings_icon)
            SettingsPage.isEnabled = false
            HomePage.isEnabled = true // Отключение кнопки HomePage
            FavouritesPage.isEnabled = true // Отключение кнопки HomePage


        }
    }

    override fun onResume() {
        super.onResume()
        val openSearchResult = intent.getBooleanExtra("openSearchResult", false)
        if (openSearchResult) {
            // Заменяем текущий фрагмент на SearchResultFragment
            replaceFragment(SearchResultFragment())
            showNavigationButtons()  // Отображение кнопок навигации
            intent.removeExtra("openSearchResult")  // Очистка флага
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun hideNavigationButtons() {
        findViewById<View>(R.id.ButtonStorage).visibility = View.GONE
    }

    fun showNavigationButtons() {
        findViewById<View>(R.id.ButtonStorage).visibility = View.VISIBLE
    }

}