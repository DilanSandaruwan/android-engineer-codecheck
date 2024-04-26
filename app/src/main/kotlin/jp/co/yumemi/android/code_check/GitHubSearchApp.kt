package jp.co.yumemi.android.code_check

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the GitHubSearchApp Android application.
 * This class initializes the Hilt dependency injection framework for the entire application.
 *
 * @constructor Creates an instance of the GitHubSearchApp.
 */
@HiltAndroidApp
class GitHubSearchApp : Application() {
}