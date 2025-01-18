package com.example.myapplication

import CustomJsonParser
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dialoglibrary.RubberEffectDialog
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun write(out: JsonWriter, value: LocalDateTime?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value.format(formatter))
        }
    }

    override fun read(`in`: JsonReader): LocalDateTime? {
        return if (`in`.peek() == com.google.gson.stream.JsonToken.NULL) {
            `in`.nextNull()
            null
        } else {
            LocalDateTime.parse(`in`.nextString(), formatter)
        }
    }
}

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var courseAdapter: CourseAdapter
    private var courseList: List<Course> = listOf()
    private val gson: Gson = Gson().newBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(CartViewModel::class.java)

        val jsonParser = CustomJsonParser()
        jsonParser.testParser()

        // Загружаем список курсов
        //loadSampleCourses()
        courseList = loadCoursesFromFile()

        // Настройка RecyclerView
        courseAdapter = CourseAdapter(courseList) { course ->
            cartViewModel.addItem(course)
            val intent = Intent(requireContext(), CourseDetailActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = courseAdapter

        // Фильтрация списка
        binding.searchInput.addTextChangedListener { text ->
            val query = text.toString().lowercase()
            val filteredList = courseList.filter {
                it.language.lowercase().contains(query) || it.difficulty.lowercase().contains(query)
            }
            courseAdapter.updateList(filteredList)
        }

//        binding.testDialogButton.setOnClickListener {
//            val dialog = RubberEffectDialog()
//            dialog.show(parentFragmentManager, "RubberEffectDialog")
//        }
    }

    private fun loadCoursesFromFile(): List<Course> {
        val file = File(requireContext().filesDir, "courses.json")
        if (file.exists() && file.length() > 0) {
            val json = file.readText()
            val type = object : TypeToken<List<Course>>() {}.type
            return try {
                gson.fromJson(json, type) ?: emptyList()
            } catch (e: Exception) {
                Log.e("HomeFragment", "Ошибка загрузки курсов", e)
                Toast.makeText(requireContext(), "Ошибка загрузки курсов", Toast.LENGTH_SHORT).show()
                emptyList()
            }
        }
        return emptyList()
    }

}
