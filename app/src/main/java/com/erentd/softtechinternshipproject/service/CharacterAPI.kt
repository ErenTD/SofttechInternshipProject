package com.erentd.softtechinternshipproject.service

import com.erentd.softtechinternshipproject.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface CharacterAPI {
    // https://rickandmortyapi.com/api/character
    @GET("character")
    fun getData() : Call<ResponseModel>
}