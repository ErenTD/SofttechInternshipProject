package com.erentd.softtechinternshipproject.service

import com.erentd.softtechinternshipproject.model.CharacterModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CharacterAPIImplementation {
    suspend fun getCharacters(
        page: Int,
    ) : Result<List<CharacterModel>> {
        val baseURL = "https://rickandmortyapi.com/api/"

        val characterRetrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CharacterAPI::class.java)

        val characterResponse = characterRetrofit.getData(page)

        return if (characterResponse.isSuccessful) {
            Result.success(characterResponse.body()!!.results)
        } else Result.success(emptyList())
    }
}