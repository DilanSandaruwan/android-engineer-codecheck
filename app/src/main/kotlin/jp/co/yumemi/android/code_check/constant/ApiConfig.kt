package jp.co.yumemi.android.code_check.constant

/**
 * An enum class representing the different API configurations used throughout the application.
 *
 * Each enum constant holds a key-value pair, where the key is the name of the configuration
 * and the value is the corresponding URL string.
 */
enum class ApiConfig(val value: String) {

    /**
     * The base URL for the GitHub API. This URL is used for making requests to the GitHub API endpoints.
     */
    BASE_URL("https://api.github.com/search/"),

    /**
     * A common URL used for testing internet connectivity. This URL is a reliable source to check
     * if the device has an active internet connection.
     */
    TEST_URL("https://www.google.com")
}