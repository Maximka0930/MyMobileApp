package com.example.mymobileapp
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class ImagePickerHelper(private val context: Context, private val imageView: ImageView) {

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    // Метод инициализации ActivityResultLauncher
    fun initLauncher(launcher: ActivityResultLauncher<Intent>) {
        pickImageLauncher = launcher
    }

    // Метод для открытия галереи
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    // Метод для обработки выбранного изображения
    fun handleImageResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                imageView.setImageURI(it)  // Установка изображения в ImageView
            }
        }
    }
}