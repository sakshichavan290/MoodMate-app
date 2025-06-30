package com.example.moodmate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodmate.screens.*
import com.example.moodmate.viewmodel.MoodViewModel

@Composable
fun AppNavigation(viewModel: MoodViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("log") { LogMoodScreen(viewModel, navController) }
        composable("history") { HistoryScreen(viewModel,navController) }
        composable("analytics") { AnalyticsScreen(viewModel,navController) }
        composable("community") { CommunityScreen(viewModel,navController) }
    }
}
