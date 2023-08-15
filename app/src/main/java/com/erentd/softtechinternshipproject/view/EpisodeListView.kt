package com.erentd.softtechinternshipproject.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erentd.softtechinternshipproject.model.EpisodeModel
import com.erentd.softtechinternshipproject.service.DefaultPaginator
import com.erentd.softtechinternshipproject.service.EpisodeAPIImplementation
import kotlinx.coroutines.launch

class EpisodeListView: ViewModel() {
    private var episodeIds = listOf(1,2,3) // TODO: Pass parameters here (Make sure list.size >= 3)
    var state by mutableStateOf(EpisodeListState(episodeIds = episodeIds))
    private val episodeApi = EpisodeAPIImplementation()

    private val paginator = DefaultPaginator (
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