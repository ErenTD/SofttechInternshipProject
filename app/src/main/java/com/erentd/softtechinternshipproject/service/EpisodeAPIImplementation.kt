package com.erentd.softtechinternshipproject.service

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.erentd.softtechinternshipproject.model.EpisodeModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun episodeAPIImplementation(
    episodeNumber : Int,
    episodeModels : SnapshotStateList<EpisodeModel>
) {
    val baseURL = "https://rickandmortyapi.com/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(EpisodeAPI::class.java)

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }

    CoroutineScope(Dispatchers.IO).launch {
        val response = retrofit.getData(episodeNumber)

        withContext(Dispatchers.Main + exceptionHandler) {
            if (response.isSuccessful) {
                response.body()?.let {
                    episodeModels.add(it)
                }
            }
        }
    }
}
