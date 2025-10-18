package com.example.myapplication

import com.example.myapplication.database.MealEntity
import org.junit.Test
import org.junit.Assert.*

class MealConverterTest {

    @Test
    fun `convertToEntity should convert Meal to MealEntity correctly`() {
        val meal = Meal(
            id = 1,
            name = "Test Meal",
            description = "Test Description",
            price = 10.99,
            imageUrl = "test.jpg"
        )
        
        val entity = MealConverter.convertToEntity(meal)
        
        assertEquals(meal.id, entity.id)
        assertEquals(meal.name, entity.name)
        assertEquals(meal.description, entity.description)
        assertEquals(meal.price, entity.price, 0.01)
        assertEquals(meal.imageUrl, entity.imageUrl)
    }

    @Test
    fun `convertToEntity should trim whitespace`() {
        val meal = Meal(
            id = 1,
            name = "  Test Meal  ",
            description = "  Test Description  ",
            price = 10.99,
            imageUrl = "  test.jpg  "
        )
        
        val entity = MealConverter.convertToEntity(meal)
        
        assertEquals("Test Meal", entity.name)
        assertEquals("Test Description", entity.description)
        assertEquals("test.jpg", entity.imageUrl)
    }

    @Test
    fun `convertToMeal should convert MealEntity to Meal correctly`() {
        val entity = MealEntity(
            id = 1,
            name = "Test Meal",
            description = "Test Description",
            price = 10.99,
            imageUrl = "test.jpg"
        )
        
        val meal = MealConverter.convertToMeal(entity)
        
        assertEquals(entity.id, meal.id)
        assertEquals(entity.name, meal.name)
        assertEquals(entity.description, meal.description)
        assertEquals(entity.price, meal.price, 0.01)
        assertEquals(entity.imageUrl, meal.imageUrl)
    }

    @Test
    fun `convertListToEntities should convert list of Meals`() {
        val meals = listOf(
            Meal(1, "Meal 1", "Description 1", 10.0),
            Meal(2, "Meal 2", "Description 2", 15.0)
        )
        
        val entities = MealConverter.convertListToEntities(meals)
        
        assertEquals(2, entities.size)
        assertEquals(meals[0].id, entities[0].id)
        assertEquals(meals[1].id, entities[1].id)
    }

    @Test
    fun `convertListToMeals should convert list of MealEntities`() {
        val entities = listOf(
            MealEntity(1, "Meal 1", "Description 1", 10.0),
            MealEntity(2, "Meal 2", "Description 2", 15.0)
        )
        
        val meals = MealConverter.convertListToMeals(entities)
        
        assertEquals(2, meals.size)
        assertEquals(entities[0].id, meals[0].id)
        assertEquals(entities[1].id, meals[1].id)
    }

    @Test
    fun `sanitizeMealName should trim and normalize whitespace`() {
        assertEquals("Test Meal", MealConverter.sanitizeMealName("Test Meal"))
        assertEquals("Test Meal", MealConverter.sanitizeMealName("  Test Meal  "))
        assertEquals("Test Meal", MealConverter.sanitizeMealName("Test   Meal"))
        assertEquals("Test Meal", MealConverter.sanitizeMealName("  Test   Meal  "))
    }

    @Test
    fun `sanitizeMealDescription should trim and normalize whitespace`() {
        assertEquals("Test Description", MealConverter.sanitizeMealDescription("Test Description"))
        assertEquals("Test Description", MealConverter.sanitizeMealDescription("  Test Description  "))
        assertEquals("Test Description", MealConverter.sanitizeMealDescription("Test   Description"))
        assertEquals("Test Description", MealConverter.sanitizeMealDescription("  Test   Description  "))
    }

    @Test
    fun `sanitizeImageUrl should trim whitespace`() {
        assertEquals("test.jpg", MealConverter.sanitizeImageUrl("test.jpg"))
        assertEquals("test.jpg", MealConverter.sanitizeImageUrl("  test.jpg  "))
        assertEquals("", MealConverter.sanitizeImageUrl(""))
        assertEquals("", MealConverter.sanitizeImageUrl("   "))
    }

    @Test
    fun `normalizePrice should format to 2 decimal places`() {
        assertEquals(10.99, MealConverter.normalizePrice(10.99), 0.01)
        assertEquals(10.00, MealConverter.normalizePrice(10.0), 0.01)
        assertEquals(10.12, MealConverter.normalizePrice(10.123456), 0.01)
        assertEquals(0.00, MealConverter.normalizePrice(0.0), 0.01)
    }

    @Test
    fun `createMealEntityFromInputs should create entity with sanitized inputs`() {
        val entity = MealConverter.createMealEntityFromInputs(
            name = "  Test Meal  ",
            description = "  Test Description  ",
            price = 10.123456,
            imageUrl = "  test.jpg  "
        )
        
        assertEquals("Test Meal", entity.name)
        assertEquals("Test Description", entity.description)
        assertEquals(10.12, entity.price, 0.01)
        assertEquals("test.jpg", entity.imageUrl)
    }

    @Test
    fun `createMealEntityFromInputs should use default empty imageUrl`() {
        val entity = MealConverter.createMealEntityFromInputs(
            name = "Test Meal",
            description = "Test Description",
            price = 10.99
        )
        
        assertEquals("", entity.imageUrl)
    }

    @Test
    fun `updateMealEntity should update only provided fields`() {
        val originalEntity = MealEntity(
            id = 1,
            name = "Original Name",
            description = "Original Description",
            price = 10.0,
            imageUrl = "original.jpg"
        )
        
        val updatedEntity = MealConverter.updateMealEntity(
            originalEntity,
            name = "Updated Name",
            price = 15.0
        )
        
        assertEquals("Updated Name", updatedEntity.name)
        assertEquals("Original Description", updatedEntity.description)
        assertEquals(15.0, updatedEntity.price, 0.01)
        assertEquals("original.jpg", updatedEntity.imageUrl)
    }

    @Test
    fun `updateMealEntity should not change fields when null provided`() {
        val originalEntity = MealEntity(
            id = 1,
            name = "Original Name",
            description = "Original Description",
            price = 10.0,
            imageUrl = "original.jpg"
        )
        
        val updatedEntity = MealConverter.updateMealEntity(originalEntity)
        
        assertEquals(originalEntity.name, updatedEntity.name)
        assertEquals(originalEntity.description, updatedEntity.description)
        assertEquals(originalEntity.price, updatedEntity.price, 0.01)
        assertEquals(originalEntity.imageUrl, updatedEntity.imageUrl)
    }

    @Test
    fun `updateMealEntity should sanitize updated fields`() {
        val originalEntity = MealEntity(
            id = 1,
            name = "Original Name",
            description = "Original Description",
            price = 10.0,
            imageUrl = "original.jpg"
        )
        
        val updatedEntity = MealConverter.updateMealEntity(
            originalEntity,
            name = "  Updated Name  ",
            description = "  Updated Description  ",
            imageUrl = "  updated.jpg  "
        )
        
        assertEquals("Updated Name", updatedEntity.name)
        assertEquals("Updated Description", updatedEntity.description)
        assertEquals("updated.jpg", updatedEntity.imageUrl)
    }

    @Test
    fun `convertListToEntities should handle empty list`() {
        val entities = MealConverter.convertListToEntities(emptyList())
        assertTrue(entities.isEmpty())
    }

    @Test
    fun `convertListToMeals should handle empty list`() {
        val meals = MealConverter.convertListToMeals(emptyList())
        assertTrue(meals.isEmpty())
    }

    @Test
    fun `sanitizeMealName should handle multiple spaces`() {
        assertEquals("Test Meal", MealConverter.sanitizeMealName("Test    Meal"))
        assertEquals("Test Meal", MealConverter.sanitizeMealName("  Test    Meal  "))
    }

    @Test
    fun `sanitizeMealDescription should handle multiple spaces`() {
        assertEquals("Test Description", MealConverter.sanitizeMealDescription("Test    Description"))
        assertEquals("Test Description", MealConverter.sanitizeMealDescription("  Test    Description  "))
    }

    @Test
    fun `normalizePrice should handle negative prices`() {
        assertEquals(-10.99, MealConverter.normalizePrice(-10.99), 0.01)
        assertEquals(-10.00, MealConverter.normalizePrice(-10.0), 0.01)
    }

    @Test
    fun `createMealEntityFromInputs should handle zero price`() {
        val entity = MealConverter.createMealEntityFromInputs(
            name = "Free Meal",
            description = "No cost meal",
            price = 0.0
        )
        
        assertEquals(0.0, entity.price, 0.01)
    }

    @Test
    fun `convertToEntity should handle meal with empty strings`() {
        val meal = Meal(
            id = 1,
            name = "",
            description = "",
            price = 0.0,
            imageUrl = ""
        )
        
        val entity = MealConverter.convertToEntity(meal)
        
        assertEquals("", entity.name)
        assertEquals("", entity.description)
        assertEquals("", entity.imageUrl)
    }

    @Test
    fun `convertToMeal should handle entity with empty strings`() {
        val entity = MealEntity(
            id = 1,
            name = "",
            description = "",
            price = 0.0,
            imageUrl = ""
        )
        
        val meal = MealConverter.convertToMeal(entity)
        
        assertEquals("", meal.name)
        assertEquals("", meal.description)
        assertEquals("", meal.imageUrl)
    }
}

