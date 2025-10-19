package com.example.myapplication

data class Meal(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String = "",
)

object SampleMeals {
    val meals =
        listOf(
            Meal(
                id = 1,
                name = "Margherita Pizza",
                description = "Classic pizza with fresh tomatoes, mozzarella, and basil",
                price = 12.99,
            ),
            Meal(
                id = 2,
                name = "Spaghetti Carbonara",
                description = "Traditional Italian pasta with eggs, cheese, and pancetta",
                price = 14.50,
            ),
            Meal(
                id = 3,
                name = "Caesar Salad",
                description = "Fresh romaine lettuce with parmesan cheese and croutons",
                price = 9.99,
            ),
            Meal(
                id = 4,
                name = "Grilled Salmon",
                description = "Fresh Atlantic salmon with herbs and lemon",
                price = 18.99,
            ),
            Meal(
                id = 5,
                name = "Chocolate Cake",
                description = "Rich chocolate cake with vanilla ice cream",
                price = 7.50,
            ),
        )
}
