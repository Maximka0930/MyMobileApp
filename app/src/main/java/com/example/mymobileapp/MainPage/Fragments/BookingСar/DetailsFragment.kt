package com.example.mymobileapp.MainPage.Fragments.BookingСar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.mymobileapp.Adapters.ImagePagerAdapter
import com.example.mymobileapp.MainPage.Fragments.BookingСar.Activities.RegistrationOfTheLease
import com.example.mymobileapp.MainPage.Fragments.BookingСar.Activities.SuccessfulCarBooking
import com.example.mymobileapp.R

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_details, container, false)

        val openFragmentButton: Button = view.findViewById(R.id.OpenRegistrationOfTheLease)
        openFragmentButton.setOnClickListener {
            openNewActivity()
        }

        val images = listOf(
            R.drawable.test_car_image,
            R.drawable.test_car_image,
            R.drawable.test_car_image
        )

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = ImagePagerAdapter(images)

        return view
    }

    private fun openNewActivity() {
        val intent = Intent(requireContext(), RegistrationOfTheLease::class.java) // Укажите целевую Activity
        startActivity(intent) // Запускаем новую Activity
    }

}
