package com.erentd.softtechinternshipproject.service

import com.erentd.softtechinternshipproject.model.EpisodeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeAPI {
    // https://rickandmortyapi.com/api/episode/{epNumber}
    @GET("episode/{epNumber}")
    fun getData(
        @Path("epNumber") epNumber: Int
    ): Call<EpisodeModel>
}