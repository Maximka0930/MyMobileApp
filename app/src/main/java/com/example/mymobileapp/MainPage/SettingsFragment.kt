import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.mymobileapp.R

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_settings, container, false)

// Настройка ImageView для перехода на дочерний фрагмент
        val imageView: ImageView = view.findViewById(R.id.UserIcon)
        imageView.setOnClickListener {
            replaceFragment(Profile())
        }

        return view

    }

    private fun replaceFragment(fragment: Fragment) {
        // Переход на новый фрагмент
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Добавляет текущий фрагмент в стек, чтобы можно было вернуться
            .commit()
    }

}
