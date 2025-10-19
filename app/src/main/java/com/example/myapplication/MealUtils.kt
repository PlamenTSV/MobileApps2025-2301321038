package com.example.myapplication

import java.text.DecimalFormat

object MealUtils {
    /**
     * Formats a price value to display with 2 decimal places
     */
    fun formatPrice(price: Double): String {
        val formatter = DecimalFormat("#0.00")
        return formatter.format(price)
    }

    /**
     * Formats a price value with currency symbol
     */
    fun formatPriceWithCurrency(price: Double): String {
        return "$${formatPrice(price)}"
    }

    /**
     * Validates if a meal name is valid
     */
    fun isValidMealName(name: String): Boolean {
        return name.trim().isNotBlank() && name.length >= 2
    }

    /**
     * Validates if a meal description is valid
     */
    fun isValidMealDescription(description: String): Boolean {
        return description.trim().isNotBlank() && description.length >= 10
    }

    /**
     * Validates if a meal price is valid
     */
    fun isValidMealPrice(price: Double): Boolean {
        return price >= 0.0 && price <= 1000.0
    }

    /**
     * Validates if a meal is complete and valid
     */
    fun isValidMeal(
        name: String,
        description: String,
        price: Double,
    ): Boolean {
        return isValidMealName(name) &&
            isValidMealDescription(description) &&
            isValidMealPrice(price)
    }

    /**
     * Generates initials from meal name
     */
    fun generateInitials(name: String): String {
        return name.trim().take(2).uppercase()
    }

    /**
     * Converts Meal to MealEntity
     */
    fun mealToEntity(meal: Meal): com.example.myapplication.database.MealEntity {
        return com.example.myapplication.database.MealEntity(
            id = meal.id,
            name = meal.name,
            description = meal.description,
            price = meal.price,
            imageUrl = meal.imageUrl,
        )
    }

    /**
     * Converts MealEntity to Meal
     */
    fun entityToMeal(entity: com.example.myapplication.database.MealEntity): Meal {
        return Meal(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            imageUrl = entity.imageUrl,
        )
    }

    /**
     * Calculates total price for a list of meals
     */
    fun calculateTotalPrice(meals: List<Meal>): Double {
        return meals.sumOf { it.price }
    }

    /**
     * Finds the most expensive meal
     */
    fun findMostExpensiveMeal(meals: List<Meal>): Meal? {
        return meals.maxByOrNull { it.price }
    }

    /**
     * Finds the cheapest meal
     */
    fun findCheapestMeal(meals: List<Meal>): Meal? {
        return meals.minByOrNull { it.price }
    }
}
