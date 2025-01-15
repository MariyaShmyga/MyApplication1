package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Загрузка данных пользователя (можно использовать SharedPreferences или базу данных)
        loadUserProfile()

        // Обработчик для кнопки "Редактировать профиль"
        binding.editProfileButton.setOnClickListener {
            // Перейти на экран редактирования профиля
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Обработчик для кнопки "Выйти из аккаунта"
        binding.logoutButton.setOnClickListener {
            // Очистка данных пользователя и переход на экран входа
            logoutUser()
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


//package com.example.myapplication
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.example.myapplication.databinding.ActivityProfileBinding
//
//class ProfileActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityProfileBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Загрузка данных пользователя (можно использовать SharedPreferences или базу данных)
//        loadUserProfile()
//
//        // Обработчик для кнопки "Редактировать профиль"
//        binding.editProfileButton.setOnClickListener {
//            // Перейти на экран редактирования профиля
//            val intent = Intent(this, EditProfileActivity::class.java)
//            startActivity(intent)
//        }
//
//        // Обработчик для кнопки "Выйти из аккаунта"
//        binding.logoutButton.setOnClickListener {
//            // Очистка данных пользователя и переход на экран входа
//            logoutUser()
//        }
//    }
//
//    private fun loadUserProfile() {
//        // Пример загрузки данных (замените на реальные данные)
//        binding.profileName.text = "John Doe"
//        binding.profileEmail.text = "johndoe@example.com"
//    }
//
//    private fun logoutUser() {
//        // Очистка данных пользователя и возврат к экрану входа
//        val intent = Intent(this, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//    }
//}
