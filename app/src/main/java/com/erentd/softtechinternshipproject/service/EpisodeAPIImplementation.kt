package com.erentd.softtechinternshipproject.service

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.erentd.softtechinternshipproject.model.EpisodeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun episodeAPIImplementation(episodeNumber : Int, episodeModels : SnapshotStateList<EpisodeModel>) {
    val baseURL = "https://rickandmortyapi.com/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(EpisodeAPI::class.java)

    val call = retrofit.getData(episodeNumber)

    call.enqueue(object: Callback<EpisodeModel> {
        override fun onResponse(call: Call<EpisodeModel>, response: Response<EpisodeModel>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    episodeModels.add(it)
                }
            }
        }

        override fun onFailure(call: Call<EpisodeModel>, t: Throwable) {
            t.printStackTrace()
        }
    })
}
