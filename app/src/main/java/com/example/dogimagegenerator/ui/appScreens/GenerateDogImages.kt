package com.example.dogimagegenerator.ui.appScreens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.dogimagegenerator.ui.theme.blue
import com.example.dogimagegenerator.ui.theme.darkBlue
import com.example.dogimagegenerator.ui.theme.lightBlue
import com.example.dogimagegenerator.viewModel.GenerateDogImagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateDogsImages(
    onNavigateBack: () -> Unit,
    viewModel: GenerateDogImagesViewModel
) {
    // Gradient colors for background
    val gradientColors = listOf(
        darkBlue,
        blue,
        lightBlue
    )

    // State variables for image, loading, and errors
    val imageUrl by viewModel.imageUrl.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            // Top bar with back navigation
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable(
                                    onClick = onNavigateBack,
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                )
                                .padding(end = 50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Back",
                                color = Color(0xFF1565C0),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "Generate Dog Images!",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0),
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = blue,
                ),
                modifier = Modifier
                    .height(76.dp),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated image card (shows loading/error/image)
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight / 2)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                isLoading -> {
                                    CircularProgressIndicator(
                                        color = Color(0xFF2196F3),
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                errorMessage != null -> {
                                    // Display error message
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Error",
                                            color = Color.Red,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = errorMessage ?: "Unknown Error",
                                            color = Color.Red,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Log.e("GenerateDogsImages", "Error: $errorMessage")
                                }
                                imageUrl != null -> {
                                    // Display dog image
                                    Image(
                                        painter = rememberAsyncImagePainter(imageUrl),
                                        contentDescription = "Dog Image",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Button to generate new dog image
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { 40 }
                    )
                ) {
                    ElevatedButton(
                        onClick = { viewModel.generateDogImage() },
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
                            text = "Generate!",
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