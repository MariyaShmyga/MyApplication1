package com.example.myapplication
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class User(val email: String, val password: String, val name: String, val isAdmin: Boolean = false)

class UserRepository(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val usersKey = "users"

    fun getUserByEmail(email: String): User? {
        val users = getAllUsers()
        return users.find { it.email == email }
    }

    fun setLoggedInUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString("loggedInUser", userJson).apply()
    }

    fun getLoggedInUser(): User? {
        val userJson = sharedPreferences.getString("loggedInUser", null)
        return if (userJson != null) gson.fromJson(userJson, User::class.java) else null
    }

    private fun saveUsers(users: List<User>) {
        val usersJson = gson.toJson(users)
        sharedPreferences.edit().putString(usersKey, usersJson).apply()
    }

    fun setLoggedInState(isLoggedIn: Boolean) {
        Log.d("UserRepository", "setLoggedInState: $isLoggedIn")
        sharedPreferences.edit()
            .putBoolean("isLoggedIn", isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        val state = sharedPreferences.getBoolean("isLoggedIn", false)
        Log.d("UserRepository", "isLoggedIn(): $state")
        return state
    }

    fun validateUser(email: String, password: String): Boolean {
        val usersJson = sharedPreferences.getString("users", null) ?: return false
        val users: List<User> = Gson().fromJson(usersJson, object : TypeToken<List<User>>() {}.type)
        return users.any { it.email == email && it.password == password}
    }

    fun addUser(user: User): Boolean {
        val usersJson = sharedPreferences.getString("users", null)
        val users: MutableList<User> = if (usersJson != null) {
            Gson().fromJson(usersJson, object : TypeToken<MutableList<User>>() {}.type)
        } else {
            mutableListOf()
        }

        if (users.any { it.email == user.email }) {
            return false
        }

        users.add(user)
        sharedPreferences.edit().putString("users", Gson().toJson(users)).apply()
        return true
    }
    fun getAllUsers(): List<User> {
        val usersJson = sharedPreferences.getString(usersKey, null)
        return if (usersJson != null) {
            gson.fromJson(usersJson, Array<User>::class.java).toList()
        } else {
            emptyList()
        }
    }

    fun updateUser(oldEmail: String, updatedUser: User) {
        val users = getAllUsers().toMutableList()
        val index = users.indexOfFirst { it.email == oldEmail }
        if (index != -1) {
            users[index] = updatedUser
            saveUsers(users)
        }
    }
}

