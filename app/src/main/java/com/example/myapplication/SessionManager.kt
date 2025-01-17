package com.example.myapplication

import android.content.Context

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AppSession", Context.MODE_PRIVATE)

    fun saveCurrentUser(email: String) {
        sharedPreferences.edit().putString("currentUser", email).apply()
    }

    fun getCurrentUser(): String? {
        return sharedPreferences.getString("currentUser", null)
    }

    fun clearCurrentUser() {
        sharedPreferences.edit().remove("currentUser").apply()
    }
}
