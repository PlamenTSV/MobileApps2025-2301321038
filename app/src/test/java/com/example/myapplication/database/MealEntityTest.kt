package com.example.myapplication.database

import org.junit.Assert.*
import org.junit.Test

class MealEntityTest {
    @Test
    fun `MealEntity should have correct default values`() {
        val meal =
            MealEntity(
                name = "Test Meal",
                description = "Test Description",
                price = 10.99,
            )

        assertEquals(0, meal.id)
        assertEquals("Test Meal", meal.name)
        assertEquals("Test Description", meal.description)
        assertEquals(10.99, meal.price, 0.01)
        assertEquals("", meal.imageUrl)
    }

    @Test
    fun `MealEntity should accept all parameters`() {
        val meal =
            MealEntity(
                id = 1,
                name = "Pizza",
                description = "Delicious pizza",
                price = 15.50,
                imageUrl = "https://example.com/pizza.jpg",
            )

        assertEquals(1, meal.id)
        assertEquals("Pizza", meal.name)
        assertEquals("Delicious pizza", meal.description)
        assertEquals(15.50, meal.price, 0.01)
        assertEquals("https://example.com/pizza.jpg", meal.imageUrl)
    }

    @Test
    fun `MealEntity should handle zero price`() {
        val meal =
            MealEntity(
                name = "Free Meal",
                description = "No cost",
                price = 0.0,
            )

        assertEquals(0.0, meal.price, 0.01)
    }

    @Test
    fun `MealEntity should handle negative price`() {
        val meal =
            MealEntity(
                name = "Discount Meal",
                description = "Negative price",
                price = -5.0,
            )

        assertEquals(-5.0, meal.price, 0.01)
    }

    @Test
    fun `MealEntity should handle empty strings`() {
        val meal =
            MealEntity(
                name = "",
                description = "",
                price = 0.0,
                imageUrl = "",
            )

        assertEquals("", meal.name)
        assertEquals("", meal.description)
        assertEquals("", meal.imageUrl)
    }

    @Test
    fun `MealEntity should handle special characters in name`() {
        val meal =
            MealEntity(
                name = "Café & Restaurant",
                description = "Special chars: @#$%",
                price = 12.99,
            )

        assertEquals("Café & Restaurant", meal.name)
        assertEquals("Special chars: @#$%", meal.description)
    }

    @Test
    fun `MealEntity should handle long strings`() {
        val longName = "A".repeat(1000)
        val longDescription = "B".repeat(2000)
        val longImageUrl = "C".repeat(500)

        val meal =
            MealEntity(
                name = longName,
                description = longDescription,
                price = 25.99,
                imageUrl = longImageUrl,
            )

        assertEquals(longName, meal.name)
        assertEquals(longDescription, meal.description)
        assertEquals(longImageUrl, meal.imageUrl)
    }

    @Test
    fun `MealEntity should handle decimal precision`() {
        val meal =
            MealEntity(
                name = "Precision Test",
                description = "Testing decimal precision",
                price = 12.999999,
            )

        assertEquals(12.999999, meal.price, 0.000001)
    }

    @Test
    fun `MealEntity should be equal when all properties match`() {
        val meal1 =
            MealEntity(
                id = 1,
                name = "Same Meal",
                description = "Same Description",
                price = 10.0,
                imageUrl = "same.jpg",
            )

        val meal2 =
            MealEntity(
                id = 1,
                name = "Same Meal",
                description = "Same Description",
                price = 10.0,
                imageUrl = "same.jpg",
            )

        assertEquals(meal1, meal2)
        assertEquals(meal1.hashCode(), meal2.hashCode())
    }

    @Test
    fun `MealEntity should not be equal when properties differ`() {
        val meal1 =
            MealEntity(
                id = 1,
                name = "Meal 1",
                description = "Description 1",
                price = 10.0,
            )

        val meal2 =
            MealEntity(
                id = 2,
                name = "Meal 2",
                description = "Description 2",
                price = 20.0,
            )

        assertNotEquals(meal1, meal2)
        assertNotEquals(meal1.hashCode(), meal2.hashCode())
    }
}
