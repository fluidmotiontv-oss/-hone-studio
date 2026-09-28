package com.example.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.ComicBuilderScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MusicStudioScreen
import com.example.ui.screens.VectorStudioScreen
import kotlinx.serialization.Serializable

@Serializable
object DashboardRoute

@Serializable
object VectorStudioRoute

@Serializable
object MusicStudioRoute

@Serializable
object ComicBuilderRoute

@Composable
fun StudioAIApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = DashboardRoute) {
        composable<DashboardRoute> {
            DashboardScreen(
                onNavigateToVector = { navController.navigate(VectorStudioRoute) },
                onNavigateToMusic = { navController.navigate(MusicStudioRoute) },
                onNavigateToComic = { navController.navigate(ComicBuilderRoute) }
            )
        }
        composable<VectorStudioRoute> {
            VectorStudioScreen(onBack = { navController.popBackStack() })
        }
        composable<MusicStudioRoute> {
            MusicStudioScreen(onBack = { navController.popBackStack() })
        }
        composable<ComicBuilderRoute> {
            ComicBuilderScreen(onBack = { navController.popBackStack() })
        }
    }
}
