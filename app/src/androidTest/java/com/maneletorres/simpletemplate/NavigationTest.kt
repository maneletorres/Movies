package com.maneletorres.simpletemplate

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.maneletorres.simpletemplate.ui.MoviesNavHost
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupMoviesNavHost() {
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            MoviesNavHost(navController = navController)
        }
    }

    @Test
    fun moviesNavHost_verifyMovieDetailStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Movies Home Screen")
            .assertIsDisplayed()
    }

    @Test
    fun moviesNavHost_clickMovie_navigatesToMovieDetail() {
        composeTestRule
            .onNodeWithContentDescription("Movie Detail")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Movie Detail Screen")
            .assertIsDisplayed()
    }
}