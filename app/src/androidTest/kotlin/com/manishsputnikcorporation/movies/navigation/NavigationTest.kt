// package com.manishsputnikcorporation.movies.navigation
//
// import androidx.compose.material.Button
// import androidx.compose.material.Text
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.setValue
// import androidx.compose.ui.platform.LocalContext
// import androidx.compose.ui.test.ExperimentalTestApi
// import androidx.compose.ui.test.assertIsDisplayed
// import androidx.compose.ui.test.assertTextEquals
// import androidx.compose.ui.test.junit4.createComposeRule
// import androidx.compose.ui.test.onNodeWithContentDescription
// import androidx.compose.ui.test.onNodeWithText
// import androidx.compose.ui.test.performClick
// import androidx.compose.ui.test.runComposeUiTest
// import androidx.lifecycle.viewmodel.compose.viewModel
// import androidx.navigation.compose.ComposeNavigator
// import androidx.navigation.testing.TestNavHostController
// import com.manishsputnikcorporation.movies.ui.navigation.AppNavigation
// import com.manishsputnikcorporation.movies.ui.theme.MediaTheme
// import org.junit.Before
// import org.junit.Rule
// import org.junit.Test
// TODO: to implement Composable testing
// class NavigationTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun myTest() {
//        composeTestRule.setContent {
//            MediaTheme {
//                var text by remember { mutableStateOf(value = "Hello") }
//                Button(onClick = { text = "Goodbye" }) {
//                    Text(text = text)
//                }
//                composeTestRule.onNodeWithText(text = "Hello").assertTextEquals()
//                composeTestRule.onNodeWithText(text = "Goodbye").assertIsDisplayed()
//            }
//        }
//    }
//
//    @Before
//    fun setupMoviesNavHost() {
//        composeTestRule.setContent {
//            val navController = TestNavHostController(context = LocalContext.current)
//            navController.navigatorProvider.addNavigator(navigator = ComposeNavigator())
//
//            AppNavigation(navController = navController, mainViewModel = viewModel())
//        }
//    }
//
//    @Test
//    fun moviesNavHost_verifyMovieDetailStartDestination() {
//        composeTestRule.setContent {
//            composeTestRule.onNodeWithContentDescription(label = "Movies Home Screen").assertIsDisplayed()
//        }
//    }
//
//    @Test
//    fun moviesNavHost_clickMovie_navigatesToMovieDetail() {
//        composeTestRule.setContent {
//            composeTestRule.onNodeWithContentDescription(label = "Movie Detail").performClick()
//
//            composeTestRule.onNodeWithContentDescription(label = "Movie Detail Screen").assertIsDisplayed()
//        }
//    }
// }
