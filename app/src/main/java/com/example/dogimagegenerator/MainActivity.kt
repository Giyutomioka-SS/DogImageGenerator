package com.example.dogimagegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dogimagegenerator.navigation.NavGraph
import com.example.dogimagegenerator.ui.theme.DogImageGeneratorTheme

// Main entry point for the application, inherits from ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for the app, allowing content to extend into the status and navigation bars
        enableEdgeToEdge()

        // Set the content view of the activity with the DogImageGeneratorTheme and NavGraph
        setContent {
            DogImageGeneratorTheme { // Apply the theme to the Composable
                NavGraph() // Set up navigation for the app using NavGraph
            }
        }
    }
}
