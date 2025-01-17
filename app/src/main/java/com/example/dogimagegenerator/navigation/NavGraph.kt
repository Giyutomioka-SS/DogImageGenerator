package com.example.dogimagegenerator.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

import com.example.dogimagegenerator.ui.appScreens.GenerateDogsImages
import com.example.dogimagegenerator.ui.appScreens.HomeScreen
import com.example.dogimagegenerator.ui.appScreens.SavedDogImages
import com.example.dogimagegenerator.viewModel.GenerateDogImagesViewModel
import com.example.dogimagegenerator.viewModel.SavedDogImagesViewModel
import com.example.dogimagegenerator.viewModel.HomeViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    // Defines the navigation graph with transitions
    NavHost(
        navController = navController,
        startDestination = "/home",
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },


        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ) {
        // Home screen route
        composable(route="/home") {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                onNavigateToGenerateDogImages = { navController.navigate("/generateDogsImages") },
                onNavigateToSavedDogImages = { navController.navigate("/savedDogImages") },
                viewModel=homeViewModel
            )
        }
        // Generate dogs images screen route
        composable("/generateDogsImages") {
            val generateDogImagesViewModel: GenerateDogImagesViewModel = viewModel()
            GenerateDogsImages(
                onNavigateBack = { navController.popBackStack() },
                viewModel = generateDogImagesViewModel
            )
        }
        // Saved dog images screen route
        composable("/savedDogImages") {
            val savedDogImagesViewModel: SavedDogImagesViewModel = viewModel()
            SavedDogImages(
                onNavigateBack = { navController.popBackStack() },
                viewModel = savedDogImagesViewModel
            )
        }
    }
}