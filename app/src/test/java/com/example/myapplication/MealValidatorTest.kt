package com.example.myapplication

import com.example.myapplication.database.MealEntity
import org.junit.Test
import org.junit.Assert.*

class MealValidatorTest {

    @Test
    fun `validateMealName should return success for valid names`() {
        assertTrue(MealValidator.validateMealName("Pizza").isSuccess())
        assertTrue(MealValidator.validateMealName("Caesar Salad").isSuccess())
        assertTrue(MealValidator.validateMealName("AB").isSuccess())
        assertTrue(MealValidator.validateMealName("  Valid Name  ").isSuccess())
    }

    @Test
    fun `validateMealName should return error for blank names`() {
        val result = MealValidator.validateMealName("")
        assertTrue(result.isError())
        assertEquals("Meal name cannot be blank", result.getErrorMessage())
    }

    @Test
    fun `validateMealName should return error for whitespace only names`() {
        val result = MealValidator.validateMealName("   ")
        assertTrue(result.isError())
        assertEquals("Meal name cannot be blank", result.getErrorMessage())
    }

    @Test
    fun `validateMealName should return error for too short names`() {
        val result = MealValidator.validateMealName("A")
        assertTrue(result.isError())
        assertEquals("Meal name must be at least 2 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMealName should return error for too long names`() {
        val longName = "A".repeat(101)
        val result = MealValidator.validateMealName(longName)
        assertTrue(result.isError())
        assertEquals("Meal name cannot exceed 100 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMealDescription should return success for valid descriptions`() {
        assertTrue(MealValidator.validateMealDescription("This is a valid description").isSuccess())
        assertTrue(MealValidator.validateMealDescription("1234567890").isSuccess())
        assertTrue(MealValidator.validateMealDescription("  Valid description  ").isSuccess())
    }

    @Test
    fun `validateMealDescription should return error for blank descriptions`() {
        val result = MealValidator.validateMealDescription("")
        assertTrue(result.isError())
        assertEquals("Meal description cannot be blank", result.getErrorMessage())
    }

    @Test
    fun `validateMealDescription should return error for too short descriptions`() {
        val result = MealValidator.validateMealDescription("Short")
        assertTrue(result.isError())
        assertEquals("Meal description must be at least 10 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMealDescription should return error for too long descriptions`() {
        val longDescription = "A".repeat(501)
        val result = MealValidator.validateMealDescription(longDescription)
        assertTrue(result.isError())
        assertEquals("Meal description cannot exceed 500 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMealPrice should return success for valid prices`() {
        assertTrue(MealValidator.validateMealPrice(0.0).isSuccess())
        assertTrue(MealValidator.validateMealPrice(10.99).isSuccess())
        assertTrue(MealValidator.validateMealPrice(1000.0).isSuccess())
    }

    @Test
    fun `validateMealPrice should return error for negative prices`() {
        val result = MealValidator.validateMealPrice(-0.01)
        assertTrue(result.isError())
        assertEquals("Meal price cannot be negative", result.getErrorMessage())
    }

    @Test
    fun `validateMealPrice should return error for too high prices`() {
        val result = MealValidator.validateMealPrice(1000.01)
        assertTrue(result.isError())
        assertEquals("Meal price cannot exceed 1000.0", result.getErrorMessage())
    }

    @Test
    fun `validateMeal should return success for valid meal`() {
        val meal = MealEntity(
            name = "Valid Meal",
            description = "This is a valid description",
            price = 12.99
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isSuccess())
    }

    @Test
    fun `validateMeal should return error for invalid name`() {
        val meal = MealEntity(
            name = "A",
            description = "This is a valid description",
            price = 12.99
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isError())
        assertEquals("Meal name must be at least 2 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMeal should return error for invalid description`() {
        val meal = MealEntity(
            name = "Valid Meal",
            description = "Short",
            price = 12.99
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isError())
        assertEquals("Meal description must be at least 10 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMeal should return error for invalid price`() {
        val meal = MealEntity(
            name = "Valid Meal",
            description = "This is a valid description",
            price = -5.0
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isError())
        assertEquals("Meal price cannot be negative", result.getErrorMessage())
    }

    @Test
    fun `validateMealInputs should return success for valid inputs`() {
        val result = MealValidator.validateMealInputs(
            name = "Valid Meal",
            description = "This is a valid description",
            price = 12.99
        )
        
        assertTrue(result.isSuccess())
    }

    @Test
    fun `validateMealInputs should return error for invalid inputs`() {
        val result = MealValidator.validateMealInputs(
            name = "A",
            description = "Short",
            price = -5.0
        )
        
        assertTrue(result.isError())
        assertEquals("Meal name must be at least 2 characters", result.getErrorMessage())
    }

    @Test
    fun `ValidationResult Success should have correct properties`() {
        val result = ValidationResult.Success
        
        assertTrue(result.isSuccess())
        assertFalse(result.isError())
        assertNull(result.getErrorMessage())
    }

    @Test
    fun `ValidationResult Error should have correct properties`() {
        val errorMessage = "Test error message"
        val result = ValidationResult.Error(errorMessage)
        
        assertFalse(result.isSuccess())
        assertTrue(result.isError())
        assertEquals(errorMessage, result.getErrorMessage())
    }

    @Test
    fun `validateMealName should handle edge cases`() {
        // Exactly minimum length
        assertTrue(MealValidator.validateMealName("AB").isSuccess())
        
        // Exactly maximum length
        val maxLengthName = "A".repeat(100)
        assertTrue(MealValidator.validateMealName(maxLengthName).isSuccess())
        
        // One character over minimum
        assertTrue(MealValidator.validateMealName("ABC").isSuccess())
        
        // One character under minimum
        assertTrue(MealValidator.validateMealName("A").isError())
    }

    @Test
    fun `validateMealDescription should handle edge cases`() {
        // Exactly minimum length
        assertTrue(MealValidator.validateMealDescription("1234567890").isSuccess())
        
        // Exactly maximum length
        val maxLengthDescription = "A".repeat(500)
        assertTrue(MealValidator.validateMealDescription(maxLengthDescription).isSuccess())
        
        // One character over minimum
        assertTrue(MealValidator.validateMealDescription("12345678901").isSuccess())
        
        // One character under minimum
        assertTrue(MealValidator.validateMealDescription("123456789").isError())
    }

    @Test
    fun `validateMealPrice should handle edge cases`() {
        // Exactly minimum price
        assertTrue(MealValidator.validateMealPrice(0.0).isSuccess())
        
        // Exactly maximum price
        assertTrue(MealValidator.validateMealPrice(1000.0).isSuccess())
        
        // Just under minimum
        assertTrue(MealValidator.validateMealPrice(-0.01).isError())
        
        // Just over maximum
        assertTrue(MealValidator.validateMealPrice(1000.01).isError())
    }

    @Test
    fun `validateMeal should prioritize name validation`() {
        val meal = MealEntity(
            name = "A", // Invalid name
            description = "Short", // Invalid description
            price = -5.0 // Invalid price
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isError())
        assertEquals("Meal name must be at least 2 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMeal should prioritize description validation after name`() {
        val meal = MealEntity(
            name = "Valid Name", // Valid name
            description = "Short", // Invalid description
            price = -5.0 // Invalid price
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isError())
        assertEquals("Meal description must be at least 10 characters", result.getErrorMessage())
    }

    @Test
    fun `validateMeal should check price validation last`() {
        val meal = MealEntity(
            name = "Valid Name", // Valid name
            description = "This is a valid description", // Valid description
            price = -5.0 // Invalid price
        )
        
        val result = MealValidator.validateMeal(meal)
        assertTrue(result.isError())
        assertEquals("Meal price cannot be negative", result.getErrorMessage())
    }
}

