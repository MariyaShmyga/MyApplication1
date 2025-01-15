package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityCourseDetailBinding
import java.time.format.DateTimeFormatter

class CourseDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDetailBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация привязки
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация ViewModel
        cartViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(CartViewModel::class.java)

        // Получение данных о курсе
        val course = intent.getSerializableExtra("course") as? Course
        course?.let {
            binding.titleTextView.text = it.name
            binding.languageTextView.text = "Язык: ${it.language}"
            binding.difficultyTextView.text = "Уровень сложности: ${it.difficulty}"
            binding.priceTextView.text = "Цена: $${it.price}"
            binding.descriptionTextView.text = it.description.joinToString(separator = "\n\n") { data ->
                "Тема: ${data.topic}\nЧасы: ${data.hours}\nМатериалы: ${data.materials}${data.optionalProperties?.let { notes -> "\nNotes: $notes" } ?: ""}"
            }
        }

        // Кнопка "Добавить в корзину"
        binding.addToCartButton.setOnClickListener {
            course?.let {
                cartViewModel.addItem(it)
                Toast.makeText(this, "Курс добавлен в корзину!", Toast.LENGTH_SHORT).show()
            }
        }

        // Кнопка "Назад"
        binding.backButton.setOnClickListener {
            finish() // Закрыть текущую Activity и вернуться назад
        }
    }
}
