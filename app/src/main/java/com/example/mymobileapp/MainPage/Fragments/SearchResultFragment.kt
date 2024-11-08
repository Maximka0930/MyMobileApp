import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymobileapp.MainPage.MainPage
import com.example.mymobileapp.R

class SearchResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_search_result, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainPage)?.showNavigationButtons() // Показ кнопок при открытии фрагмента
    }
}
