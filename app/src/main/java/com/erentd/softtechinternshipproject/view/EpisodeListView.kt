package com.erentd.softtechinternshipproject.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erentd.softtechinternshipproject.model.CharacterModel
import com.erentd.softtechinternshipproject.model.EpisodeModel
import com.erentd.softtechinternshipproject.service.DefaultPaginator
import com.erentd.softtechinternshipproject.service.EpisodeAPIImplementation
import kotlinx.coroutines.launch

class EpisodeListView(character: CharacterModel, episodeApi: EpisodeAPIImplementation) :
    ViewModel() {
    private fun getEpisodesList(character: CharacterModel): List<Int> {
        val episodeIds: ArrayList<Int> = arrayListOf()
        for (episode in character.episode.reversed()) {
            if (episodeIds.size >= 3) break
            episodeIds.add(
                Regex("(?<episodeId>[0-9]+)$")
                    .find(episode)!!
                    .groups["episodeId"]
                    ?.value!!
                    .toInt()
            )
        }
        while (episodeIds.size < 3) {
            episodeIds.add(0)
        }
        return episodeIds.toList()
    }

    var state by mutableStateOf(EpisodeListState(episodeIds = getEpisodesList(character)))

    private val paginator = DefaultPaginator(
        initialKey = state.episode,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextEpisode ->
            episodeApi.getEpisode(state.episodeIds[nextEpisode])
        },
        getNextKey = {
            state.episode + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { episodes, newKey ->
            state = state.copy(
                episodes = state.episodes + episodes,
                episode = newKey,
                endReached = state.episode == 2
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

data class EpisodeListState(
    val isLoading: Boolean = false,
    val episodes: List<EpisodeModel> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val episode: Int = 0,
    val episodeIds: List<Int> = emptyList()
)