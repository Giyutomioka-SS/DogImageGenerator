package com.example.dogimagegenerator.data.model

import kotlinx.coroutines.flow.Flow

// Repository to manage data operations via DAO
class Repository(private val daoDogImage: DaoDogImage) {

    // Fetch recent dog images as a Flow
    fun getRecentDogImages(): Flow<List<DogImage>> {
        return daoDogImage.getRecentDogImages()
    }
    // Insert a new dog image into the database
    suspend fun insertDogImage(dogImage: DogImage) {
        daoDogImage.insertDogImage(dogImage)
    }
    // Clear all dog images from the database
    suspend fun clearDogImages() {
        daoDogImage.clearDogImages()
    }
}
