package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAdminBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import android.widget.EditText
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private val coursesFileName = "courses.json"
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin)
        Log.d("AdminActivity", "AdminActivity запущена")

        findViewById<Button>(R.id.addCourseButton).setOnClickListener {
            Log.d("AdminPanelActivity", "Добавить курс нажата")
            addCourse()
        }

        findViewById<Button>(R.id.editCourseButton).setOnClickListener {
            Log.d("AdminActivity", "Кнопка 'Редактировать курс' нажата")
            editCourses()
        }

        findViewById<Button>(R.id.deleteCourseButton).setOnClickListener {
            Log.d("AdminActivity", "Кнопка 'Удалить курс' нажата")
            deleteCourse()
        }

        findViewById<Button>(R.id.clearFileButton).setOnClickListener {
            Log.d("AdminActivity", "Кнопка 'Очистить данные' нажата")
            clearFile()
        }

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Log.d("AdminActivity", "Кнопка 'Выйти' нажата")
            logoutAdmin()
        }



//        val logoutButton = findViewById<Button>(R.id.logoutButton)
//        logoutButton.setOnClickListener {
//            logoutAdmin()
//        }

        // Проверяем, что текущий пользователь — администратор
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val currentUserEmail = sharedPreferences.getString("currentUserEmail", null)
        val isAdmin = currentUserEmail == "admin@example.com" // Замените email на email администратора

        if (!isAdmin) {
            // Если не администратор, перенаправляем на авторизацию
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

//        setContentView(R.layout.activity_admin)
    }

//    private fun addCourse() {
//        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null, false)
//
//        val customDataContainer = dialogView.findViewById<LinearLayout>(R.id.customDataContainer)
//        val addCustomDataButton = dialogView.findViewById<Button>(R.id.addCustomDataButton)
//
//        if (customDataContainer == null || addCustomDataButton == null) {
//            Log.e("AdminActivity", "Ошибка: customDataContainer или addCustomDataButton не найдены.")
//            Toast.makeText(this, "Ошибка интерфейса", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val dialog = AlertDialog.Builder(this)
//            .setTitle("Добавить курс")
//            .setView(dialogView)
//            .setPositiveButton("Добавить") { _, _ ->
//                try {
//                    val name = dialogView.findViewById<EditText>(R.id.courseNameInput)?.text.toString()
//                    val language = dialogView.findViewById<EditText>(R.id.courseLanguageInput)?.text.toString()
//                    val difficulty = dialogView.findViewById<EditText>(R.id.courseDifficultyInput)?.text.toString()
//                    val price = dialogView.findViewById<EditText>(R.id.coursePriceInput)?.text.toString().toFloatOrNull() ?: 0f
//                    val startDateText = dialogView.findViewById<EditText>(R.id.startDateInput)?.text.toString()
//                    val durationMonths = dialogView.findViewById<EditText>(R.id.durationMonthsInput)?.text.toString().toIntOrNull() ?: 0
//
//                    val startDate = try {
//                        if (startDateText.isNotBlank()) {
//                            LocalDateTime.parse(startDateText, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
//                        } else {
//                            throw IllegalArgumentException("Дата начала курса не может быть пустой.")
//                        }
//                    } catch (e: Exception) {
//                        Log.e("AdminActivity", "Ошибка парсинга даты: ${e.message}")
//                        LocalDateTime.now() // Значение по умолчанию
//                    }
//
//                    val customDataList = mutableListOf<CustomData>()
//                    for (i in 0 until customDataContainer.childCount) {
//                        val view = customDataContainer.getChildAt(i)
//
//                        val topic = view.findViewById<EditText>(R.id.topicInput)?.text.toString()
//                        val hours = view.findViewById<EditText>(R.id.hoursInput)?.text.toString().toIntOrNull() ?: 0
//                        val materials = view.findViewById<EditText>(R.id.materialsInput)?.text.toString()
//                        val optional = view.findViewById<EditText>(R.id.optionalPropertiesInput)?.text.toString()
//
//                        if (topic.isNotBlank() || hours > 0 || materials.isNotBlank()) {
//                            customDataList.add(CustomData(topic, hours, materials, if (optional.isBlank()) null else optional))
//                        }
//                    }
//
//                    Log.d("AdminActivity", "Название курса: $name")
//                    Log.d("AdminActivity", "Язык курса: $language")
//                    Log.d("AdminActivity", "Сложность: $difficulty")
//                    Log.d("AdminActivity", "Цена: $price")
//                    Log.d("AdminActivity", "Дата начала: $startDate")
//                    Log.d("AdminActivity", "Продолжительность (мес): $durationMonths")
//                    Log.d("AdminActivity", "Описание курса: $customDataList")
//
//                    if (name.isNotBlank() && language.isNotBlank() && difficulty.isNotBlank()) {
//                        val newCourse = Course(name, language, difficulty, price, startDate, durationMonths, customDataList)
//                        saveCourse(newCourse)
//                    } else {
//                        Toast.makeText(this, "Заполните все обязательные поля", Toast.LENGTH_SHORT).show()
//                    }
//                } catch (e: Exception) {
//                    Log.e("AdminActivity", "Ошибка при добавлении курса: ${e.message}")
//                    Toast.makeText(this, "Ошибка при добавлении курса", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .setNegativeButton("Отмена", null)
//            .create()
//
//        dialog.show()
//    }
private fun addCourse() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null, false)

    val customDataContainer = dialogView.findViewById<LinearLayout>(R.id.customDataContainer)
    val addCustomDataButton = dialogView.findViewById<Button>(R.id.addCustomDataButton)

    // Обработчик кнопки "Добавить занятие"
    addCustomDataButton.setOnClickListener {
        val customDataView = LayoutInflater.from(this).inflate(R.layout.custom_data_item, customDataContainer, false)
        customDataContainer.addView(customDataView)
    }

    val dialog = AlertDialog.Builder(this)
        .setTitle("Добавить курс")
        .setView(dialogView)
        .setPositiveButton("Добавить") { _, _ ->
            try {
                val name = dialogView.findViewById<EditText>(R.id.courseNameInput)?.text.toString()
                val language = dialogView.findViewById<EditText>(R.id.courseLanguageInput)?.text.toString()
                val difficulty = dialogView.findViewById<EditText>(R.id.courseDifficultyInput)?.text.toString()
                val price = dialogView.findViewById<EditText>(R.id.coursePriceInput)?.text.toString().toFloatOrNull() ?: 0f
                val startDateText = dialogView.findViewById<EditText>(R.id.startDateInput)?.text.toString()
                val durationMonths = dialogView.findViewById<EditText>(R.id.durationMonthsInput)?.text.toString().toIntOrNull() ?: 0
//                val imageUrl = dialogView.findViewById<EditText>(R.id.ImgUrl)?.text.toString()
                // Парсинг даты
                val startDate = if (startDateText.isNotBlank()) {
                    try {
                        // Нормализуем разделители в дате
                        val normalizedDate = startDateText.replace("[./]".toRegex(), "-")
                        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)
                        LocalDate.parse(normalizedDate, formatter).atStartOfDay()
                    } catch (e: Exception) {
                        Log.e("AdminActivity", "Ошибка парсинга даты: ${e.message}")
                        throw IllegalArgumentException("Неверный формат даты. Используйте dd-MM-yyyy.")
                    }
                } else {
                    throw IllegalArgumentException("Дата начала курса не может быть пустой.")
                }

                val customDataList = mutableListOf<CustomData>()
                for (i in 0 until customDataContainer.childCount) {
                    val view = customDataContainer.getChildAt(i)
                    val topic = view.findViewById<EditText>(R.id.topicInput)?.text.toString()
                    val hours = view.findViewById<EditText>(R.id.hoursInput)?.text.toString().toIntOrNull() ?: 0
                    val materials = view.findViewById<EditText>(R.id.materialsInput)?.text.toString()
                    val optional = view.findViewById<EditText>(R.id.optionalPropertiesInput)?.text.toString()


                    if (topic.isNotBlank() || hours > 0 || materials.isNotBlank()) {
                        customDataList.add(CustomData(topic, hours, materials, if (optional.isBlank()) null else optional))
                    }
                }

                if (name.isNotBlank() && language.isNotBlank() && difficulty.isNotBlank()) {
//                    val newCourse = Course(name, language, difficulty, price, startDate, durationMonths, customDataList)
//                    saveCourse(newCourse)
                } else {
                    Toast.makeText(this, "Пожалуйста, заполните все обязательные поля", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("AdminActivity", "Ошибка при добавлении курса: ${e.message}")
                Toast.makeText(this, "Ошибка при добавлении курса", Toast.LENGTH_SHORT).show()
            }
        }
        .setNegativeButton("Отмена", null)
        .create()

    dialog.show()
}


    private fun editCourses() {
        val courses = loadCourses() // Метод для загрузки курсов из JSON-файла

        val courseNames = courses.map { it.name }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Выберите курс для редактирования")
            .setItems(courseNames) { _, which ->
                val selectedCourse = courses[which]
                showEditCourseDialog(selectedCourse)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showEditCourseDialog(course: Course) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_course, null)
        val customDataContainer = dialogView.findViewById<LinearLayout>(R.id.customDataContainer)

        // Заполняем данные выбранного курса
        dialogView.findViewById<EditText>(R.id.courseNameInput).setText(course.name)
        dialogView.findViewById<EditText>(R.id.courseLanguageInput).setText(course.language)
        dialogView.findViewById<EditText>(R.id.courseDifficultyInput).setText(course.difficulty)
        dialogView.findViewById<EditText>(R.id.coursePriceInput).setText(course.price.toString())
        dialogView.findViewById<EditText>(R.id.durationMonthsInput).setText(course.durationMonths.toString())

        // Заполняем CustomData
        course.description.forEach { customData ->
            val customDataView = layoutInflater.inflate(R.layout.custom_data_item, customDataContainer, false)
            customDataView.findViewById<EditText>(R.id.topicInput).setText(customData.topic)
            customDataView.findViewById<EditText>(R.id.hoursInput).setText(customData.hours.toString())
            customDataView.findViewById<EditText>(R.id.materialsInput).setText(customData.materials)
            customDataView.findViewById<EditText>(R.id.optionalPropertiesInput).setText(customData.optionalProperties ?: "")
            customDataContainer.addView(customDataView)
        }

        AlertDialog.Builder(this)
            .setTitle("Редактировать курс")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val name = dialogView.findViewById<EditText>(R.id.courseNameInput).text.toString()
                val language = dialogView.findViewById<EditText>(R.id.courseLanguageInput).text.toString()
                val difficulty = dialogView.findViewById<EditText>(R.id.courseDifficultyInput).text.toString()
                val price = dialogView.findViewById<EditText>(R.id.coursePriceInput).text.toString().toFloatOrNull() ?: 0f
                val durationMonths = dialogView.findViewById<EditText>(R.id.durationMonthsInput).text.toString().toIntOrNull() ?: 0

                val updatedCustomData = mutableListOf<CustomData>()
                for (i in 0 until customDataContainer.childCount) {
                    val view = customDataContainer.getChildAt(i)
                    val topic = view.findViewById<EditText>(R.id.topicInput).text.toString()
                    val hours = view.findViewById<EditText>(R.id.hoursInput).text.toString().toIntOrNull() ?: 0
                    val materials = view.findViewById<EditText>(R.id.materialsInput).text.toString()
                    val optional = view.findViewById<EditText>(R.id.optionalPropertiesInput).text.toString()
                    updatedCustomData.add(CustomData(topic, hours, materials, if (optional.isBlank()) null else optional))
                }

                course.name = name
                course.language = language
                course.difficulty = difficulty
                course.price = price
                course.durationMonths = durationMonths
                course.description = updatedCustomData


                saveCourse(course) // Сохраняем изменения
                Toast.makeText(this, "Курс успешно обновлен", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun loadCourses(): MutableList<Course> {
        val file = File(filesDir, "courses.json")
        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(json, type)
        }
        return mutableListOf()
    }

    private fun saveCourses(courses: List<Course>) {
        val json = Gson().toJson(courses)
        val file = File(filesDir, "courses.json")
        file.writeText(json)
    }

    private fun deleteCourse() {
        val courses = loadCourses() // Загрузка всех курсов

        val courseNames = courses.map { it.name }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Выберите курс для удаления")
            .setItems(courseNames) { _, which ->
                courses.removeAt(which) // Удаление выбранного курса
                saveCourses(courses) // Сохранение изменений
                Toast.makeText(this, "Курс удален", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun clearFile() {
        val file = File(filesDir, coursesFileName)
        file.writeText("")
        Toast.makeText(this, "Файл очищен", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFile() {
        val file = File(filesDir, coursesFileName)
        if (file.exists()) {
            file.delete()
            Toast.makeText(this, "Файл удалён", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Файл не существует", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCourse(course: Course) {
        if (course.name.isBlank() || course.language.isBlank() || course.difficulty.isBlank()) {
            Log.e("AdminActivity", "Ошибка сохранения курса: Некорректные данные курса")
            Toast.makeText(this, "Ошибка сохранения курса: Некорректные данные", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(filesDir, coursesFileName)
        val type = object : TypeToken<MutableList<Course>>() {}.type

        val currentCourses: MutableList<Course> = if (file.exists()) {
            try {
                gson.fromJson(file.readText(), type) ?: mutableListOf()
            } catch (e: Exception) {
                Log.e("AdminActivity", "Ошибка чтения файла: ${e.message}")
                mutableListOf()
            }
        } else {
            mutableListOf()
        }

        currentCourses.add(course)

        try {
            file.writeText(gson.toJson(currentCourses, type))
            Toast.makeText(this, "Курс добавлен", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("AdminActivity", "Ошибка сохранения файла: ${e.message}")
            Toast.makeText(this, "Ошибка сохранения файла", Toast.LENGTH_SHORT).show()
        }
    }
    private fun logoutAdmin() {
        // Сбрасываем состояние авторизации администратора
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        sharedPreferences.edit()
            .remove("currentUserEmail") // Удаляем текущий email
            .putBoolean("isLoggedIn", false) // Устанавливаем состояние неавторизованного пользователя
            .apply()

        // Перенаправляем на экран авторизации
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
