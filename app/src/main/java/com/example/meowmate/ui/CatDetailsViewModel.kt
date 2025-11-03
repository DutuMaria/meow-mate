package com.example.meowmate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowmate.domain.repository.CatsRepository
import com.example.meowmate.ui.state.CatDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailsViewModel @Inject constructor(
    private val repo: CatsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CatDetailsUiState())
    val state: StateFlow<CatDetailsUiState> = _state

    fun load(imageId: String) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            repo.getCatByImageId(imageId)
                .onSuccess { _state.value = CatDetailsUiState(item = it) }
                .onFailure {
                    _state.value = CatDetailsUiState(error = it.message ?: "Unknown error")
                }
        }
    }
}
