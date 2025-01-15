package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Переключатель темы
        val themeSwitch = findPreference<SwitchPreferenceCompat>("dark_theme")
        themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            val isChecked = newValue as Boolean
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }

        // Переключатель звука уведомлений
        val soundSwitch = findPreference<SwitchPreferenceCompat>("notification_sound")
        soundSwitch?.setOnPreferenceChangeListener { _, _ ->
            true
        }

        // Политика конфиденциальности
        val privacyPolicy = findPreference<Preference>("privacy_policy")
        privacyPolicy?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://your-privacy-policy-url.com"))
            startActivity(intent)
            true
        }

        // Связь с поддержкой
        val contactSupport = findPreference<Preference>("contact_support")
        contactSupport?.setOnPreferenceClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("support@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Support Request")
            }
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
            true
        }
    }
}


