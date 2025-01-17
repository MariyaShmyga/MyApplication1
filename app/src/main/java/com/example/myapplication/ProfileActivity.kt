package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Инициализация UserRepository
        userRepository = UserRepository(requireContext())
        // Загрузка данных пользователя (можно использовать SharedPreferences или базу данных)
        loadUserProfile()

        // Обработчик для кнопки "Редактировать профиль"
        binding.editProfileButton.setOnClickListener {
            // Перейти на экран редактирования профиля
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        val user = userRepository.getLoggedInUser()
        user?.let {
            binding.profileName.text = it.name
            binding.profileEmail.text = it.email
        }

        binding.logoutButton.setOnClickListener {
            userRepository.setLoggedInState(false)
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

//        binding.logoutButton.setOnClickListener {
//            val sharedPreferences = requireContext().getSharedPreferences("UserSession", AppCompatActivity.MODE_PRIVATE)
//            sharedPreferences.edit().apply {
//                putBoolean("isLoggedIn", false)
//                apply()
//            }
//
//            // Перенаправляем на экран авторизации
//            val intent = Intent(requireContext(), LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }

        Log.d("ProfileFragment", "isLoggedIn = ${userRepository.isLoggedIn()} before tap on logoutbutton")
        binding.logoutButton.setOnClickListener {
            userRepository.setLoggedInState(false)
            Log.d("ProfileFragment", "Logout: isLoggedIn = ${userRepository.isLoggedIn()}")
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun loadUserProfile() {
        // Пример загрузки данных (замените на реальные данные)
        binding.profileName.text = "John Doe"
        binding.profileEmail.text = "johndoe@example.com"
    }

    private fun logoutUser() {
//        // Очистка данных пользователя (при необходимости) и навигация к фрагменту входа
//        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

