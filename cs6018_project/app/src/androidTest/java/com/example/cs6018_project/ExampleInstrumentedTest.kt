package com.example.cs6018_project

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cs6018_project.activity.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.cs6018_project", appContext.packageName)
    }
//
//    @Test
//    fun testClick() {
//        onView(withId(R.id.bt1)).perform(click())
//    }
//
//    @Test
//    fun testClick2() {
//        onView(withId(R.id.bt1)).perform(click())
//        onView(withId(R.id.button_circle)).perform(click())
//        onView(withId(R.id.button_square)).perform(click())
//    }
//
//    @Test
//    fun testFragmentView() {
//        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun testSwithView() {
//        onView(withId(R.id.bt1)).perform(click())
//        onView(withId(R.id.customView)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun testBack() {
//        onView(withId(R.id.bt1)).perform(click())
//        pressBack()
//        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()))
//    }
}