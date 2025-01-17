package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import android.util.Log

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        getApplication<Application>().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Модель данных корзины
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems

    init {
        loadCartFromFile() // Загружаем данные корзины из файла при инициализации
    }

    // Получение текущего email пользователя
    private fun getCurrentUserEmail(): String? {
        return sharedPreferences.getString("currentUserEmail", null)
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
        if (currentList.removeIf { it.course.name == course.name }) {
            _cartItems.value = currentList // Обновляем LiveData
            saveCartToFile() // Сохраняем изменения в файл
        }
    }

    // Получение общей стоимости
    fun getTotalPrice(): Float {
        return _cartItems.value?.sumOf { (it.course.price * it.quantity).toDouble() }?.toFloat() ?: 0f
    }

    // Очистка корзины
    fun clearCart() {
        _cartItems.value = mutableListOf() // Обновляем корзину
        saveCartToFile()
    }

    // Сохранение корзины в файл
    private fun saveCartToFile() {
        val email = getCurrentUserEmail()
        if (email != null) {
            val fileName = "cart_$email.json"
            try {
                val currentItems: MutableList<CartItem> = _cartItems.value ?: mutableListOf()
                val type = object : TypeToken<MutableList<CartItem>>() {}.type
                val json = gson.toJson(currentItems, type)
                val file = File(getApplication<Application>().filesDir, fileName)
                file.writeText(json)
            } catch (e: IOException) {
                Log.e("CartViewModel", "Error saving cart to file: $e")
            }
        } else {
            Log.w("CartViewModel", "No user logged in. Cannot save cart.")
        }
    }

    // Загрузка корзины из файла
    private fun loadCartFromFile() {
        val email = getCurrentUserEmail()
        if (email != null) {
            val fileName = "cart_$email.json"
            val file = File(getApplication<Application>().filesDir, fileName)
            if (file.exists()) {
                try {
                    val json = file.readText()
                    val type = object : TypeToken<MutableList<CartItem>>() {}.type
                    val loadedCartItems: MutableList<CartItem> = gson.fromJson(json, type)
                    _cartItems.value = loadedCartItems
                } catch (e: IOException) {
                    Log.e("CartViewModel", "Error loading cart from file: $e")
                }
            }
        } else {
            Log.w("CartViewModel", "No user logged in. Cannot load cart.")
        }
    }
}



//class CartViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val cartFileName = "cart.json"
//    private val gson = Gson()
//
//    // Модель данных корзины
//    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
//    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems
//
//    init {
//        loadCartFromFile() // Загружаем данные корзины из файла при инициализации
//    }
//
//    // Добавление элемента в корзину
//    fun addItem(course: Course) {
//        val currentList = _cartItems.value ?: mutableListOf()
//        val existingItem = currentList.find { it.course.name == course.name }
//        if (existingItem != null) {
//            existingItem.quantity += 1
//        } else {
//            currentList.add(CartItem(course))
//        }
//        _cartItems.value = currentList // Обновляем LiveData
//        saveCartToFile() // Сохраняем изменения в файл
//    }
//
//    // Удаление элемента из корзины
//    fun removeItem(course: Course) {
//        val currentList = _cartItems.value ?: mutableListOf()
//        currentList.removeIf { it.course.name == course.name }
//        _cartItems.value = currentList // Обновляем LiveData
//        saveCartToFile() // Сохраняем изменения в файл
//    }
//
//    // Получение общей стоимости
//    fun getTotalPrice(): Float {
//        return _cartItems.value?.sumOf { (it.course.price * it.quantity).toDouble() }?.toFloat() ?: 0f
//    }
//
//    // Сохранение корзины в файл
//    private fun saveCartToFile() {
//        val currentItems: MutableList<CartItem> = _cartItems.value ?: mutableListOf() // Явное указание типа
//        val type = object : TypeToken<MutableList<CartItem>>() {}.type
//        val json = gson.toJson(currentItems, type) // Используем явное значение currentItems
//        val file = File(getApplication<Application>().filesDir, cartFileName)
//        file.writeText(json)
//    }
//
//
//    // Загрузка корзины из файла
//    private fun loadCartFromFile() {
//        val file = File(getApplication<Application>().filesDir, cartFileName)
//        if (file.exists()) {
//            val json = file.readText()
//            val type = object : TypeToken<MutableList<CartItem>>() {}.type
//            val loadedCartItems: MutableList<CartItem> = gson.fromJson(json, type)
//            _cartItems.value = loadedCartItems
//        }
//    }
//
//    fun clearCart() {
//        _cartItems.value?.clear()
//        _cartItems.value = _cartItems.value // Обновляем LiveData
//        saveCartToFile()
//    }
//}
