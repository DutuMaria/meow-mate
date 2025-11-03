package com.example.meowmate.i18n

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

fun Context.withLocale(tag: String): Context {
    if (tag.isBlank()) return this
    val locale = Locale.forLanguageTag(tag)
    Locale.setDefault(locale)
    val cfg = Configuration(resources.configuration).apply {
        setLocales(LocaleList(locale))
    }
    return createConfigurationContext(cfg)
}
