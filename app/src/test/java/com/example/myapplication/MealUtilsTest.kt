package com.example.myapplication

import com.example.myapplication.database.MealEntity
import org.junit.Test
import org.junit.Assert.*

class MealUtilsTest {

    @Test
    fun `formatPrice should format price with 2 decimal places`() {
        assertEquals("10.99", MealUtils.formatPrice(10.99))
        assertEquals("0.00", MealUtils.formatPrice(0.0))
        assertEquals("100.50", MealUtils.formatPrice(100.5))
        assertEquals("999.99", MealUtils.formatPrice(999.99))
    }

    @Test
    fun `formatPrice should handle negative prices`() {
        assertEquals("-5.00", MealUtils.formatPrice(-5.0))
        assertEquals("-0.50", MealUtils.formatPrice(-0.5))
    }

    @Test
    fun `formatPriceWithCurrency should add dollar sign`() {
        assertEquals("$10.99", MealUtils.formatPriceWithCurrency(10.99))
        assertEquals("$0.00", MealUtils.formatPriceWithCurrency(0.0))
        assertEquals("$-5.00", MealUtils.formatPriceWithCurrency(-5.0))
    }

    @Test
    fun `isValidMealName should return true for valid names`() {
        assertTrue(MealUtils.isValidMealName("Pizza"))
        assertTrue(MealUtils.isValidMealName("Caesar Salad"))
        assertTrue(MealUtils.isValidMealName("AB"))
        assertTrue(MealUtils.isValidMealName("  Valid Name  "))
    }

    @Test
    fun `isValidMealName should return false for invalid names`() {
        assertFalse(MealUtils.isValidMealName(""))
        assertFalse(MealUtils.isValidMealName(" "))
        assertFalse(MealUtils.isValidMealName("A"))
        assertFalse(MealUtils.isValidMealName("   "))
    }

    @Test
    fun `isValidMealDescription should return true for valid descriptions`() {
        assertTrue(MealUtils.isValidMealDescription("This is a valid description"))
        assertTrue(MealUtils.isValidMealDescription("1234567890"))
        assertTrue(MealUtils.isValidMealDescription("  Valid description  "))
    }

    @Test
    fun `isValidMealDescription should return false for invalid descriptions`() {
        assertFalse(MealUtils.isValidMealDescription(""))
        assertFalse(MealUtils.isValidMealDescription("Short"))
        assertFalse(MealUtils.isValidMealDescription("123456789"))
        assertFalse(MealUtils.isValidMealDescription("   "))
    }

    @Test
    fun `isValidMealPrice should return true for valid prices`() {
        assertTrue(MealUtils.isValidMealPrice(0.0))
        assertTrue(MealUtils.isValidMealPrice(10.99))
        assertTrue(MealUtils.isValidMealPrice(1000.0))
        assertTrue(MealUtils.isValidMealPrice(500.50))
    }

    @Test
    fun `isValidMealPrice should return false for invalid prices`() {
        assertFalse(MealUtils.isValidMealPrice(-0.01))
        assertFalse(MealUtils.isValidMealPrice(-10.0))
        assertFalse(MealUtils.isValidMealPrice(1000.01))
        assertFalse(MealUtils.isValidMealPrice(2000.0))
    }

    @Test
    fun `isValidMeal should return true for valid meal`() {
        assertTrue(MealUtils.isValidMeal("Pizza", "Delicious pizza with cheese", 12.99))
    }

    @Test
    fun `isValidMeal should return false for invalid meal`() {
        assertFalse(MealUtils.isValidMeal("A", "Short", 12.99))
        assertFalse(MealUtils.isValidMeal("Pizza", "Short", 12.99))
        assertFalse(MealUtils.isValidMeal("Pizza", "Valid description", -5.0))
    }

    @Test
    fun `generateInitials should return first two characters uppercase`() {
        assertEquals("PI", MealUtils.generateInitials("Pizza"))
        assertEquals("CA", MealUtils.generateInitials("Caesar Salad"))
        assertEquals("AB", MealUtils.generateInitials("ab"))
        assertEquals("A ", MealUtils.generateInitials("a b"))
    }

    @Test
    fun `generateInitials should handle edge cases`() {
        assertEquals("A", MealUtils.generateInitials("A"))
        assertEquals("", MealUtils.generateInitials(""))
        assertEquals("  ", MealUtils.generateInitials("  "))
        assertEquals("PI", MealUtils.generateInitials("  Pizza  "))
    }

    @Test
    fun `mealToEntity should convert correctly`() {
        val meal = Meal(
            id = 1,
            name = "Test Meal",
            description = "Test Description",
            price = 10.99,
            imageUrl = "test.jpg"
        )
        
        val entity = MealUtils.mealToEntity(meal)
        
        assertEquals(meal.id, entity.id)
        assertEquals(meal.name, entity.name)
        assertEquals(meal.description, entity.description)
        assertEquals(meal.price, entity.price, 0.01)
        assertEquals(meal.imageUrl, entity.imageUrl)
    }

    @Test
    fun `entityToMeal should convert correctly`() {
        val entity = MealEntity(
            id = 1,
            name = "Test Meal",
            description = "Test Description",
            price = 10.99,
            imageUrl = "test.jpg"
        )
        
        val meal = MealUtils.entityToMeal(entity)
        
        assertEquals(entity.id, meal.id)
        assertEquals(entity.name, meal.name)
        assertEquals(entity.description, meal.description)
        assertEquals(entity.price, meal.price, 0.01)
        assertEquals(entity.imageUrl, meal.imageUrl)
    }

    @Test
    fun `calculateTotalPrice should sum all meal prices`() {
        val meals = listOf(
            Meal(1, "Meal 1", "Description 1", 10.0),
            Meal(2, "Meal 2", "Description 2", 15.5),
            Meal(3, "Meal 3", "Description 3", 5.25)
        )
        
        val total = MealUtils.calculateTotalPrice(meals)
        
        assertEquals(30.75, total, 0.01)
    }

    @Test
    fun `calculateTotalPrice should return zero for empty list`() {
        val total = MealUtils.calculateTotalPrice(emptyList())
        assertEquals(0.0, total, 0.01)
    }

    @Test
    fun `findMostExpensiveMeal should return meal with highest price`() {
        val meals = listOf(
            Meal(1, "Cheap Meal", "Description", 5.0),
            Meal(2, "Expensive Meal", "Description", 25.0),
            Meal(3, "Medium Meal", "Description", 15.0)
        )
        
        val mostExpensive = MealUtils.findMostExpensiveMeal(meals)
        
        assertNotNull(mostExpensive)
        assertEquals("Expensive Meal", mostExpensive!!.name)
        assertEquals(25.0, mostExpensive.price, 0.01)
    }

    @Test
    fun `findMostExpensiveMeal should return null for empty list`() {
        val mostExpensive = MealUtils.findMostExpensiveMeal(emptyList())
        assertNull(mostExpensive)
    }

    @Test
    fun `findCheapestMeal should return meal with lowest price`() {
        val meals = listOf(
            Meal(1, "Expensive Meal", "Description", 25.0),
            Meal(2, "Cheap Meal", "Description", 5.0),
            Meal(3, "Medium Meal", "Description", 15.0)
        )
        
        val cheapest = MealUtils.findCheapestMeal(meals)
        
        assertNotNull(cheapest)
        assertEquals("Cheap Meal", cheapest!!.name)
        assertEquals(5.0, cheapest.price, 0.01)
    }

    @Test
    fun `findCheapestMeal should return null for empty list`() {
        val cheapest = MealUtils.findCheapestMeal(emptyList())
        assertNull(cheapest)
    }

    @Test
    fun `findMostExpensiveMeal should handle equal prices`() {
        val meals = listOf(
            Meal(1, "Meal 1", "Description", 10.0),
            Meal(2, "Meal 2", "Description", 10.0),
            Meal(3, "Meal 3", "Description", 10.0)
        )
        
        val mostExpensive = MealUtils.findMostExpensiveMeal(meals)
        
        assertNotNull(mostExpensive)
        assertEquals(10.0, mostExpensive!!.price, 0.01)
    }

    @Test
    fun `formatPrice should handle very small decimal values`() {
        assertEquals("0.01", MealUtils.formatPrice(0.01))
        assertEquals("0.10", MealUtils.formatPrice(0.1))
        assertEquals("0.99", MealUtils.formatPrice(0.99))
    }

    @Test
    fun `formatPrice should handle large numbers`() {
        assertEquals("999999.99", MealUtils.formatPrice(999999.99))
        assertEquals("1000000.00", MealUtils.formatPrice(1000000.0))
    }
}

