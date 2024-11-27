package com.example.mymobileapp.Authorization.Registration

import UserInfo
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mymobileapp.ImagePickerHelper
import com.example.mymobileapp.DataBase.MyApp
import com.example.mymobileapp.R
import com.google.android.material.textfield.TextInputEditText
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import java.io.ByteArrayOutputStream


val supabase = createSupabaseClient(
    supabaseUrl = "https://qzzziiipxgsyqcsruaml.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF6enppaWlweGdzeXFjc3J1YW1sIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEwNjUyOTYsImV4cCI6MjA0NjY0MTI5Nn0.lmCy2_M2Mrt0fNfAhTsMibNNMZAeXqTk7XdwdAiBebg"
) {
    install(io.github.jan.supabase.storage.Storage)
    install(Postgrest)
}





class ThirdSignUpActivity : AppCompatActivity() {
    private lateinit var dateEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateEditText = findViewById(R.id.dateEditTextForThird)
        dateEditText.setOnDrawableClickListener {
            showDatePickerDialog()
        }

        dateEditText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val mask = "DD/MM/YYYY"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                isUpdating = true

                // Получаем чистую строку, убирая любые символы кроме цифр
                val clean = s.toString().replace(Regex("[^\\d]"), "")
                val length = clean.length

                // Форматируем строку по маске
                val formatted = when {
                    length >= 8 -> "${clean.substring(0, 2)}/${clean.substring(2, 4)}/${clean.substring(4, 8)}"
                    length >= 4 -> "${clean.substring(0, 2)}/${clean.substring(2, 4)}/${clean.substring(4)}"
                    length >= 2 -> "${clean.substring(0, 2)}/${clean.substring(2)}"
                    else -> clean
                }

                // Обновляем текст в поле
                dateEditText.setText(formatted)
                dateEditText.setSelection(formatted.length)

                isUpdating = false
            }
        })

        val imageView1 = findViewById<ImageView>(R.id.AddUserImagePlus)
        val imageView2 = findViewById<ImageView>(R.id.UserImage)

        val PassportImage = findViewById<ImageView>(R.id.PassportImage)
        val DriverLicenseImage = findViewById<ImageView>(R.id.DriverLicenseImage)

        initializeImagePickerForView(imageView2, imageView1, findViewById(R.id.AddUserImagePlus))
        initializeImagePickerForView(imageView2, imageView2, findViewById(R.id.AddUserImagePlus))
        initializeImagePickerForView(PassportImage,PassportImage)
        initializeImagePickerForView(DriverLicenseImage,DriverLicenseImage)

    }

    private var isPassportImage = false;
    private var isDriverLicenseImage = false;



    @SuppressLint("DefaultLocale")
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                dateEditText.setText(formattedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    @SuppressLint("SimpleDateFormat", "CutPasteId")
    fun openCongratulationsActivity(view: View) {

        val number = findViewById<TextInputEditText>(R.id.numberVU)
        val date = findViewById<TextInputEditText>(R.id.dateEditTextForThird).text.toString()
        if (number.text.toString().length == 10 && date != "")
        {
            val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
            if (changeDateString(date) != currentDate.toString())
            {
                //Получение изображений
                val user_avatar_image = findViewById<ImageView>(R.id.UserImage)
                val user_driver_license_image = findViewById<ImageView>(R.id.DriverLicenseImage)
                val user_passport_image = findViewById<ImageView>(R.id.PassportImage)

                val avatarImageData = imageViewToByteArray(user_avatar_image)
                val driverLicenseImageData = imageViewToByteArray(user_driver_license_image)
                val passportImageData = imageViewToByteArray(user_passport_image)

                //Текстовые данные, введённые пользователем
                val user_email = intent.getStringExtra("email").toString()
                val user_password = intent.getStringExtra("password").toString()
                val user_surname = intent.getStringExtra("surname").toString()
                val user_name = intent.getStringExtra("name").toString()
                val user_patronymic = intent.getStringExtra("patronymic").toString()

                val user_dateOfBirth = intent.getStringExtra("dateOfBirth").toString()
                val user_gender = intent.getStringExtra("gender").toString()
                val user_driver_license_number = findViewById<TextInputEditText>(R.id.numberVU).text.toString()

                //Перевод дат из String в Date
                val dateOfBirth = formatDateToString(stringToDate(user_dateOfBirth.toString()))
                val dateOfIssueDll = formatDateToString(stringToDate(date))

                val currentDate = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val formattedDate = dateFormat.format(currentDate)

                //val DLNumber = findViewById<TextInputEditText>(R.id.numberVU).text.toString()
                Log.d("Date Check", "dateOfBirth: $dateOfBirth, dateOfIssueDL: $dateOfIssueDll")

                    lifecycleScope.launch {
                        try {
                            val user = UserInfo(
                                email = user_email,
                                password = user_password,
                                surname = user_surname,
                                name = user_name,
                                patronymic = user_patronymic,
                                date_of_birth = dateOfBirth,
                                gender = user_gender,
                                driver_license_number = user_driver_license_number,
                                date_of_issue_dl = dateOfIssueDll,
                                date_of_registration = formattedDate.toString()
                            )

                            val res = supabase.from("user_info").upsert(user) {
                                select()
                            }.decodeSingle<UserInfo>()


                            val app = application as MyApp
                            app.userId = res.id

                            Log.d("id нового пользователя","id = ${app.userId}")

                            if (avatarImageData != null) {
                                uploadUserImage(res.id.toString(), "user_avatar.jpg", avatarImageData)
                            }
                            if (driverLicenseImageData != null) {
                                uploadUserImage(res.id.toString(), "user_driver_license.jpg", driverLicenseImageData)
                            }
                            if (passportImageData != null) {
                                uploadUserImage(res.id.toString(), "user_passport.jpg", passportImageData)
                            }

                            val intent = Intent(this@ThirdSignUpActivity, CongratulationsActivity::class.java)
                            startActivity(intent)
                            Log.d("Запись данных в БД","Запись данных успешна!")
                        }
                        catch (e: Exception)
                        {
                            Toast.makeText(this@ThirdSignUpActivity,"Ошибка! ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.d("Запись в БД", "Ошибка при записи данных в БД! ${e.message}")
                        }

                    }

            }
            else
            {
                Toast.makeText(this,"Дата не может быть сегодняшней!", Toast.LENGTH_SHORT).show()
            }
        }
        else
        { Toast.makeText(this,"Были введены не все данные!", Toast.LENGTH_SHORT).show() }

    }

    fun goBack(view: View) {
        finish()
    }

    fun changeDateString(date: String): String {
        if (date.isNotEmpty() && date.length > 2) {
            date.replace(date[2],'/')
            date.replace(date[5],'/')
            return date
        } else {
            Log.e("DateError", "Некорректная дата: $date")
            return ""
        }
    }

    fun stringToDate(dateString: String): Date? {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            format.parse(dateString)
        } catch (e: Exception) {
            null // Возвращаем null, если строка не может быть преобразована в дату
        }
    }
    fun formatDateToString(date: Date?): String? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault()) // ISO 8601 format
        return date?.let { format.format(it) }
    }



    // Метод для обработки каждого ImageView
    private fun initializeImagePickerForView(imageView: ImageView, pickImageButton: ImageView, addImagePlus: ImageView) {
        val imagePickerHelper = ImagePickerHelper(this, imageView)

        // Инициализация ActivityResultLauncher и передача в ImagePickerHelper
        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val uri = result.data!!.data
                if (uri != null) {
                    // Установите изображение в ImageView с масштабированием
                    imageView.setImageURI(uri)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP // Масштабирование

                    // Скрыть AddUserImagePlus
                    addImagePlus.visibility = View.GONE
                }
            }
        }

        imagePickerHelper.initLauncher(pickImageLauncher)

        // Открытие галереи по нажатию на ImageView
        pickImageButton.setOnClickListener {
            imagePickerHelper.openGallery()
        }
    }

    private fun initializeImagePickerForView(imageView: ImageView, pickImageButton: ImageView)  {
        val imagePickerHelper = ImagePickerHelper(this, imageView)

        // Инициализация ActivityResultLauncher и передача в ImagePickerHelper
        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val uri = result.data!!.data
                if (uri != null) {
                    // Установите изображение в ImageView с масштабированием
                    imageView.setImageURI(uri)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP // Масштабирование

                }
            }
        }

        imagePickerHelper.initLauncher(pickImageLauncher)

        // Открытие галереи по нажатию на ImageView
        pickImageButton.setOnClickListener {
            imagePickerHelper.openGallery()
        }
    }

    // Функция для преобразования ImageView в ByteArray
    private fun imageViewToByteArray(imageView: ImageView): ByteArray? {
        val drawable = imageView.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap ?: return null

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    // Функция для загрузки изображения в папку пользователя
    private fun uploadUserImage(userId: String, imageName: String, imageData: ByteArray) {
        CoroutineScope(Dispatchers.IO).launch {
            val userFolder = "$userId" // Папка для пользователя
            val filePath = "$userFolder/$imageName"

            try {
                // Загрузка изображения в Supabase Storage
                supabase.storage.from("UserImages")
                    .upload(filePath, imageData) {
                        upsert = true
                    }
                Log.d("Добавление изображения в Storage", " Изображение ${imageName} успешно добавлено")
            } catch (e: Exception) {
                Log.d("Добавление изображения в Storage", " Ошибка при добавлении изображения ${imageName}. Ошибка: ${e.message}")
            }
        }
    }

}

