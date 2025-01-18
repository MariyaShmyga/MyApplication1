package com.example.myapplication

import java.time.LocalDateTime
import java.io.Serializable
import java.time.temporal.ChronoUnit

data class Course(
    var name: String,                  // Название курса
    var language: String,              // Язык курса
    var difficulty: String,            // Уровень сложности
    var price: Float,                  // Стоимость курса
    var startDateTime: LocalDateTime,  // Дата и время начала курса
    var durationMonths: Int,           // Продолжительность в месяцах
    var description: List<CustomData> = emptyList(), // Подробности курса
    var imageUrl: String // URL изображения
): Serializable {
    fun endDate(): LocalDateTime {
        return startDateTime.plusMonths(durationMonths.toLong())
    }
}

data class CustomData(
    var topic: String,                 // Тема занятия или раздел курса
    var hours: Int,                    // Количество часов на тему
    var materials: String,             // Сопутствующие материалы
    var optionalProperties: String? = null // Дополнительная информация (опционально)
): Serializable



//data class Course(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val price: Double
//) : java.io.Serializable