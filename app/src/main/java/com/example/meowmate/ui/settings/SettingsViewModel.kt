package com.example.meowmate.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowmate.data.settings.SettingsRepository
import com.example.meowmate.i18n.LanguagePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repo: SettingsRepository
) : ViewModel() {

    private val _locale = MutableStateFlow(LanguagePrefs.get(appContext))
    val locale: StateFlow<String> = _locale

    private val _requestRecreate = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val requestRecreate: SharedFlow<Unit> = _requestRecreate

    fun changeLanguage(tag: String) {
        if (tag == _locale.value) return
        LanguagePrefs.set(appContext, tag)
        _locale.value = tag
        _requestRecreate.tryEmit(Unit)
    }

    val isDark: StateFlow<Boolean> =
        repo.isDarkTheme.stateIn(viewModelScope, SharingStarted.Lazily, false)

    val dynamic: StateFlow<Boolean> =
        repo.useDynamicColor.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleDark(on: Boolean) {
        repo.setDarkTheme(on)
    }

    fun toggleDynamic(on: Boolean) {
        repo.setDynamicColor(on)
    }
}
