package com.example.meowmate.ui.state

import com.example.meowmate.domain.model.CatItem

data class CatDetailUiState(
    val isLoading: Boolean = false,
    val item: CatItem? = null,
    val error: String? = null
)
