package com.example.myapplication

import com.example.myapplication.database.MealEntity

class MealConverter {
    companion object {
        fun convertToEntity(meal: Meal): MealEntity {
            return MealEntity(
                id = meal.id,
                name = meal.name.trim(),
                description = meal.description.trim(),
                price = meal.price,
                imageUrl = meal.imageUrl.trim(),
            )
        }

        fun convertToMeal(entity: MealEntity): Meal {
            return Meal(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                price = entity.price,
                imageUrl = entity.imageUrl,
            )
        }

        fun convertListToEntities(meals: List<Meal>): List<MealEntity> {
            return meals.map { convertToEntity(it) }
        }

        fun convertListToMeals(entities: List<MealEntity>): List<Meal> {
            return entities.map { convertToMeal(it) }
        }

        fun sanitizeMealName(name: String): String {
            return name.trim().replace(Regex("\\s+"), " ")
        }

        fun sanitizeMealDescription(description: String): String {
            return description.trim().replace(Regex("\\s+"), " ")
        }

        fun sanitizeImageUrl(url: String): String {
            return url.trim()
        }

        fun normalizePrice(price: Double): Double {
            return String.format("%.2f", price).toDouble()
        }

        fun createMealEntityFromInputs(
            name: String,
            description: String,
            price: Double,
            imageUrl: String = "",
        ): MealEntity {
            return MealEntity(
                name = sanitizeMealName(name),
                description = sanitizeMealDescription(description),
                price = normalizePrice(price),
                imageUrl = sanitizeImageUrl(imageUrl),
            )
        }

        fun updateMealEntity(
            entity: MealEntity,
            name: String? = null,
            description: String? = null,
            price: Double? = null,
            imageUrl: String? = null,
        ): MealEntity {
            return entity.copy(
                name = name?.let { sanitizeMealName(it) } ?: entity.name,
                description = description?.let { sanitizeMealDescription(it) } ?: entity.description,
                price = price?.let { normalizePrice(it) } ?: entity.price,
                imageUrl = imageUrl?.let { sanitizeImageUrl(it) } ?: entity.imageUrl,
            )
        }
    }
}
