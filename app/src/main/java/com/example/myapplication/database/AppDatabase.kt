package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "restaurant_database",
                    )
                        .addCallback(
                            object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            populateDatabase(database.mealDao())
                                        }
                                    }
                                }
                            },
                        )
                        .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateDatabase(mealDao: MealDao) {
            val sampleMeals =
                listOf(
                    MealEntity(
                        name = "Margherita Pizza",
                        description = "Classic pizza with fresh tomatoes, mozzarella, and basil",
                        price = 12.99,
                    ),
                    MealEntity(
                        name = "Spaghetti Carbonara",
                        description = "Traditional Italian pasta with eggs, cheese, and pancetta",
                        price = 14.50,
                    ),
                    MealEntity(
                        name = "Caesar Salad",
                        description = "Fresh romaine lettuce with parmesan cheese and croutons",
                        price = 9.99,
                    ),
                    MealEntity(
                        name = "Grilled Salmon",
                        description = "Fresh Atlantic salmon with herbs and lemon",
                        price = 18.99,
                    ),
                    MealEntity(
                        name = "Chocolate Cake",
                        description = "Rich chocolate cake with vanilla ice cream",
                        price = 7.50,
                    ),
                )
            mealDao.insertMeals(sampleMeals)
        }
    }
}
