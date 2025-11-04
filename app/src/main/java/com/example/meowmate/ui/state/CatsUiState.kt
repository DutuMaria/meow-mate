package com.example.meowmate.ui.state


import com.example.meowmate.domain.model.CatItem

sealed interface CatsUiState {
    data object Loading : CatsUiState
    data class Success(val items: List<CatItem>) : CatsUiState
    data object Empty : CatsUiState
    data class Error(val message: String, val cached: List<CatItem> = emptyList()) : CatsUiState
}
