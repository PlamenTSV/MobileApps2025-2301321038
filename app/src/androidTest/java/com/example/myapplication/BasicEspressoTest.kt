package com.example.myapplication

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BasicEspressoTest {
    @Test
    fun mainActivity_starts_andRootIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(isRoot()).check(matches(isDisplayed()))
        }
    }

    @Test
    fun mealDetailsActivity_starts_withIntent_andRootIsDisplayed() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MealDetailsActivity::class.java)
        intent.putExtra("mealId", 1)
        ActivityScenario.launch<MealDetailsActivity>(intent).use {
            onView(isRoot()).check(matches(isDisplayed()))
        }
    }
}
