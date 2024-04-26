package jp.co.yumemi.android.code_check.repository

import android.util.Log
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constant.StringConstant.TAG
import jp.co.yumemi.android.code_check.model.GitHubSearchResponse
import jp.co.yumemi.android.code_check.service.api.GitHubAccountApiService
import jp.co.yumemi.android.code_check.util.network.ApiResultState
import jp.co.yumemi.android.code_check.util.network.retryIOOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class GitHubAccountRepository @Inject constructor(private val gitHubAccountApiService: GitHubAccountApiService) {
    /**
     * Retrieves GitHub account information from the data source.
     *
     * @param query The search query.
     * @return The GitHubSearchResponse object containing the account information, or null if unsuccessful.
     */
    suspend fun getGitHubAccountFromDataSource(query: String): ApiResultState<GitHubSearchResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext retryIOOperation(3) {
                getResponseFromRemoteService(query)
            }
        }
    }

    /**
     * Retrieves the response from the remote GitHub Account API service.
     *
     * @param query The search query.
     * @return An [ApiResultState] representing the response from the API service, which can be a success or a failure.
     */
    private suspend fun getResponseFromRemoteService(query: String): ApiResultState<GitHubSearchResponse?> {
        return try {
            val response = gitHubAccountApiService.getGitHubRepositories(query)
            if (response.isSuccessful) {
                ApiResultState.Success(response.body())
            } else {
                when (response.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> {
                        val errorMessage = "Resource not found"
                        logError(errorMessage)
                        ApiResultState.Failed(R.string.resource_not_found, null)
                    }

                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        val errorMessage = "Unauthorized access"
                        logError(errorMessage)
                        ApiResultState.Failed(R.string.unauthorized_access, null)
                    }

                    else -> {
                        if (!response.raw().message.isNullOrEmpty()) {
                            val errorMessage = response.raw().message
                            logError(errorMessage)
                            ApiResultState.Failed(R.string.something_wrong_with_code, errorMessage)
                        } else {
                            val errorMessage = response.code().toString()
                            logError("Something went wrong!\n- Status Code: $errorMessage")
                            ApiResultState.Failed(R.string.something_wrong_with_code, errorMessage)
                        }

                    }
                }
            }
        } catch (exception: IOException) {
            val errorMessage = exception.message
            logError("Network error - $errorMessage")
            ApiResultState.Failed(R.string.network_error, errorMessage)
        } catch (exception: Exception) {
            val errorMessage = exception.localizedMessage
            logError("Something went wrong!\n- $errorMessage")
            ApiResultState.Failed(R.string.something_wrong, errorMessage)
        }
    }

    /**
     * Logs an error message with a specific tag.
     *
     * @param errorMessage The error message to be logged.
     */
    private fun logError(errorMessage: String) {
        val logTag = "$TAG - Response Check"
        Log.e(logTag, "getResponseFromRemoteService: $errorMessage")
    }
}