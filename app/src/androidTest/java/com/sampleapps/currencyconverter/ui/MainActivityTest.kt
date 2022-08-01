package com.sampleapps.currencyconverter.ui


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith
import com.sampleapps.currencyconverter.R

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
internal class MainActivityTest {

    @Test
    fun test_visibility_mainactivity() {
        val scenario=ActivityScenario.launch(MainActivity::class.java)
        val onView = onView(withId(R.id.main)).check(matches(isDisplayed()))
    }


}