package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.MealEntity
import com.example.myapplication.ui.theme.MyApplicationTheme

class MealDetailsActivity : ComponentActivity() {
    private lateinit var themeManager: ThemeManager
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        themeManager = ThemeManager(this)
        database = AppDatabase.getDatabase(this)

        val mealId = intent.getIntExtra("mealId", -1)

        setContent {
            var isDarkTheme by remember { mutableStateOf(themeManager.isDarkTheme()) }
            val meals by database.mealDao().getAllMeals().collectAsState(initial = emptyList())
            val meal = meals.find { it.id == mealId }

            MyApplicationTheme(darkTheme = isDarkTheme) {
                if (meal != null) {
                    MealDetailsScreen(
                        meal = meal,
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = {
                            isDarkTheme = !isDarkTheme
                            themeManager.setDarkTheme(isDarkTheme)
                        },
                        onBackClick = { finish() },
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Meal not found")
                    }
                }
            }
        }
    }
}

@Composable
fun MealDetailsScreen(
    meal: MealEntity,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "Meal Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
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
        },
    ) { innerPadding ->
        MealPage(
            meal = meal,
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        )
    }
}

@Composable
fun MealPage(
    meal: MealEntity,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = meal.name.take(2).uppercase(),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = meal.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = meal.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                Text(
                    text = "$${String.format("%.2f", meal.price)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealDetailsScreenPreview() {
    MyApplicationTheme {
        MealDetailsScreen(
            meal =
                MealEntity(
                    name = "Test Meal",
                    description = "Test description",
                    price = 10.99,
                ),
            isDarkTheme = false,
            onThemeToggle = {},
            onBackClick = {},
        )
    }
}
