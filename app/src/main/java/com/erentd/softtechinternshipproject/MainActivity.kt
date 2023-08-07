package com.erentd.softtechinternshipproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.erentd.softtechinternshipproject.model.CharacterLocation
import com.erentd.softtechinternshipproject.model.CharacterModel
import com.erentd.softtechinternshipproject.model.ResponseModel
import com.erentd.softtechinternshipproject.service.CharacterAPI
import com.erentd.softtechinternshipproject.ui.theme.SofttechInternshipProjectTheme
import com.erentd.softtechinternshipproject.view.AppBar
import com.erentd.softtechinternshipproject.view.CharacterList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SofttechInternshipProjectTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var characterModels = remember { mutableStateListOf<CharacterModel>()}

    val baseURL = "https://rickandmortyapi.com/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CharacterAPI::class.java)

    val call = retrofit.getData()

    call.enqueue(object: Callback<ResponseModel> {
        override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    characterModels.addAll(it.results)
                }
            }
        }

        override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            t.printStackTrace()
        }

    })

    Scaffold(topBar = {AppBar()}) {
        Column (modifier = Modifier.padding(it)) {
            CharacterList(characters = characterModels)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SofttechInternshipProjectTheme {
        val chars = listOf(
            CharacterModel(
                id = 0,
                name = "Test",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = CharacterLocation("Earth (C-137)", ""),
                location = CharacterLocation("Earth (C-137)", ""),
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = listOf("3","4"),
                created = "2017-11-04T18:48:46.250Z"
            ),
            CharacterModel(
                id = 1,
                name = "Test1",
                status = "Dead",
                species = "Humanoid",
                type = "",
                gender = "Male",
                origin = CharacterLocation("Earth (C-137)", ""),
                location = CharacterLocation("Earth (C-137)", ""),
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = listOf("2","3"),
                created = "2017-11-04T18:48:46.250Z"
            ),
            CharacterModel(
                id = 2,
                name = "Test2",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Female",
                origin = CharacterLocation("Earth (C-137)", ""),
                location = CharacterLocation("Earth (C-137)", ""),
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = listOf("1","2"),
                created = "2017-11-04T18:48:46.250Z"
            ),
        )
        Scaffold(topBar = {AppBar()}) {
            Column (modifier = Modifier.padding(it)) {
                CharacterList(characters = chars)
            }
        }
    }
}