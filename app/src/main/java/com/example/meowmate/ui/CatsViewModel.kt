package com.example.meowmate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowmate.domain.usecase.GetCatsUseCase
import com.example.meowmate.ui.state.CatsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val getCats: GetCatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CatsUiState(isLoading = true))
    val state: StateFlow<CatsUiState> = _state.asStateFlow()

    init { refresh(force = false) }

    fun refresh(force: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            getCats(force, query = _state.value.query)
                .catch { e -> _state.value = _state.value.copy(isLoading = false, error = e.message) }
                .collectLatest { result ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        items = result.getOrElse { emptyList() },
                        error = result.exceptionOrNull()?.message
                    )
                }
        }
    }

    fun updateQuery(q: String) {
        _state.value = _state.value.copy(query = q)
        refresh(force = false)
    }
}
