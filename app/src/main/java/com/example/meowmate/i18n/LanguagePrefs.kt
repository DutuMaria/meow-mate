package com.example.meowmate.i18n

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

object LanguagePrefs {
    private const val PREFS = "settings_prefs"
    private const val KEY_LOCALE = "locale_tag"

    fun get(context: Context): String =
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(KEY_LOCALE, "en") ?: "en"

    fun set(context: Context, tag: String) {
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .edit {
                putString(KEY_LOCALE, tag)
            }
    }
}