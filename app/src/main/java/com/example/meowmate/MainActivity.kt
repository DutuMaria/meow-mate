package com.example.meowmate

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.meowmate.data.settings.SettingsRepository
import com.example.meowmate.i18n.LanguagePrefs
import com.example.meowmate.i18n.withLocale
import com.example.meowmate.navigation.AppNavHost
import com.example.meowmate.ui.theme.MeowMateTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun attachBaseContext(newBase: Context) {
        val tag = LanguagePrefs.get(newBase)          // "en"/"ro"
        val wrapped = newBase.withLocale(tag)
        super.attachBaseContext(wrapped)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDark by settingsRepository.isDarkTheme.collectAsState(initial = false)
            val dynamicColor by settingsRepository.useDynamicColor.collectAsState(initial = false)
            MeowMateTheme(darkTheme = isDark, dynamicColor = dynamicColor) {
                val nav = rememberNavController()
                AppNavHost(nav)
            }
        }
    }
}


