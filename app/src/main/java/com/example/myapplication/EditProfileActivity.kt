package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val userRepository by lazy { UserRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем текущего пользователя
        val currentUser = userRepository.getLoggedInUser()

        // Заполняем поля текущими данными пользователя
        currentUser?.let {
            binding.nameInput.setText(it.name)
            binding.emailInput.setText(it.email)
        }

        // Сохраняем изменения
        binding.saveButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()

            if (name.isNotBlank() && email.isNotBlank()) {
                currentUser?.let { user ->
                    val updatedUser = user.copy(name = name, email = email)
                    userRepository.updateUser(user.email, updatedUser)
                    Toast.makeText(this, "Профиль обновлён", Toast.LENGTH_SHORT).show()
                    finish() // Закрыть активити
                }
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

