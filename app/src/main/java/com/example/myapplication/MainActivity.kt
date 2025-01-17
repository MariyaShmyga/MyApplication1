package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверка состояния авторизации
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val userRepository = UserRepository(this)
        val isLoggedIn = userRepository.isLoggedIn()
        Log.d("ProfileFragment", "isLoggedIn = $isLoggedIn before checkup")

        if (!isLoggedIn) {
            // Перенаправляем на экран авторизации, если пользователь не авторизован
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Log.d("MainActivity", "isLoggedIn = $isLoggedIn after chekup")
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация BottomNavigationView
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_cart -> {
                    replaceFragment(CartFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.nav_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // Устанавливаем вкладку "Home" по умолчанию
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
