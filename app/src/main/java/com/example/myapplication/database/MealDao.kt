package com.example.myapplication.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meals ORDER BY name ASC")
    fun getAllMeals(): Flow<List<MealEntity>>
    
    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealById(id: Int): MealEntity?
    
    @Insert
    suspend fun insertMeal(meal: MealEntity): Long
    
    @Insert
    suspend fun insertMeals(meals: List<MealEntity>)
    
    @Update
    suspend fun updateMeal(meal: MealEntity)
    
    @Delete
    suspend fun deleteMeal(meal: MealEntity)
    
    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()
}
