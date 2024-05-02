package jp.co.yumemi.android.code_check.model

/**
 * Data class representing the response of a local database query operation.
 *
 * @param isSuccess A boolean indicating whether the query operation was successful.
 * @param message An optional message providing additional information about the query operation.
 */
data class LocalDBQueryResponse(
    val isSuccess: Boolean,
    val message: String?,
)
