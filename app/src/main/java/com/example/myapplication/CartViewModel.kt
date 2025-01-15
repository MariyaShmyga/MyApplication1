package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartFileName = "cart.json"
    private val gson = Gson()

    // Модель данных корзины
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems

    init {
        loadCartFromFile() // Загружаем данные корзины из файла при инициализации
    }

    // Добавление элемента в корзину
    fun addItem(course: Course) {
        val currentList = _cartItems.value ?: mutableListOf()
        val existingItem = currentList.find { it.course.name == course.name }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            currentList.add(CartItem(course))
        }
        _cartItems.value = currentList // Обновляем LiveData
        saveCartToFile() // Сохраняем изменения в файл
    }

    // Удаление элемента из корзины
    fun removeItem(course: Course) {
        val currentList = _cartItems.value ?: mutableListOf()
        currentList.removeIf { it.course.name == course.name }
        _cartItems.value = currentList // Обновляем LiveData
        saveCartToFile() // Сохраняем изменения в файл
    }

    // Получение общей стоимости
    fun getTotalPrice(): Float {
        return _cartItems.value?.sumOf { (it.course.price * it.quantity).toDouble() }?.toFloat() ?: 0f
    }

    // Сохранение корзины в файл
    private fun saveCartToFile() {
        val currentItems: MutableList<CartItem> = _cartItems.value ?: mutableListOf() // Явное указание типа
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        val json = gson.toJson(currentItems, type) // Используем явное значение currentItems
        val file = File(getApplication<Application>().filesDir, cartFileName)
        file.writeText(json)
    }


    // Загрузка корзины из файла
    private fun loadCartFromFile() {
        val file = File(getApplication<Application>().filesDir, cartFileName)
        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableList<CartItem>>() {}.type
            val loadedCartItems: MutableList<CartItem> = gson.fromJson(json, type)
            _cartItems.value = loadedCartItems
        }
    }
}
