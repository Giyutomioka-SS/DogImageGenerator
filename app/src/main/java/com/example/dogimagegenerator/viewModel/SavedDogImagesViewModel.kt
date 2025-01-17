package com.example.dogimagegenerator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogimagegenerator.data.model.DogImage
import com.example.dogimagegenerator.data.model.ApplicationDatabase
import com.example.dogimagegenerator.data.model.Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel to handle the management of saved dog images
class SavedDogImagesViewModel(application: Application) : AndroidViewModel(application) {

    private val model: Repository
    // StateFlow to hold the list of recent dog images
    val recentDogImages: StateFlow<List<DogImage>>

    // Initialize repository and get the recent dog images from the database
    init {
        val daoDogImage = ApplicationDatabase.getDatabase(application).daoDogImage()
        model = Repository(daoDogImage)
        // Get recent dog images and store them in StateFlow, with lazy loading
        recentDogImages = model.getRecentDogImages()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()) // Lazily starts the flow
    }

    // Function to clear all saved dog images from the database
    fun clearDogs() {
        viewModelScope.launch {
            // Call repository function to clear dog images
            model.clearDogImages()
        }
    }
}
