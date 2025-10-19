package com.example.myapplication.database

import org.junit.Assert.*
import org.junit.Test

class MealDaoTest {
    private val testMeal =
        MealEntity(
            id = 1,
            name = "Test Meal",
            description = "Test Description",
            price = 10.99,
            imageUrl = "test.jpg",
        )

    private val testMeals =
        listOf(
            MealEntity(1, "Meal 1", "Description 1", 10.0),
            MealEntity(2, "Meal 2", "Description 2", 15.0),
            MealEntity(3, "Meal 3", "Description 3", 20.0),
        )

    @Test
    fun `MealEntity should have correct properties`() {
        assertEquals(1, testMeal.id)
        assertEquals("Test Meal", testMeal.name)
        assertEquals("Test Description", testMeal.description)
        assertEquals(10.99, testMeal.price, 0.01)
        assertEquals("test.jpg", testMeal.imageUrl)
    }

    @Test
    fun `MealEntity should handle empty imageUrl`() {
        val meal =
            MealEntity(
                id = 2,
                name = "Another Meal",
                description = "Another Description",
                price = 15.50,
            )

        assertEquals("", meal.imageUrl)
    }

    @Test
    fun `MealEntity should handle zero price`() {
        val meal =
            MealEntity(
                id = 3,
                name = "Free Meal",
                description = "No cost meal",
                price = 0.0,
            )

        assertEquals(0.0, meal.price, 0.01)
    }

    @Test
    fun `MealEntity should handle long names`() {
        val longName = "This is a very long meal name that should still work correctly"
        val meal =
            MealEntity(
                id = 4,
                name = longName,
                description = "Description",
                price = 12.99,
            )

        assertEquals(longName, meal.name)
    }

    @Test
    fun `MealEntity should handle special characters in description`() {
        val description = "Meal with special chars: @#$%^&*()_+-=[]{}|;':\",./<>?"
        val meal =
            MealEntity(
                id = 5,
                name = "Special Meal",
                description = description,
                price = 8.99,
            )

        assertEquals(description, meal.description)
    }

    @Test
    fun `testMeals list should have correct size`() {
        assertEquals(3, testMeals.size)
    }

    @Test
    fun `testMeals should be ordered by id`() {
        val ids = testMeals.map { it.id }
        assertEquals(listOf(1, 2, 3), ids)
    }

    @Test
    fun `testMeals should have increasing prices`() {
        val prices = testMeals.map { it.price }
        assertTrue(prices[0] < prices[1])
        assertTrue(prices[1] < prices[2])
    }
}
