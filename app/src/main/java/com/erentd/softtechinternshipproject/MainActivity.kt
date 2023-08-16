package com.erentd.softtechinternshipproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erentd.softtechinternshipproject.ui.theme.SofttechInternshipProjectTheme
import com.erentd.softtechinternshipproject.view.AppBar
import com.erentd.softtechinternshipproject.view.CharacterList
import com.erentd.softtechinternshipproject.view.CharacterListView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(
            "com.erentd.softtechinternshipproject",
            Context.MODE_PRIVATE
        )
        setContent {
            SofttechInternshipProjectTheme {
                MainScreen(sharedPreferences)
            }
        }
    }
}

@Composable
fun MainScreen(sharedPreferences: SharedPreferences) {
    val viewModel = viewModel<CharacterListView>()
    val state = viewModel.state

    Scaffold(topBar = {AppBar()}) {
        Column (modifier = Modifier.padding(it)) {
            CharacterList(
                state = state,
                viewModel = viewModel,
                sharedPreferences = sharedPreferences
            )
        }
    }
}
