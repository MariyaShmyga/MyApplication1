import com.example.myapplication.Course
import com.example.myapplication.CustomData
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomJsonParser {

    fun coursesToJson(courses: List<Course>): String {
        val jsonArray = JSONArray()
        courses.forEach { course ->
            val jsonObject = JSONObject()
            jsonObject.put("name", course.name)
            jsonObject.put("language", course.language)
            jsonObject.put("difficulty", course.difficulty)
            jsonObject.put("price", course.price)
            jsonObject.put(
                "startDateTime",
                course.startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
            jsonObject.put("durationMonths", course.durationMonths) // Добавляем продолжительность
            jsonObject.put("imageUrl", course.imageUrl) // Добавляем URL изображения

            // Сериализуем описание курса
            val descriptionArray = JSONArray()
            course.description.forEach { customData ->
                val dataObject = JSONObject()
                dataObject.put("topic", customData.topic)
                dataObject.put("hours", customData.hours)
                dataObject.put("materials", customData.materials)
                dataObject.put("optionalProperties", customData.optionalProperties)
                descriptionArray.put(dataObject)
            }
            jsonObject.put("description", descriptionArray)

            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    // Десериализация JSON строки в список курсов
    fun jsonToCourses(json: String): List<Course> {
        val courses = mutableListOf<Course>()
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.getString("name")
            val language = jsonObject.getString("language")
            val difficulty = jsonObject.getString("difficulty")
            val price = jsonObject.getDouble("price").toFloat()
            val startDateTime = LocalDateTime.parse(
                jsonObject.getString("startDateTime"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            )
            val durationMonths = jsonObject.getInt("durationMonths") // Читаем продолжительность
            val imageUrl = jsonObject.getString("imageUrl") // Читаем URL изображения

            // Разбор описания курса
            val descriptionArray = jsonObject.getJSONArray("description")
            val description = mutableListOf<CustomData>()
            for (j in 0 until descriptionArray.length()) {
                val dataObject = descriptionArray.getJSONObject(j)
                val topic = dataObject.getString("topic")
                val hours = dataObject.getInt("hours")
                val materials = dataObject.getString("materials")
                val optionalProperties = if (dataObject.has("optionalProperties")) {
                    dataObject.getString("optionalProperties")
                } else {
                    null
                }
                description.add(CustomData(topic, hours, materials, optionalProperties))
            }

            val course = Course(
                name = name,
                language = language,
                difficulty = difficulty,
                price = price,
                startDateTime = startDateTime,
                durationMonths = durationMonths, // Заполняем продолжительность
                imageUrl = imageUrl, // Заполняем URL изображения
                description = description
            )
            courses.add(course)
        }
        return courses
    }

    fun testParser() {
        val sampleCourses = listOf(
            Course(
                name = "Суперпомерашки",
                language = "Английский",
                difficulty = "Начинающий",
                price = 50.0f,
                startDateTime = LocalDateTime.of(2025, 2, 1, 9, 0),
                durationMonths = 6,
                imageUrl = "https://img.freepik.com/free-photo/high-angle-english-books-arrangement_23-2149440468.jpg?t=st=1737232729~exp=1737236329~hmac=caea3f573e1520dc594783b2ad18965cc96eecc418f46009f58c460a98d7db58&w=740",
                description = listOf(
                    CustomData("Введение", 5, "PDF Notes", null),
                    CustomData("Грамматика", 15, "Workbook", null)
                )
            ),
            Course(
                name = "Испанские мотивы",
                language = "Испанский",
                difficulty = "Начинающий",
                price = 40.0f,
                startDateTime = LocalDateTime.of(2025, 3, 10, 10, 0),
                durationMonths = 6,
                imageUrl = "https://img.freepik.com/free-photo/learn-spanish-language-online-education-concept_53876-139716.jpg?t=st=1737232779~exp=1737236379~hmac=f78d301ab59105a2afbb43468168dc766887e4e972f50106db35e9d1bc6b45f1&w=1380",
                description = listOf(
                    CustomData("Словарный запас", 10, "Карточки", null),
                    CustomData("Разговорный язык", 20, "Аудиоуроки", null)
                )
            )
        )

        val json = coursesToJson(sampleCourses)
        println("Сериализованный JSON:\n$json")

        val parsedCourses = jsonToCourses(json)
        println("Десериализованный список курсов:\n$parsedCourses")
    }
}
