package com.example.moodmate.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodmate.R
import com.example.moodmate.viewmodel.MoodViewModel

@Composable
fun HistoryScreen(viewModel: MoodViewModel, navController: NavHostController) {
    val moods by viewModel.moodList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMoods()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.home_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Mood History",
                fontSize = 28.sp,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (moods.isEmpty()) {
                Text(
                    "No mood entries yet. Start logging your moods!",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(moods.reversed()) { mood ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(8.dp, shape = MaterialTheme.shapes.medium),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Mood: ${mood.mood}", fontSize = 18.sp, color = Color.Black)
                                if (mood.note.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Note: ${mood.note}", fontSize = 14.sp, color = Color.DarkGray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
