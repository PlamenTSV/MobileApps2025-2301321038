package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class ThemeManager(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("app_theme", Context.MODE_PRIVATE)
    
    private val DARK_THEME_KEY = "is_dark_theme"
    
    fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(DARK_THEME_KEY, false)
    }
    
    fun setDarkTheme(isDark: Boolean) {
        sharedPreferences.edit()
            .putBoolean(DARK_THEME_KEY, isDark)
            .apply()
    }
}
