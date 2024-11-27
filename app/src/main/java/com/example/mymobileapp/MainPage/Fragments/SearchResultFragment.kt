import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymobileapp.DataBase.CarInfo
import com.example.mymobileapp.DataBase.CarInfoLayout
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

class SearchResultFragment : Fragment() {

    private lateinit var cars_bar: LinearLayout
    private lateinit var searchQuery: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_search_result, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем строку поиска
        val searchQuery = activity?.intent?.getStringExtra("searchQuery") ?: ""

        Log.d("huh", searchQuery)


        // Выполняем поиск по базе данных
        cars_bar = view.findViewById(R.id.CarsBar)

        searchCars(searchQuery)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainPage)?.showNavigationButtons() // Показ кнопок при открытии фрагмента
    }

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://qzzziiipxgsyqcsruaml.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF6enppaWlweGdzeXFjc3J1YW1sIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEwNjUyOTYsImV4cCI6MjA0NjY0MTI5Nn0.lmCy2_M2Mrt0fNfAhTsMibNNMZAeXqTk7XdwdAiBebg"
    ) {
        install(Postgrest)
        install(io.github.jan.supabase.storage.Storage)
    }


    private fun searchCars(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Выполняем запрос по модели автомобилей
                val cars = supabase.from("cars_info")
                    .select(columns = Columns.list("id", "model", "manufacturer", "price_per_day", "transmission", "type_of_fuel"))
                    .decodeList<CarInfo>() // Получаем список автомобилей

                val filteredCars = cars.filter { car ->
                    car.model?.let { model ->
                        Log.d("FilterDebug", "Model: $model, Query: $query")
                        model.contains(query, ignoreCase = true)
                    } ?: false
                }

                if (filteredCars.isNotEmpty()) {
                    for (car in filteredCars) {

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
                    Toast.makeText(requireContext(), "Автомобили не найдены", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("Ошибка", "Ошибка при запросе данных: ${e.message}")
                Toast.makeText(requireContext(), "Ошибка при запросе данных: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
