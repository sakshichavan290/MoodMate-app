package com.example.moodmate.screens

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodmate.R
import com.example.moodmate.viewmodel.MoodViewModel

@Composable
fun LogMoodScreen(viewModel: MoodViewModel, navController: NavController) {

    val moods = listOf("😊 Happy", "😢 Sad", "😡 Angry", "😌 Calm", "🤩 Excited")
    var selectedMood by remember { mutableStateOf("") }
    var note by remember { mutableStateOf(TextFieldValue("")) }

    val poppins = FontFamily(Font(R.font.poppins_regular))
    val context = LocalContext.current // ✅ Correct Context

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
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f))
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.White.copy(alpha = 0.7f), Color(0xFFE1BEE7).copy(alpha = 0.6f))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "How are you feeling?",
                            fontFamily = poppins,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFCE73F7)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        moods.forEach { mood ->
                            val animatedSize by animateDpAsState(
                                targetValue = if (selectedMood == mood) 28.dp else 24.dp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = selectedMood == mood,
                                    onClick = { selectedMood = mood },
                                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFCE73F7))
                                )
                                Text(
                                    text = mood,
                                    fontFamily = poppins,
                                    fontSize = if (selectedMood == mood) 18.sp else 16.sp,
                                    fontWeight = if (selectedMood == mood) FontWeight.Bold else FontWeight.Normal,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Write a note (optional):",
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            color = Color(0xFFCE73F7)
                        )

                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            placeholder = { Text("Type here...", fontFamily = poppins) },
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontFamily = poppins)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                viewModel.saveMood(context, selectedMood, note.text)
                                navController.popBackStack()
                            },
                            enabled = selectedMood.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCE73F7),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                        ) {
                            Text("Save Mood", fontFamily = poppins)
                        }
                    }
                }
            }
        }
    }
}
