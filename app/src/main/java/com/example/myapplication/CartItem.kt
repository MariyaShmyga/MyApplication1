package com.example.myapplication

data class CartItem(
    val course: Course, // Ссылка на выбранный курс
    var quantity: Int = 1 // Количество (можно добавить возможность покупки нескольких мест на курс)
)