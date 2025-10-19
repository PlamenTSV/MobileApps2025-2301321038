package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class MealTest {
    @Test
    fun `Meal should have correct properties`() {
        val meal =
            Meal(
                id = 1,
                name = "Test Meal",
                description = "Test Description",
                price = 10.99,
                imageUrl = "test.jpg",
            )

        assertEquals(1, meal.id)
        assertEquals("Test Meal", meal.name)
        assertEquals("Test Description", meal.description)
        assertEquals(10.99, meal.price, 0.01)
        assertEquals("test.jpg", meal.imageUrl)
    }

    @Test
    fun `Meal should have default empty imageUrl`() {
        val meal =
            Meal(
                id = 1,
                name = "Test Meal",
                description = "Test Description",
                price = 10.99,
            )

        assertEquals("", meal.imageUrl)
    }

    @Test
    fun `Meal should handle zero and negative values`() {
        val meal =
            Meal(
                id = 0,
                name = "",
                description = "",
                price = -5.0,
            )

        assertEquals(0, meal.id)
        assertEquals("", meal.name)
        assertEquals("", meal.description)
        assertEquals(-5.0, meal.price, 0.01)
    }

    @Test
    fun `Meal should be equal when all properties match`() {
        val meal1 =
            Meal(
                id = 1,
                name = "Same Meal",
                description = "Same Description",
                price = 10.0,
                imageUrl = "same.jpg",
            )

        val meal2 =
            Meal(
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
    fun `Meal should not be equal when properties differ`() {
        val meal1 =
            Meal(
                id = 1,
                name = "Meal 1",
                description = "Description 1",
                price = 10.0,
            )

        val meal2 =
            Meal(
                id = 2,
                name = "Meal 2",
                description = "Description 2",
                price = 20.0,
            )

        assertNotEquals(meal1, meal2)
        assertNotEquals(meal1.hashCode(), meal2.hashCode())
    }
}

class SampleMealsTest {
    @Test
    fun `SampleMeals should contain exactly 5 meals`() {
        assertEquals(5, SampleMeals.meals.size)
    }

    @Test
    fun `SampleMeals should have unique IDs`() {
        val ids = SampleMeals.meals.map { it.id }
        val uniqueIds = ids.toSet()
        assertEquals(ids.size, uniqueIds.size)
    }

    @Test
    fun `SampleMeals should have sequential IDs starting from 1`() {
        val expectedIds = listOf(1, 2, 3, 4, 5)
        val actualIds = SampleMeals.meals.map { it.id }.sorted()
        assertEquals(expectedIds, actualIds)
    }

    @Test
    fun `SampleMeals should have non-empty names`() {
        SampleMeals.meals.forEach { meal ->
            assertTrue("Meal name should not be empty", meal.name.isNotBlank())
        }
    }

    @Test
    fun `SampleMeals should have non-empty descriptions`() {
        SampleMeals.meals.forEach { meal ->
            assertTrue("Meal description should not be empty", meal.description.isNotBlank())
        }
    }

    @Test
    fun `SampleMeals should have positive prices`() {
        SampleMeals.meals.forEach { meal ->
            assertTrue("Meal price should be positive", meal.price > 0)
        }
    }

    @Test
    fun `SampleMeals should have empty imageUrls by default`() {
        SampleMeals.meals.forEach { meal ->
            assertEquals("Meal imageUrl should be empty", "", meal.imageUrl)
        }
    }

    @Test
    fun `SampleMeals should contain specific meals`() {
        val mealNames = SampleMeals.meals.map { it.name }

        assertTrue("Should contain Margherita Pizza", mealNames.contains("Margherita Pizza"))
        assertTrue("Should contain Spaghetti Carbonara", mealNames.contains("Spaghetti Carbonara"))
        assertTrue("Should contain Caesar Salad", mealNames.contains("Caesar Salad"))
        assertTrue("Should contain Grilled Salmon", mealNames.contains("Grilled Salmon"))
        assertTrue("Should contain Chocolate Cake", mealNames.contains("Chocolate Cake"))
    }

    @Test
    fun `SampleMeals should have correct prices for specific meals`() {
        val margheritaPizza = SampleMeals.meals.find { it.name == "Margherita Pizza" }
        assertNotNull("Margherita Pizza should exist", margheritaPizza)
        assertEquals(12.99, margheritaPizza!!.price, 0.01)

        val carbonara = SampleMeals.meals.find { it.name == "Spaghetti Carbonara" }
        assertNotNull("Spaghetti Carbonara should exist", carbonara)
        assertEquals(14.50, carbonara!!.price, 0.01)

        val salad = SampleMeals.meals.find { it.name == "Caesar Salad" }
        assertNotNull("Caesar Salad should exist", salad)
        assertEquals(9.99, salad!!.price, 0.01)

        val salmon = SampleMeals.meals.find { it.name == "Grilled Salmon" }
        assertNotNull("Grilled Salmon should exist", salmon)
        assertEquals(18.99, salmon!!.price, 0.01)

        val cake = SampleMeals.meals.find { it.name == "Chocolate Cake" }
        assertNotNull("Chocolate Cake should exist", cake)
        assertEquals(7.50, cake!!.price, 0.01)
    }

    @Test
    fun `SampleMeals should have reasonable price range`() {
        val prices = SampleMeals.meals.map { it.price }
        val minPrice = prices.minOrNull() ?: 0.0
        val maxPrice = prices.maxOrNull() ?: 0.0

        assertTrue("Minimum price should be reasonable", minPrice >= 5.0)
        assertTrue("Maximum price should be reasonable", maxPrice <= 25.0)
    }

    @Test
    fun `SampleMeals should have descriptive descriptions`() {
        SampleMeals.meals.forEach { meal ->
            assertTrue("Description should be descriptive", meal.description.length > 10)
        }
    }

    @Test
    fun `SampleMeals should be immutable`() {
        val originalSize = SampleMeals.meals.size
        val originalMeal = SampleMeals.meals.first()

        // Try to modify (this should not affect the original)
        val modifiedMeals = SampleMeals.meals.toMutableList()
        modifiedMeals.add(Meal(999, "Extra Meal", "Extra Description", 99.99))

        assertEquals("Original meals should be unchanged", originalSize, SampleMeals.meals.size)
        assertEquals("Original meal should be unchanged", originalMeal, SampleMeals.meals.first())
    }
}
