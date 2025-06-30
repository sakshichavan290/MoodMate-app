package com.example.moodmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.moodmate.ui.theme.MoodMateTheme
import com.example.moodmate.viewmodel.MoodViewModel
import com.example.moodmate.AppNavigation

class MainActivity : ComponentActivity() {
    private val moodViewModel = MoodViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodMateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(viewModel = moodViewModel)
                }
            }
        }
    }
}
