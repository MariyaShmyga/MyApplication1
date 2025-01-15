package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import java.time.LocalDateTime

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cartViewModel: CartViewModel

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

        val courseList = listOf(
            Course("English Basics", "English", "Beginner", 50.0f, LocalDateTime.of(2025, 2, 1, 9, 0), 3, listOf(
                CustomData("Introduction", 5, "PDF Notes"),
                CustomData("Grammar", 15, "Workbook")
            )),
            Course("Spanish for Beginners", "Spanish", "Beginner", 40.0f, LocalDateTime.of(2025, 3, 10, 10, 0), 4, listOf(
                CustomData("Basic Vocabulary", 10, "Flashcards"),
                CustomData("Speaking", 20, "Audio Lessons")
            ))
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CourseAdapter(courseList) { course ->
            cartViewModel.addItem(course)
//            Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), CourseDetailActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }
    }
}
