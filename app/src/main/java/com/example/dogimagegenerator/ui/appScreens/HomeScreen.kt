package com.example.dogimagegenerator.ui.appScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dogimagegenerator.ui.theme.blue
import com.example.dogimagegenerator.ui.theme.darkBlue
import com.example.dogimagegenerator.ui.theme.lightBlue
import com.example.dogimagegenerator.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    onNavigateToGenerateDogImages: () -> Unit,
    onNavigateToSavedDogImages: () -> Unit,
    viewModel: HomeViewModel
) {
    // Define gradient colors for the background
    val gradientColors = listOf(
        darkBlue,
        blue,
        lightBlue
    )

    var visible by remember { mutableStateOf(false) }
    // Trigger visibility change on screen load
    LaunchedEffect(Unit) {
        visible = true
    }

    // Box for the entire screen layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(gradientColors)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated title card
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { -40 }
                )
            ) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Random Dog Image Generator!",
                        modifier = Modifier.padding(14.dp),
                        color = Color(0xFF1565C0),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(200.dp))

            // Animated button container
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 40 }
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Button to navigate to generate dog images
                    ElevatedButton(
                        onClick = onNavigateToGenerateDogImages,
                        modifier = Modifier
                            .width(240.dp)
                            .height(40.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(28.dp)
                            ),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF2196F3)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = "Generate Dog Image!",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Button to navigate to saved dog images
                    ElevatedButton(
                        onClick = onNavigateToSavedDogImages,
                        modifier = Modifier
                            .width(240.dp)
                            .height(40.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(28.dp)
                            ),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF1976D2)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = "Saved Dog Images",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}