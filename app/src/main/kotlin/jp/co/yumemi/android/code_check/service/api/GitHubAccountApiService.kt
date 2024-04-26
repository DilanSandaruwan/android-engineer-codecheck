package jp.co.yumemi.android.code_check.service.api

import jp.co.yumemi.android.code_check.model.GitHubSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GitHubAccountApiService {
    companion object {
        /**
         * The base URL for the GitHub API is likely defined elsewhere (e.g., in a String resource).
         * This constant specifies the endpoint path for fetching repositories relative to the base URL.
         */
        const val ENDPOINT_REPOSITORIES = "repositories"
    }

    /**
     * Fetches a list of GitHub repositories matching the provided search query.
     *
     * This method utilizes a GET request to the `/repositories` endpoint with the search query
     * parameter (`q`). The response is expected to be in JSON format (specified by the `Accept` header).
     *
     * @param query The search query string to find repositories.
     *
     * @return A suspendable function that returns a [Response] object containing the search results.
     *         The response body will be parsed into a [GitHubSearchResponse] object if successful.
     *
     * @throws [HttpException] If there is an error during the network request.
     */
    @Headers("Accept: application/vnd.github.v3+json")
    @GET(ENDPOINT_REPOSITORIES)
    suspend fun getGitHubRepositories(@Query("q") query: String): Response<GitHubSearchResponse>
}