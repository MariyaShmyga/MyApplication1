package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CourseAdapter(
    private var courses: List<Course>,
    private val onClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.bind(course, onClick)
    }

    override fun getItemCount(): Int = courses.size

    // Метод для обновления списка
    fun updateList(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val startDateTextView: TextView = itemView.findViewById(R.id.startDateTextView)
        private val courseImageView: ImageView = itemView.findViewById(R.id.courseImageView)
        private val difficultyTextView: TextView = itemView.findViewById(R.id.difficultyTextView)

        fun bind(course: Course, onClick: (Course) -> Unit) {
            titleTextView.text = course.name
            difficultyTextView.text = "Сложность: ${course.difficulty}" // Уровень сложности
            priceTextView.text = "$${course.price}"
            startDateTextView.text = "Starts: ${course.startDateTime.toLocalDate()}"
            itemView.setOnClickListener { onClick(course) }

            // Загрузка изображения через Picasso
            Picasso.get()
                .load(course.imageUrl)
//                .placeholder(R.drawable.placeholder) // Замените на ресурс-заглушку
//                .error(R.drawable.error_placeholder) // Замените на ресурс ошибки
                .into(courseImageView)
        }
    }
}