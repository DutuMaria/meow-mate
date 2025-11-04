package com.example.meowmate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowmate.domain.repository.CatsRepository
import com.example.meowmate.domain.usecase.GetCatsUseCase
import com.example.meowmate.ui.state.CatsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatsViewModel @Inject constructor(
    private val getCats: GetCatsUseCase,
    private val repo: CatsRepository
) : ViewModel() {

    private val _ui = MutableStateFlow<CatsUiState>(CatsUiState.Loading)
    val ui: StateFlow<CatsUiState> = _ui

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init { refresh() }

    fun onQueryChange(newQ: String) {
        _query.value = newQ
        refresh(newQ)
    }

    fun refresh(q: String = _query.value) {
        viewModelScope.launch {
            val cache = repo.cachedCats(q)
            if (cache.isNotEmpty()) _ui.value = CatsUiState.Success(cache)
            else _ui.value = CatsUiState.Loading

            runCatching { getCats(q) }
                .onSuccess { fresh ->
                    _ui.value = if (fresh.isEmpty()) CatsUiState.Empty
                    else CatsUiState.Success(fresh)
                }
                .onFailure { e ->
                    if (cache.isEmpty())
                        _ui.value = CatsUiState.Error(e.message ?: "Network error", emptyList())
                    else
                        _ui.value = CatsUiState.Error(e.message ?: "Network error", cache)
                }
        }
    }
}
