package com.example.meowmate.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meowmate.R
import com.example.meowmate.ui.components.BackAppBar
import com.example.meowmate.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    vm: SettingsViewModel = hiltViewModel()
) {
    val isDark by vm.isDark.collectAsState()
    val dynamic by vm.dynamic.collectAsState()
    val locale by vm.locale.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(Unit) {
        vm.requestRecreate.collect {
            activity?.recreate()
        }
    }

    Scaffold(
        topBar = {
            BackAppBar(
                title = stringResource(R.string.settings),
                onBack = onBack
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(stringResource(R.string.theme), style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(
                    selected = !isDark,
                    onClick = { vm.toggleDark(false) },
                    label = { Text(stringResource(R.string.light)) }
                )
                FilterChip(
                    selected = isDark,
                    onClick = { vm.toggleDark(true) },
                    label = { Text(stringResource(R.string.dark)) }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = dynamic, onCheckedChange = { vm.toggleDynamic(it) })
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.dynamic_color))
            }

            HorizontalDivider()

            Text(stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("en" to R.string.english, "ro" to R.string.romanian)
                    .forEach { (tag, labelRes) ->
                        FilterChip(
                            selected = locale == tag,
                            onClick = { vm.changeLanguage(tag) },
                            label = { Text(stringResource(labelRes)) }
                        )
                    }
            }
        }
    }
}


