package com.example.cs6018_project

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cs6018_project.activity.MainActivity
import com.example.cs6018_project.mvvm.BoardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
class NavigationAndViewModelTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var scope: CoroutineScope
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
//
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.cs6018_project", appContext.packageName)
//    }

    @Test
    fun testViewModel() {
        val vm = BoardViewModel()
        assertNull(vm.boardData.value)
        assertNull(vm.boardData.value?.shapeOfPen)
        assertNull(vm.boardData.value?.color)
    }

    @Test
    fun testEntryPage() {
        scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            delay(2000)
            composeTestRule.onNodeWithText("bt1").assertIsDisplayed()
            // test EntryPage UI + navigation from EntryPage to CanvasPage
            composeTestRule.onNodeWithContentDescription("Go to Second Fragment").performClick()

            composeTestRule.onNodeWithText("button_circle").assertIsDisplayed()
            composeTestRule.onNodeWithText("button_square").assertIsDisplayed()

            composeTestRule.onNodeWithText("button_blue_color").assertIsDisplayed()
            composeTestRule.onNodeWithText("button_red_color").assertIsDisplayed()
            composeTestRule.onNodeWithTag("button_green_color").performClick()
            composeTestRule.onNodeWithText("button_yellow_color").assertIsDisplayed()
            composeTestRule.onNodeWithText("button_cyan_color").assertIsDisplayed()
            composeTestRule.onNodeWithText("button_gary_color").assertIsDisplayed()
            composeTestRule.onNodeWithText("button_black_color").assertIsDisplayed()
        }
    }
}