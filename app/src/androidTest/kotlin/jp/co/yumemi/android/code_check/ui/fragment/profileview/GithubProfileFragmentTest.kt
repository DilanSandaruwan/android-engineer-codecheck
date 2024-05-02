package jp.co.yumemi.android.code_check.ui.fragment.profileview

import android.os.Bundle
import android.webkit.WebView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

/**
 * Instrumented test class for testing the behavior of the [GithubProfileFragment].
 */
@RunWith(AndroidJUnit4::class)
class GithubProfileFragmentTest {

    // Mock NavController for fragment navigation
    private lateinit var navController: NavController

    /**
     * Sets up the test environment before each test method is run.
     */
    @Before
    fun setUp() {
        // Create a mock NavController
        navController = mock(NavController::class.java)

        // Create a bundle with profile URL argument
        val bundle = Bundle().apply {
            putString("profileUrl", "https://github.com/flutter/flutter")
        }

        // Launch the fragment with arguments
        val scenario = launchFragmentInContainer<GithubProfileFragment>(bundle)

        // Set up NavController for fragment
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    /**
     * Tests the initialization of the fragment.
     */
    @Test
    fun testFragmentInitialization() {
        // Verify that the WebView is correctly displayed
        onView(withId(R.id.webViewProfile)).check(matches(isDisplayed()))
    }

    /**
     * Tests the configuration of the WebView.
     */
    @Test
    fun testWebViewConfiguration() {
        // Verify WebView settings
        onView(withId(R.id.webViewProfile)).check { view, _ ->
            val webView = view as WebView
            assert(webView.settings.javaScriptEnabled)
            assert(webView.settings.loadWithOverviewMode)
            assert(webView.settings.useWideViewPort)
        }
    }

    /**
     * Tests the loading of the profile URL into the WebView.
     */
    @Test
    fun testLoadProfileUrl() {
        // Mock profile URL
        val profileUrl = "https://github.com/flutter/flutter"

        // Set profile URL
        val fragment = GithubProfileFragment()
        fragment.profileUrl = profileUrl

        // Verify that the WebView loads the correct URL
        onView(withId(R.id.webViewProfile)).check { view, _ ->
            val webView = view as WebView
            assert(webView.url == profileUrl)
        }
    }
}