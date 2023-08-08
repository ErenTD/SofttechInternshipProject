package com.erentd.softtechinternshipproject.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erentd.softtechinternshipproject.model.CharacterModel
import com.erentd.softtechinternshipproject.model.EpisodeModel
import com.erentd.softtechinternshipproject.service.episodeAPIImplementation

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
fun CharacterList(characters: List<CharacterModel>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(characters) {character ->
            CharacterRow(character)
        }
    }
}

@Composable
fun CharacterRow(character: CharacterModel) {
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
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = character.gender,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 5.dp)
                )
                LastEpisodesList(character = character)
            }
        }
    }
}

@Composable
fun LastEpisodesList(character: CharacterModel) {
    var lastEpisodeIndex = character.episode.lastIndex
    val episodes = remember { mutableStateListOf<EpisodeModel>()}
    var episodeCounter = 3
    while (episodeCounter > 0) {
        if (lastEpisodeIndex >= 0) {
            episodeAPIImplementation(lastEpisodeIndex - 3 + episodeCounter, episodes)
        }
        else {
            episodes.add(EpisodeModel(
                0,
                "-",
                "-",
                "SXXEXX",
                listOf("-"),
                "-",
                "-"
            ))
        }
        episodeCounter--
        lastEpisodeIndex--
    }
    Column (verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Last 3 appearances of character",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 5.dp)
        )
        LazyRow (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(episodes.reversed()) {episode ->
                LastEpisodesEntry(episode = episode)
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