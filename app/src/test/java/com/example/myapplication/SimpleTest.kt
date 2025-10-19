package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class SimpleTest {
    @Test
    fun `basic test should pass`() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `string test should pass`() {
        val name = "Test Meal"
        assertTrue(name.isNotBlank())
        assertEquals("Test Meal", name)
    }

    @Test
    fun `list test should pass`() {
        val meals = listOf("Pizza", "Salad", "Soup")
        assertEquals(3, meals.size)
        assertTrue(meals.contains("Pizza"))
    }
}


