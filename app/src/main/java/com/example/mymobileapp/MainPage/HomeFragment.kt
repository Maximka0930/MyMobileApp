import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.mymobileapp.CarSearch.Loader
import com.example.mymobileapp.MainPage.MainPage
import com.example.mymobileapp.CarSearch.SearchResult
import com.example.mymobileapp.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home_page, container, false)
        val editText: EditText = view.findViewById(R.id.FindNeededCar)

        editText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                (activity as? MainPage)?.hideNavigationButtons()
                startActivity(Intent(requireContext(), Loader::class.java))
                true
            } else {
                false
            }
        }



        return view
    }
}
