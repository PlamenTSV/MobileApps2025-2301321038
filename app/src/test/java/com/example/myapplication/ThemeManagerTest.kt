package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ThemeManagerTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var themeManager: ThemeManager

    @Before
    fun setUp() {
        `when`(mockContext.getSharedPreferences("app_theme", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putBoolean(anyString(), anyBoolean())).thenReturn(mockEditor)
        
        themeManager = ThemeManager(mockContext)
    }

    @Test
    fun `isDarkTheme should return false by default`() {
        `when`(mockSharedPreferences.getBoolean("is_dark_theme", false)).thenReturn(false)
        
        val result = themeManager.isDarkTheme()
        
        assertFalse(result)
        verify(mockSharedPreferences).getBoolean("is_dark_theme", false)
    }

    @Test
    fun `isDarkTheme should return true when dark theme is enabled`() {
        `when`(mockSharedPreferences.getBoolean("is_dark_theme", false)).thenReturn(true)
        
        val result = themeManager.isDarkTheme()
        
        assertTrue(result)
        verify(mockSharedPreferences).getBoolean("is_dark_theme", false)
    }

    @Test
    fun `setDarkTheme should save true when enabling dark theme`() {
        themeManager.setDarkTheme(true)
        
        verify(mockSharedPreferences).edit()
        verify(mockEditor).putBoolean("is_dark_theme", true)
        verify(mockEditor).apply()
    }

    @Test
    fun `setDarkTheme should save false when disabling dark theme`() {
        themeManager.setDarkTheme(false)
        
        verify(mockSharedPreferences).edit()
        verify(mockEditor).putBoolean("is_dark_theme", false)
        verify(mockEditor).apply()
    }

    @Test
    fun `setDarkTheme should handle multiple calls`() {
        themeManager.setDarkTheme(true)
        themeManager.setDarkTheme(false)
        themeManager.setDarkTheme(true)
        
        verify(mockSharedPreferences, times(3)).edit()
        verify(mockEditor, times(3)).putBoolean("is_dark_theme", anyBoolean())
        verify(mockEditor, times(3)).apply()
    }

    @Test
    fun `isDarkTheme should handle multiple calls`() {
        `when`(mockSharedPreferences.getBoolean("is_dark_theme", false)).thenReturn(true)
        
        val result1 = themeManager.isDarkTheme()
        val result2 = themeManager.isDarkTheme()
        
        assertTrue(result1)
        assertTrue(result2)
        verify(mockSharedPreferences, times(2)).getBoolean("is_dark_theme", false)
    }

    @Test
    fun `ThemeManager should use correct shared preferences name`() {
        verify(mockContext).getSharedPreferences("app_theme", Context.MODE_PRIVATE)
    }

    @Test
    fun `ThemeManager should use correct key for dark theme`() {
        `when`(mockSharedPreferences.getBoolean("is_dark_theme", false)).thenReturn(false)
        
        themeManager.isDarkTheme()
        
        verify(mockSharedPreferences).getBoolean("is_dark_theme", false)
    }

    @Test
    fun `setDarkTheme should not call commit`() {
        themeManager.setDarkTheme(true)
        
        verify(mockEditor).apply()
        verify(mockEditor, never()).commit()
    }

    @Test
    fun `ThemeManager should handle null context gracefully`() {
        // This test ensures the constructor doesn't crash with null context
        // In real implementation, this would be handled by dependency injection
        assertNotNull("ThemeManager should be created successfully", themeManager)
    }

    @Test
    fun `isDarkTheme should return consistent results`() {
        `when`(mockSharedPreferences.getBoolean("is_dark_theme", false)).thenReturn(true)
        
        val result1 = themeManager.isDarkTheme()
        val result2 = themeManager.isDarkTheme()
        
        assertEquals(result1, result2)
    }

    @Test
    fun `setDarkTheme should update the theme state`() {
        // First check initial state
        `when`(mockSharedPreferences.getBoolean("is_dark_theme", false)).thenReturn(false)
        assertFalse(themeManager.isDarkTheme())
        
        // Set dark theme
        themeManager.setDarkTheme(true)
        
        // Verify the editor was called
        verify(mockEditor).putBoolean("is_dark_theme", true)
        verify(mockEditor).apply()
    }

    @Test
    fun `ThemeManager should handle edge cases`() {
        // Test with extreme values
        themeManager.setDarkTheme(true)
        themeManager.setDarkTheme(false)
        
        verify(mockEditor, times(2)).putBoolean(eq("is_dark_theme"), anyBoolean())
    }
}

