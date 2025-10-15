package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.MealEntity
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMealActivity : ComponentActivity() {
    private lateinit var themeManager: ThemeManager
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        themeManager = ThemeManager(this)
        database = AppDatabase.getDatabase(this)
        
        setContent {
            var isDarkTheme by remember { mutableStateOf(themeManager.isDarkTheme()) }
            
            MyApplicationTheme(darkTheme = isDarkTheme) {
                AddMealScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { 
                        isDarkTheme = !isDarkTheme
                        themeManager.setDarkTheme(isDarkTheme)
                    },
                    onBackClick = { finish() },
                    onSaveMeal = { meal ->
                        saveMeal(meal)
                        finish()
                    }
                )
            }
        }
    }
    
    private fun saveMeal(meal: MealEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            database.mealDao().insertMeal(meal)
        }
    }
}

@Composable
fun AddMealScreen(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onBackClick: () -> Unit,
    onSaveMeal: (MealEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    
    val isValid = name.isNotBlank() && description.isNotBlank() && price.isNotBlank()
    
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "Add New Meal",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDarkTheme) "Light" else "Dark"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Meal Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                prefix = { Text("$") }
            )
            
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    val meal = MealEntity(
                        name = name.trim(),
                        description = description.trim(),
                        price = price.toDoubleOrNull() ?: 0.0,
                        imageUrl = imageUrl.trim()
                    )
                    onSaveMeal(meal)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isValid
            ) {
                Text("Save Meal")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMealScreenPreview() {
    MyApplicationTheme {
        AddMealScreen(
            isDarkTheme = false,
            onThemeToggle = {},
            onBackClick = {},
            onSaveMeal = {}
        )
    }
}
