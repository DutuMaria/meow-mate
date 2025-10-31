package com.example.meowmate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowmate.domain.usecase.GetCatByIdUseCase
import com.example.meowmate.ui.state.CatDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val getCatById: GetCatByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CatDetailUiState(isLoading = true))
    val state: StateFlow<CatDetailUiState> = _state.asStateFlow()

    fun load(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = getCatById(id)
            _state.value = result.fold(
                onSuccess = { CatDetailUiState(isLoading = false, item = it) },
                onFailure = { CatDetailUiState(isLoading = false, error = it.message) }
            )
        }
    }
}
