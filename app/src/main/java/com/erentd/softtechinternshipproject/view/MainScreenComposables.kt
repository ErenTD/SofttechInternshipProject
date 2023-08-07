package com.erentd.softtechinternshipproject.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erentd.softtechinternshipproject.model.CharacterModel

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
    // TODO
    Card(modifier = Modifier
        .fillMaxWidth(0.98F)
        .background(color = MaterialTheme.colorScheme.background)
        .padding(all = 5.dp)
    ) {
        Column {
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = character.gender,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}