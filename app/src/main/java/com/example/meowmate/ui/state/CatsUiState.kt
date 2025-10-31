package com.example.meowmate.ui.state


import com.example.meowmate.domain.model.CatItem

data class CatsUiState(
    val isLoading: Boolean = false,
    val items: List<CatItem> = emptyList(),
    val query: String = "",
    val error: String? = null
)
