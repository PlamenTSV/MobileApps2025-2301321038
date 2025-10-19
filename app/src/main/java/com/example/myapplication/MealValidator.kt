package com.example.myapplication

import com.example.myapplication.database.MealEntity

class MealValidator {
    companion object {
        private const val MIN_NAME_LENGTH = 2
        private const val MIN_DESCRIPTION_LENGTH = 10
        private const val MAX_PRICE = 1000.0
        private const val MIN_PRICE = 0.0

        fun validateMealName(name: String): ValidationResult {
            return when {
                name.isBlank() -> ValidationResult.Error("Meal name cannot be blank")
                name.trim().length < MIN_NAME_LENGTH -> ValidationResult.Error("Meal name must be at least $MIN_NAME_LENGTH characters")
                name.length > 100 -> ValidationResult.Error("Meal name cannot exceed 100 characters")
                else -> ValidationResult.Success
            }
        }

        fun validateMealDescription(description: String): ValidationResult {
            return when {
                description.isBlank() -> ValidationResult.Error("Meal description cannot be blank")
                description.trim().length < MIN_DESCRIPTION_LENGTH ->
                    ValidationResult.Error(
                        "Meal description must be at least $MIN_DESCRIPTION_LENGTH characters",
                    )
                description.length > 500 -> ValidationResult.Error("Meal description cannot exceed 500 characters")
                else -> ValidationResult.Success
            }
        }

        fun validateMealPrice(price: Double): ValidationResult {
            return when {
                price < MIN_PRICE -> ValidationResult.Error("Meal price cannot be negative")
                price > MAX_PRICE -> ValidationResult.Error("Meal price cannot exceed $MAX_PRICE")
                else -> ValidationResult.Success
            }
        }

        fun validateMeal(meal: MealEntity): ValidationResult {
            val nameResult = validateMealName(meal.name)
            if (nameResult is ValidationResult.Error) return nameResult

            val descriptionResult = validateMealDescription(meal.description)
            if (descriptionResult is ValidationResult.Error) return descriptionResult

            val priceResult = validateMealPrice(meal.price)
            if (priceResult is ValidationResult.Error) return priceResult

            return ValidationResult.Success
        }

        fun validateMealInputs(
            name: String,
            description: String,
            price: Double,
        ): ValidationResult {
            val nameResult = validateMealName(name)
            if (nameResult is ValidationResult.Error) return nameResult

            val descriptionResult = validateMealDescription(description)
            if (descriptionResult is ValidationResult.Error) return descriptionResult

            val priceResult = validateMealPrice(price)
            if (priceResult is ValidationResult.Error) return priceResult

            return ValidationResult.Success
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()

    data class Error(val message: String) : ValidationResult()

    fun isSuccess(): Boolean = this is Success

    fun isError(): Boolean = this is Error

    fun getErrorMessage(): String? = if (this is Error) this.message else null
}
