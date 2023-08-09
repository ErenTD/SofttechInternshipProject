package com.erentd.softtechinternshipproject.service

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.erentd.softtechinternshipproject.model.CharacterModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun characterAPIImplementation(characterModels : SnapshotStateList<CharacterModel>) {
    val baseURL = "https://rickandmortyapi.com/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(CharacterAPI::class.java)

    getPage(1, characterModels, retrofit)
}

fun getPage(
    characterPage : Int,
    characterModels : SnapshotStateList<CharacterModel>,
    retrofit : CharacterAPI
) {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }

    CoroutineScope(Dispatchers.IO).launch {
        val response = retrofit.getData(characterPage)

        withContext(Dispatchers.Main + exceptionHandler) {
            if (response.isSuccessful) {
                response.body()?.let {
                    characterModels.addAll(it.results)
                    // TODO("Implement proper paging instead of recursion")
                    getPage(characterPage + 1, characterModels, retrofit)
                }
            }
        }
    }
}
