package com.example.moodmate.screens
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.sp
import com.example.moodmate.R


@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier=Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id= R.drawable.bg_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(
            onClick = { navController.navigate("log") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,          // Button background: white
                contentColor = Color(0xFFCE73F7)       // Button text: your purple
            )
        ){
            Text("Log Mood")
        }
        Button(
            onClick = { navController.navigate("history") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,          // Button background: white
                contentColor = Color(0xFFCE73F7)       // Button text: your purple
            )
        ){
            Text(text="View History")
        }
        Button(
            onClick = { navController.navigate("analytics") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,          // Button background: white
                contentColor = Color(0xFFCE73F7)       // Button text: your purple
            )
        ) {
            Text(text="Analytics")
        }
        Button(
            onClick = { navController.navigate("community") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,          // Button background: white
                contentColor = Color(0xFFCE73F7)       // Button text: your purple
            )
        ) {
            Text(text="Community")
        }
    }


}