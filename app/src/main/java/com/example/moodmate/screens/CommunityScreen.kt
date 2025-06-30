package com.example.moodmate.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodmate.R
import com.example.moodmate.viewmodel.MoodViewModel

@Composable
fun CommunityScreen(viewModel: MoodViewModel, navController: NavController) {
    val communityMoods by viewModel.communityMoodList.collectAsState()
    var showCommentDialog by remember { mutableStateOf(false) }
    var currentMoodForComment by remember { mutableStateOf<MoodViewModel.Mood?>(null) }
    var newComment by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.fetchCommunityMoods() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.home_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Community Moods", style = MaterialTheme.typography.headlineSmall, color = Color(0xFFCE73F7))
            Spacer(modifier = Modifier.height(16.dp))

            if (communityMoods.isEmpty()) {
                Text("No moods shared yet.", color = Color.White)
            } else {
                LazyColumn {
                    items(communityMoods.reversed()) { mood ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Profile row
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_face_24),
                                        contentDescription = "Profile",
                                        modifier = Modifier.size(32.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = mood.username, style = MaterialTheme.typography.bodyMedium)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(text = mood.mood, fontSize = 20.sp)
                                if (mood.note.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = mood.note, fontSize = 16.sp, color = Color.DarkGray)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(onClick = {
                                        viewModel.likeMood(mood)
                                    }) {
                                        Text("❤️ ${mood.likes}")
                                    }
                                    TextButton(onClick = {
                                        currentMoodForComment = mood
                                        showCommentDialog = true
                                    }) {
                                        Text("💬 ${mood.comments.size}")
                                    }
                                }

                                if (mood.comments.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    mood.comments.takeLast(2).forEach {
                                        Text("- $it", fontSize = 14.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Comment dialog
        if (showCommentDialog) {
            AlertDialog(
                onDismissRequest = { showCommentDialog = false },
                title = { Text("Add Comment") },
                text = {
                    OutlinedTextField(
                        value = newComment,
                        onValueChange = { newComment = it },
                        label = { Text("Your comment") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        currentMoodForComment?.let {
                            viewModel.addComment(it, newComment)
                        }
                        newComment = ""
                        showCommentDialog = false
                    }) {
                        Text("Post")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showCommentDialog = false
                        newComment = ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
