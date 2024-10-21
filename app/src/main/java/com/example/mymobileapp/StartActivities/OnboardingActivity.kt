package com.example.mymobileapp.StartActivities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import android.view.View
import android.widget.TextView
import com.example.mymobileapp.Authorization.GettingStartedActivity
import com.example.mymobileapp.R
import com.google.android.material.button.MaterialButton


class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2 // Задаем поле класса
    private lateinit var nextButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Подключаем основной layout

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        nextButton = findViewById(R.id.nextButton)
        viewPager = findViewById<ViewPager2>(R.id.viewPager) // замените ViewPager2, если используете ViewPager

        val images = listOf(
            R.drawable.first_onboarding,
            R.drawable.second_onboarding,
            R.drawable.third_onboarding
        )

        val adapter = ViewPagerAdapterForOnboarding(images)
        viewPager.adapter = adapter


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                animateIndicator(position)
                changeText(position)
                updateNextButtonText(position)
            }

        })


    }

    private fun animateIndicator(position: Int) {
        val line1 = findViewById<View>(R.id.line1)
        val line2 = findViewById<View>(R.id.line2)
        val line3 = findViewById<View>(R.id.line3)

        // Задаем параметры для линий в зависимости от выбранной страницы
        val activeLineWidth = 80 // Ширина активной линии
        val inactiveLineWidth = 10 // Ширина неактивной линии

        // Обновляем параметры линий
        line1.layoutParams.width = if (position == 0) activeLineWidth else inactiveLineWidth
        line2.layoutParams.width = if (position == 1) activeLineWidth else inactiveLineWidth
        line3.layoutParams.width = if (position == 2) activeLineWidth else inactiveLineWidth

        // Обновляем фон линий
        line1.background = if (position == 0) getDrawable(R.drawable.rounded_line) else getDrawable(
            R.drawable.rounded_line2
        )
        line2.background = if (position == 1) getDrawable(R.drawable.rounded_line) else getDrawable(
            R.drawable.rounded_line2
        )
        line3.background = if (position == 2) getDrawable(R.drawable.rounded_line) else getDrawable(
            R.drawable.rounded_line2
        )

        // Запрашиваем обновление макета
        line1.requestLayout()
        line2.requestLayout()
        line3.requestLayout()
    }

    private fun changeText(position: Int)
    {
        val text1 = findViewById<TextView>(R.id.firstText)
        val text2 = findViewById<TextView>(R.id.secondText)

        if (position == 0)
        {
            text1.text = "Аренда автомобилей"
            text2.text = "Открой для себя удобный и доступный способ передвижения"
        }
        if (position == 1)
        {
            text1.text = "Безопасно и удобно"
            text2.text = "Арендуй автомобиль и наслаждайся его удобством"
        }
        if (position == 2)
        {
            text1.text = "Лучшие предложения"
            text2.text = "Выбирай понравившееся среди сотен доступных автомобилей"
        }

    }

    fun onNextButtonClick(view: android.view.View) {
        val nextItem = viewPager.currentItem + 1
        if (nextItem < viewPager.adapter?.itemCount ?: 0) {
            viewPager.currentItem = nextItem
        }
        if (nextItem > 2)
        {
            openGettingStartedActivity(viewPager)
        }
    }

    private fun updateNextButtonText(position: Int) {
        nextButton.text = if (position == 2) "Поехали" else "Далее"
    }

    fun openGettingStartedActivity(view: View)
    {
        val intent = Intent(this, GettingStartedActivity::class.java)
        startActivity(intent)
    }

}