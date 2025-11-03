package com.example.meowmate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.meowmate.R
import com.example.meowmate.domain.model.CatItem
import com.example.meowmate.ui.components.StarRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailsScreen(
    imageId: String,
    onBack: () -> Unit,
    vm: CatDetailsViewModel = hiltViewModel()
) {
    val ui by vm.state.collectAsState()
    LaunchedEffect(imageId) { vm.load(imageId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        ui.item?.breed?.name ?: stringResource(R.string.app_name),
                        maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when {
            ui.isLoading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            ui.error != null -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.error_prefix, ui.error ?: ""))
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.load(imageId) }) { Text(stringResource(R.string.retry)) }
                }
            }

            ui.item != null -> DetailsContent(
                cat = ui.item!!,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun DetailsContent(cat: CatItem, modifier: Modifier = Modifier) {
    val breed = cat.breed
    val uri = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 10.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = cat.imageUrl,
            contentDescription = breed?.name ?: "Cat",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
                .clip(RoundedCornerShape(20.dp))
        )

        Text(
            text = breed?.name ?: "Unknown",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        breed?.origin?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${stringResource(R.string.origin)}: ")
                    }
                    append(it)
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }

        breed?.temperament?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${stringResource(R.string.temperament)}: ")
                    }
                    append(it)
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }

        breed?.lifeSpan?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${stringResource(R.string.life_span)}: ")
                    }
                    append(it)
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }


        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(35.dp)) {
                StarRating(stringResource(R.string.intelligence), breed?.intelligence)
                StarRating(stringResource(R.string.affection_level), breed?.affectionLevel)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(35.dp)) {
                StarRating(stringResource(R.string.child_friendly), breed?.childFriendly)
                StarRating(stringResource(R.string.social_needs), breed?.socialNeeds)
            }
        }

        if (!breed?.description.isNullOrBlank()) {
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Text(
                stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(breed.description!!, style = MaterialTheme.typography.bodyMedium)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(35.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {
            if (!breed?.wikipediaUrl.isNullOrBlank()) {
                AssistChip(
                    onClick = { uri.openUri(breed.wikipediaUrl!!) },
                    label = { Text(stringResource(R.string.open_wiki)) }
                )
            }
            if (!breed?.vetstreetUrl.isNullOrBlank()) {
                AssistChip(
                    onClick = { uri.openUri(breed.vetstreetUrl!!) },
                    label = { Text(stringResource(R.string.open_vetstreet)) }
                )
            }
        }
    }
}


