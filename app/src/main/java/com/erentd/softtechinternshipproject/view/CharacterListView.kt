package com.erentd.softtechinternshipproject.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erentd.softtechinternshipproject.model.CharacterModel
import com.erentd.softtechinternshipproject.service.CharacterAPIImplementation
import com.erentd.softtechinternshipproject.service.DefaultPaginator
import kotlinx.coroutines.launch

class CharacterListView(characterApi: CharacterAPIImplementation): ViewModel() {
    var state by mutableStateOf(CharacterListState())

    private val paginator = DefaultPaginator (
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            characterApi.getCharacters(nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { characters, newKey ->
            state = state.copy(
                characters = state.characters + characters,
                page = newKey,
                endReached = characters.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}

data class CharacterListState(
    val isLoading: Boolean = false,
    val characters: List<CharacterModel> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)