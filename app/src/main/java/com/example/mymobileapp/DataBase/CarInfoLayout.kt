package com.example.mymobileapp.DataBase
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.telecom.Call.Details
import android.widget.*
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.mymobileapp.MainPage.Fragments.BookingСar.DetailsFragment
import com.example.mymobileapp.R
import com.squareup.picasso.Picasso

@SuppressLint("UseCompatLoadingForDrawables", "ViewConstructor")
class CarInfoLayout(
    context: Context,
    model: String,
    brand: String,
    pricePerDay: String,
    gearbox: String,
    fuelType: String,
    carImageUrl: String
) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(24.dpToPx(), 16.dpToPx(), 24.dpToPx(), 0)
        }
        background = context.getDrawable(R.drawable.shell_of_search_car)

        val frameLayout = FrameLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 136.dpToPx()).apply {
                setMargins(24.dpToPx(), 24.dpToPx(), 24.dpToPx(), 24.dpToPx())
            }
        }

        val verticalLayout = LinearLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(198.dpToPx(), 102.dpToPx()).apply {
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
            }
            orientation = VERTICAL
        }

        val carModel = TextView(context).apply {
            text = model
            setTextColor(Color.parseColor("#0D0D0D"))
            textSize = 16f
            setTypeface(typeface, Typeface.BOLD)
        }

        val carBrand = TextView(context).apply {
            text = brand
            setTextColor(Color.parseColor("#16161AB2"))
            textSize = 12f
        }

        val priceLayout = LinearLayout(context).apply {
            orientation = HORIZONTAL
        }

        val priceText = TextView(context).apply {
            text = pricePerDay
            setTextColor(Color.parseColor("#0D0D0D"))
            textSize = 14f
            setTypeface(typeface, Typeface.BOLD)
        }

        val pricePerDayText = TextView(context).apply {
            text = " в день"
            setTextColor(Color.parseColor("#16161AB2"))
            textSize = 14f
        }

        priceLayout.addView(priceText)
        priceLayout.addView(pricePerDayText)

        verticalLayout.addView(carModel)
        verticalLayout.addView(carBrand)
        verticalLayout.addView(priceLayout)

        val gearboxLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(44.dpToPx(), 18.dpToPx()).apply {
                marginEnd = 16.dpToPx()
            }
            orientation = HORIZONTAL
        }

        val gearboxIcon = ImageView(context).apply {
            layoutParams = LayoutParams(18.dpToPx(), 18.dpToPx()).apply {
                marginEnd = 1.5.dpToPx()
            }
            setImageResource(R.drawable.gearbox_icon)
        }

        val gearboxText = TextView(context).apply {
            text = gearbox
            setTextColor(Color.parseColor("#16161AB2"))
            textSize = 12f
        }

        gearboxLayout.addView(gearboxIcon)
        gearboxLayout.addView(gearboxText)

        val fuelLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(71.dpToPx(), 18.dpToPx())
            orientation = HORIZONTAL
        }

        val fuelIcon = ImageView(context).apply {
            layoutParams = LayoutParams(18.dpToPx(), 18.dpToPx()).apply {
                marginEnd = 1.5.dpToPx()
            }
            setImageResource(R.drawable.fuel_icon)
        }

        val fuelText = TextView(context).apply {
            text = fuelType
            setTextColor(Color.parseColor("#16161AB2"))
            textSize = 12f
        }

        fuelLayout.addView(fuelIcon)
        fuelLayout.addView(fuelText)

        frameLayout.addView(verticalLayout)
        frameLayout.addView(ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(176.dpToPx(), 136.dpToPx()).apply {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }

            Picasso.get()
                .load(carImageUrl)
                .into(this)
        })

        val buttonLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 40.dpToPx()).apply {
                setMargins(24.dpToPx(), 0, 24.dpToPx(), 24.dpToPx())
            }
            orientation = HORIZONTAL
        }

        val bookButton = Button(context).apply {
            layoutParams = LayoutParams(0, 40.dpToPx(), 1f).apply {
                marginEnd = 16.dpToPx()
            }
            background = context.getDrawable(R.drawable.button_bg)
            text = "Забронировать"
            setTextColor(Color.WHITE)
            textSize = 12f
            isAllCaps = false  // Отключаем автоматическое преобразование текста в верхний регистр
        }

        val detailsButton = Button(context).apply {
            layoutParams = LayoutParams(0, 40.dpToPx(), 1f)
            background = context.getDrawable(R.drawable.button2_bg)
            text = "Детали"
            setTextColor(Color.BLACK)
            textSize = 12f
            isAllCaps = false  // Отключаем автоматическое преобразование текста в верхний регистр
        }

        detailsButton.setOnClickListener {
            // Замените `FragmentToOpen` на имя вашего фрагмента
            val fragment = DetailsFragment()

            // Открываем фрагмент через FragmentManager
            (context as? AppCompatActivity)?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment) // Замените fragment_container на ID контейнера
                ?.addToBackStack(null) // Добавляет транзакцию в стек, чтобы можно было вернуться
                ?.commit()
        }


        buttonLayout.addView(bookButton)
        buttonLayout.addView(detailsButton)

        addView(frameLayout)
        addView(buttonLayout)
    }

        private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

        private fun Double.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

}