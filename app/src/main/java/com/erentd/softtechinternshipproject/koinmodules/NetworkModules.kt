package com.erentd.softtechinternshipproject.koinmodules

import com.erentd.softtechinternshipproject.view.CharacterListView
import com.erentd.softtechinternshipproject.view.EpisodeListView
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    viewModel { p ->
        EpisodeListView(character = p[0], episodeApi = get())
    }
    viewModel {
        CharacterListView(characterApi = get())
    }
}