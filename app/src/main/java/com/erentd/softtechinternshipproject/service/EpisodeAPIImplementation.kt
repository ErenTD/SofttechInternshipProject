package com.erentd.softtechinternshipproject.service

import com.erentd.softtechinternshipproject.model.EpisodeModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class EpisodeAPIImplementation {
    suspend fun getEpisode(
        epNumber: Int
    ): Result<List<EpisodeModel>> {
        val baseURL = "https://rickandmortyapi.com/api/"

        val episodeRetrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(EpisodeAPI::class.java)

        return Result.success(
            listOf(
                episodeGenerator(episodeRetrofit.getData(epNumber))
            )
        )
    }

    private fun episodeGenerator(episodeResponse: Response<EpisodeModel>): EpisodeModel {
        return if (episodeResponse.isSuccessful) {
            episodeResponse.body()!!
        } else {
            EpisodeModel(
                0,
                "-",
                "-",
                "SXXEXX",
                listOf("-"),
                "-",
                "-"
            )
        }
    }
}
