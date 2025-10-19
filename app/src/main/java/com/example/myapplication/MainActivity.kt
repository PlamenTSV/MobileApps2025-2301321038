package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.MealEntity
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var themeManager: ThemeManager
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        themeManager = ThemeManager(this)
        database = AppDatabase.getDatabase(this)

        setContent {
            var isDarkTheme by remember { mutableStateOf(themeManager.isDarkTheme()) }
            val meals by database.mealDao().getAllMeals().collectAsState(initial = emptyList())

            MyApplicationTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { navigateToAddMeal() }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                ) { innerPadding ->
                    MealListScreen(
                        meals = meals,
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = {
                            isDarkTheme = !isDarkTheme
                            themeManager.setDarkTheme(isDarkTheme)
                        },
                        onMealClick = { mealId -> navigateToMealDetails(mealId) },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    private fun navigateToMealDetails(mealId: Int) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        intent.putExtra("mealId", mealId)
        startActivity(intent)
    }

    private fun navigateToAddMeal() {
        startActivity(Intent(this, AddMealActivity::class.java))
    }
}

@Composable
fun MealListScreen(
    meals: List<MealEntity>,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onMealClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Restaurant Menu",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = onThemeToggle) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = if (isDarkTheme) "Light" else "Dark",
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(meals) { meal ->
                MealItem(meal = meal, onClick = { onMealClick(meal.id) })
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun MealListScreenPreview() {
    MyApplicationTheme(darkTheme = false) {
        MealListScreen(
            meals = emptyList(),
            isDarkTheme = false,
            onThemeToggle = {},
            onMealClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun MealListScreenDarkPreview() {
    MyApplicationTheme(darkTheme = true) {
        MealListScreen(
            meals = emptyList(),
            isDarkTheme = true,
            onThemeToggle = {},
            onMealClick = {},
        )
    }
}
