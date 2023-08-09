package com.erentd.softtechinternshipproject.service

import com.erentd.softtechinternshipproject.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterAPI {
    // https://rickandmortyapi.com/api/character
    @GET("character")
    suspend fun getData(
        @Query("page") page: Int
    ): Response<ResponseModel>
}