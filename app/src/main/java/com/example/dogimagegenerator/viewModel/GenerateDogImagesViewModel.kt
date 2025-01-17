package com.example.dogimagegenerator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogimagegenerator.data.model.DogImage
import com.example.dogimagegenerator.data.model.ApplicationDatabase
import com.example.dogimagegenerator.data.model.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.UnknownHostException

// ViewModel to handle dog image generation and saving
class GenerateDogImagesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    private val json = Json { ignoreUnknownKeys = true }  // JSON parser with unknown keys ignored

    // Initialize the repository and database access
    init {
        val daoDogImage = ApplicationDatabase.getDatabase(application).daoDogImage()
        repository = Repository(daoDogImage)
    }

    // StateFlow to manage the URL of the generated image
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    // StateFlow to manage the loading state of the image generation
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // StateFlow to handle error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // OkHttpClient instance for making network requests
    private val client = OkHttpClient.Builder()
        .build()

    // Function to trigger dog image generation
    fun generateDogImage() {
        viewModelScope.launch {
            _isLoading.value = true  // Set loading state to true
            _errorMessage.value = null  // Clear any previous error messages
            _imageUrl.value = null  // Clear previous image URL

            try {
                // Fetch a random dog image from the API
                val dogImage = withContext(Dispatchers.IO) {
                    fetchDogImage()  // Perform network call on the IO thread
                }

                // If dog image is retrieved successfully, update the UI state
                dogImage?.let {
                    _imageUrl.value = it.message
                    try {
                        // Attempt to save the image URL in the database
                        repository.insertDogImage(DogImage(imageUrl = it.message))
                    } catch (e: Exception) {
                        // If saving fails, show an error message
                        _errorMessage.value = "Failed to save image: ${e.localizedMessage}"
                    }
                }
            } catch (_: UnknownHostException) {
                // Handle no internet connection
                _errorMessage.value = "No internet connection. Please check your network."
            } catch (e: IOException) {
                // Handle other network-related errors
                _errorMessage.value = "Network error: ${e.localizedMessage}"
            } catch (e: Exception) {
                // Handle generic errors
                _errorMessage.value = "Error: ${e.localizedMessage ?: "Unknown error occurred"}"
            } finally {
                // Set loading state to false after the process is completed
                _isLoading.value = false
            }
        }
    }

    // Function to fetch a random dog image from the API
    private fun fetchDogImage(): DogResponse? {
        // Create a request to fetch random dog image
        val request = Request.Builder()
            .url("https://dog.ceo/api/breeds/image/random")
            .build()

        return try {
            // Execute the request
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()?.let { responseBody ->
                    // Parse the response to DogResponse object
                    try {
                        json.decodeFromString<DogResponse>(responseBody)
                    } catch (e: Exception) {
                        // Throw an error if parsing fails
                        throw Exception("Failed to parse response: ${e.localizedMessage}")
                    }
                } ?: throw Exception("Empty response from server") // Handle empty responses
            } else {
                // Handle server error
                throw IOException("Server returned error: ${response.code}")
            }
        } catch (e: Exception) {
            // Propagate any errors encountered during the request
            throw e
        }
    }

    // Data class to represent the response from the Dog API
    @Serializable
    data class DogResponse(
        val message: String,  // URL of the dog image
        val status: String    // Status of the response (e.g., "success")
    )
}
