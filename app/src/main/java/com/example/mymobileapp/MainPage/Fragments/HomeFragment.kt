package com.example.mymobileapp.MainPage.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymobileapp.DataBase.CarInfo
import com.example.mymobileapp.DataBase.CarInfoLayout
import com.example.mymobileapp.MainPage.CarSearchLoader
import com.example.mymobileapp.MainPage.MainPage
import com.example.mymobileapp.R
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private lateinit var cars_search: EditText

    private lateinit var cars_bar: LinearLayout
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
        val view = inflater.inflate(R.layout.activity_home_page, container, false)
        cars_search = view.findViewById(R.id.FindNeededCar)

        cars_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                Log.d("Значение строки поиска", query)

                // Дополнительные действия с query, если нужно
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        cars_search.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                (activity as? MainPage)?.hideNavigationButtons()

                // Переходим в LoaderFragment с передачей строки поиска
                val intent = Intent(requireContext(), CarSearchLoader::class.java)
                intent.putExtra("searchQuery", cars_search.text.toString())
                startActivity(intent)

                true
            } else {
                false
            }
        }


        cars_bar = view.findViewById(R.id.CarsBar)
        loadCarData()

        return view
    }

    private fun loadCarData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Проверяем, прикреплен ли фрагмент к активности
                if (isAdded) {
                    val cars = supabase.from("cars_info")
                        .select(columns = Columns.list("id", "model", "manufacturer", "price_per_day", "transmission", "type_of_fuel"))
                        .decodeList<CarInfo>() // Используем decodeList для получения списка

                    if (cars.isNotEmpty()) {
                        for (car in cars) {
                            val imageUrl = supabase.storage.from("CarImages").publicUrl("${car.id}/car_first_icon.png")

                            val carInfoLayout = CarInfoLayout(
                                requireContext(),
                                car.model.toString(),
                                car.manufacturer.toString(),
                                car.price_per_day.toString(),
                                car.transmission.toString(),
                                car.type_of_fuel.toString(),
                                imageUrl
                            )
                            cars_bar.addView(carInfoLayout)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Не удалось найти автомобили", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.d("Ошибка", "Ошибка при запросе данных: ${e.message}")
                Toast.makeText(requireContext(), "Ошибка при запросе данных: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
