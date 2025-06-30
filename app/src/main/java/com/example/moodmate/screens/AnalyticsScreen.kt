package com.example.moodmate.screens

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodmate.R
import com.example.moodmate.viewmodel.MoodViewModel

@Composable
fun AnalyticsScreen(viewModel: MoodViewModel, navController: NavHostController) {
    val moods by viewModel.moodList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMoods()
    }

    val moodCounts = moods.groupingBy { it.mood }.eachCount()
    val totalCount = moodCounts.values.sum().takeIf { it > 0 } ?: 1

    val moodColors = mapOf(
        "😊 Happy" to Color(0xFFFFC107),
        "😢 Sad" to Color(0xFF2196F3),
        "😡 Angry" to Color(0xFFF44336),
        "😌 Calm" to Color(0xFF4CAF50),
        "🤩 Excited" to Color(0xFF9C27B0)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.home_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Centered Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = MaterialTheme.shapes.medium),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Mood Analytics",
                        fontSize = 24.sp,
                        color = Color(0xFFCE73F7),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (moodCounts.isEmpty()) {
                        Text("No data yet. Log some moods to see analytics!")
                    } else {
                        moodCounts.forEach { (mood, count) ->
                            val percent = (count * 100f / totalCount)
                            val animatedPercent by animateFloatAsState(targetValue = percent)

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        mood,
                                        modifier = Modifier.width(90.dp),
                                        color = Color.DarkGray
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .weight(1f)
                                            .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(animatedPercent / 100)
                                                .background(
                                                    moodColors[mood] ?: Color.Gray,
                                                    shape = MaterialTheme.shapes.small
                                                )
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("${animatedPercent.toInt()}%", color = Color.Black)
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        val mostFrequent = moodCounts.maxByOrNull { it.value }?.key ?: "N/A"

                        Text("Most frequent mood: $mostFrequent", style = MaterialTheme.typography.bodyLarge)
                        Text("Total entries: ${moods.size}", style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Recent Moods", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn {
                            items(moods.takeLast(5).reversed()) { mood ->
                                Text("${mood.mood} — ${mood.note}", color = Color.DarkGray)
                                Divider()
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                navController.navigate("history")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE73F7)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("View Full History", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
