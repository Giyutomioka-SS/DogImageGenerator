package com.example.dogimagegenerator.data.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

// Defines the Room database configuration and serves as the main access point to the database
@Database(entities = [DogImage::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun daoDogImage(): DaoDogImage

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        /**
         * Singleton implementation to get the database instance.
         * Ensures only one instance of the database is created throughout the app's lifecycle.
         */

        fun getDatabase(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "dog_image_database" // Name of the database file
                ).build()
                INSTANCE = instance // Cache the instance for future use
                instance
            }
        }
    }
}