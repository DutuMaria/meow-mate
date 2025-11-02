package com.example.meowmate.i18n

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object Locales {
    fun set(langTag: String) {
        val locales = LocaleListCompat.forLanguageTags(langTag)
        AppCompatDelegate.setApplicationLocales(locales)
    }
}
