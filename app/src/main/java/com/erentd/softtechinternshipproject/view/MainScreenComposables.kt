package com.erentd.softtechinternshipproject.view

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.erentd.softtechinternshipproject.model.CharacterModel
import com.erentd.softtechinternshipproject.model.EpisodeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(
            text = "Rick and Morty",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun CharacterList(
    state: CharacterListState,
    viewModel: CharacterListView,
    sharedPreferences: SharedPreferences
) {
    val characters = state.characters
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(characters.size) {i ->
            val character = characters[i]
            if (i >= characters.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            CharacterRow(character = character, sharedPreferences = sharedPreferences)
        }
        item {
            if (state.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun CharacterRow(character: CharacterModel, sharedPreferences: SharedPreferences) {
    val viewModel = viewModel<EpisodeListView>()
    val state = viewModel.state
    Card(modifier = Modifier
        .fillMaxWidth(0.98F)
        .background(color = MaterialTheme.colorScheme.background)
        .padding(all = 5.dp)
    ) {
        Row {
            AsyncImage(
                model = character.image,
                contentDescription = "Image of ${character.name}",
            )
            Column (modifier = Modifier.fillMaxHeight()) {
                Row {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 5.dp).fillMaxSize(0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    FavoriteButton(character = character.id, sharedPreferences = sharedPreferences)
                }
                Text(
                    text = character.gender,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 5.dp)
                )
                LastEpisodesList(state = state, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63),
    character: Int,
    sharedPreferences: SharedPreferences
) {
    //TODO: Add or remove an entry with character in the sharedPreferences
    var isFavorite by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }
}

@Composable
fun LastEpisodesList(state: EpisodeListState, viewModel: EpisodeListView) {
    Column (verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Last 3 appearances of character",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 5.dp)
        )
        LazyRow(contentPadding = PaddingValues(start = 5.dp)) {
            items(state.episodes.size) {i ->
                val episode = state.episodes[i]
                if (i >= state.episodes.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                LastEpisodesEntry(episode)
            }
            item {
                if (state.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun LastEpisodesEntry(episode : EpisodeModel) {
    Text(
        text = episode.episode,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(5.dp)
    )
}