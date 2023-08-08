package com.erentd.softtechinternshipproject.service

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.erentd.softtechinternshipproject.model.CharacterModel
import com.erentd.softtechinternshipproject.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    val call = retrofit.getData(characterPage)

    call.enqueue(object: Callback<ResponseModel> {
        override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    characterModels.addAll(it.results)
                    // TODO("Implement proper paging instead of recursion")
                    getPage(characterPage + 1, characterModels, retrofit)
                }
            }
        }

        override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            t.printStackTrace()
        }
    })
}
