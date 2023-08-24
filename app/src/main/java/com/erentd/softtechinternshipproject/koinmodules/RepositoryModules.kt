package com.erentd.softtechinternshipproject.koinmodules

import com.erentd.softtechinternshipproject.service.CharacterAPIImplementation
import com.erentd.softtechinternshipproject.service.EpisodeAPIImplementation
import org.koin.dsl.module

val repositoryModule = module {
    single {
        EpisodeAPIImplementation()
    }
    single {
        CharacterAPIImplementation()
    }
}