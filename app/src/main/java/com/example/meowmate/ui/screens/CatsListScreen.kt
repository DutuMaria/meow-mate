package com.example.meowmate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meowmate.R
import com.example.meowmate.ui.components.*
import com.example.meowmate.ui.state.CatsUiState
import com.example.meowmate.ui.viewmodel.CatsViewModel

@Composable
fun CatsListScreen(
    vm: CatsViewModel = hiltViewModel(),
    onOpenSettings: () -> Unit,
    onOpenDetails: (String) -> Unit
) {
    val ui by vm.ui.collectAsState()
    val query by vm.query.collectAsState()
    val refreshing = ui is CatsUiState.Loading

    Scaffold(
        topBar = {
            MeowMateAppBar(onOpenSettings)
        }
    ) { pad ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { vm.refresh() },                // apelul tău de reload
            modifier = Modifier.padding(pad)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CatSearchBar(
                    query = query,
                    onQueryChange = vm::onQueryChange,
                    modifier = Modifier.fillMaxWidth()
                )

                when (ui) {
                    is CatsUiState.Success -> {
                        CatsGrid(
                            items = (ui as CatsUiState.Success).items,
                            onClick = { id -> if (id.isNotBlank()) onOpenDetails(id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    CatsUiState.Empty -> {
                        EmptyState(
                            message = stringResource(R.string.no_results),
                            onRetry = { vm.refresh() }
                        )
                    }
                    is CatsUiState.Error -> {
                        val err = ui as CatsUiState.Error
                        if (err.cached.isNotEmpty()) {
                            ErrorBanner(err.message)
                            Spacer(Modifier.height(8.dp))
                            CatsGrid(
                                items = err.cached,
                                onClick = onOpenDetails,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            ErrorState(message = err.message, onRetry = { vm.refresh() })
                        }
                    }
                    CatsUiState.Loading -> LoadingGridPlaceholder()
                }
            }
        }
    }
}

