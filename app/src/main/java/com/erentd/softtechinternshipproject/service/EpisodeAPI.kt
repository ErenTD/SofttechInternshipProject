package com.erentd.softtechinternshipproject.service

import com.erentd.softtechinternshipproject.model.EpisodeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeAPI {
    // https://rickandmortyapi.com/api/episode/{epNumber}
    @GET("episode/{epNumber}")
    suspend fun getData(
        @Path("epNumber") epNumber: Int
    ): Response<EpisodeModel>
}