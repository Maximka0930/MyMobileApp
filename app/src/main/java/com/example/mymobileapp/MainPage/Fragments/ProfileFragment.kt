import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymobileapp.DataBase.MyApp
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
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var userNameSurname: TextView
    private lateinit var userEmail: TextView
    private lateinit var userGender: TextView
    private lateinit var userGmail: TextView
    private lateinit var userAvatar: ImageView
    private lateinit var userDateOfRegistration: TextView

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

        val view = inflater.inflate(R.layout.activity_profile, container, false)

        userNameSurname = view.findViewById(R.id.UserNameSurname)
        userEmail = view.findViewById(R.id.UserEmail)
        userGender = view.findViewById(R.id.UserGender)
        userGmail = view.findViewById(R.id.UserGmail)
        userAvatar = view.findViewById(R.id.UserImage)
        userDateOfRegistration = view.findViewById(R.id.UserDateOfJoin)

        loadUserData()
        return view
    }

    private fun loadUserData() {
        val app = requireActivity().application as MyApp
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = supabase.from("user_info")
                    .select(columns = Columns.list("id", "name", "surname", "gender", "email","date_of_registration")) {
                        filter {
                            app.userId?.let { eq("id", it) }
                        }
                    }.decodeSingleOrNull<UserInfo>()

                if (user != null) {
                    userNameSurname.text = "${user.name} ${user.surname}"
                    userEmail.text = user.email
                    userGender.text = user.gender
                    userGmail.text = user.email

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale("ru"))
                    val outputFormat = SimpleDateFormat("LLLL yyyy", Locale("ru"))

                    val date = inputFormat.parse(user.date_of_registration.toString())
                    val formattedDate = outputFormat.format(date)

                    userDateOfRegistration.text = "Присоединился в "+formattedDate

                } else {
                    Toast.makeText(requireContext(), "Не удалось найти данные пользователя", Toast.LENGTH_SHORT).show()
                }

                val imageUrl = supabase.storage.from("UserImages").publicUrl("${app.userId}/user_avatar.jpg")
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.user_icon_in_settings)  // Показать изображение-заполнитель
                    .into(userAvatar)


            } catch (e: Exception) {
                Log.d("Ошибка","Ошибка при запросе данных: ${e.message}")
                Toast.makeText(requireContext(), "Ошибка при запросе данных: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
