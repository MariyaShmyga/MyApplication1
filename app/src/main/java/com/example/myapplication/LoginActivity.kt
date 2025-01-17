package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация userRepository
        userRepository = UserRepository(this)
        // Инициализация sharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)

        // Обработчик кнопки входа
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()


            if (userRepository.validateUser(email, password)) {
                val user = userRepository.getUserByEmail(email)
                if (user != null) {
                    if (userRepository.validateUser(email, password)) {
                        val user = userRepository.getUserByEmail(email)
                        if (user != null) {
                            userRepository.setLoggedInUser(user)
                            sharedPreferences.edit().putString("currentUserEmail", email).apply()
                            userRepository.setLoggedInState(true)

                            if (email == "admin@example.com") {
                                // Если администратор, переходим на панель администратора
                                val intent = Intent(this, AdminActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Если обычный пользователь, переходим на главное меню
                                userRepository.setLoggedInUser(user)
                                sharedPreferences.edit().putString("currentUserEmail", email).apply()
                                userRepository.setLoggedInState(true)
                                Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                                navigateToMain()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Неверные данные для входа", Toast.LENGTH_SHORT).show()
            }
        }

        // Обработчик кнопки регистрации
        binding.registerButton.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
