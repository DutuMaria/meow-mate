package com.example.meowmate.data.settings

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
 private val context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private val _locale = MutableStateFlow(prefs.getString(KEY_LOCALE, "en") ?: "en")
    val localeTag: StateFlow<String> = _locale

    private val _isDark = MutableStateFlow(prefs.getBoolean(KEY_DARK, false))
    val isDarkTheme: StateFlow<Boolean> = _isDark

    private val _dynamic = MutableStateFlow(prefs.getBoolean(KEY_DYNAMIC, false))
    val useDynamicColor: StateFlow<Boolean> = _dynamic

    fun setLocale(tag: String) {
        if (tag == _locale.value) return
        prefs.edit().putString(KEY_LOCALE, tag).apply()
        _locale.value = tag
    }

    fun setDarkTheme(enabled: Boolean) {
        if (enabled == _isDark.value) return
        prefs.edit().putBoolean(KEY_DARK, enabled).apply()
        _isDark.value = enabled
    }

    fun setDynamicColor(enabled: Boolean) {
        if (enabled == _dynamic.value) return
        prefs.edit().putBoolean(KEY_DYNAMIC, enabled).apply()
        _dynamic.value = enabled
    }

    companion object {
        private const val PREFS = "settings_prefs"
        private const val KEY_LOCALE = "locale_tag"     // "en"/"ro""
        private const val KEY_DARK = "theme_dark"       // true/false
        private const val KEY_DYNAMIC = "theme_dynamic" // true/false
    }
}