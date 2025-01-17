package com.example.dogimagegenerator.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoDogImage {
    //Fetches the 20 most recent dog images from the database.
    @Query("SELECT * FROM dog_images ORDER BY id DESC LIMIT 20")
    fun getRecentDogImages(): Flow<List<DogImage>>

    //Inserts a DogImage into the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogImage(dogImage: DogImage)

    //Deletes all entries from the dog_images table.
    @Query("DELETE FROM dog_images")
    suspend fun clearDogImages()
}
