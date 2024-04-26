package jp.co.yumemi.android.code_check.model

/**
 * Data class representing the response from a GitHub search.
 *
 * @param total_count The total count of search results.
 * @param incomplete_results Indicates if the results are incomplete or not.
 * @param items The list of GitHub accounts matching the search criteria.
 */
data class GitHubSearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GitHubAccount>
)
