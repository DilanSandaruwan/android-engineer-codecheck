package jp.co.yumemi.android.code_check.repository

import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem
import jp.co.yumemi.android.code_check.model.LocalDBQueryResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for interacting with bookmarked GitHub repository items.
 */
interface BookmarkGitHubAccountRepository {
    /**
     * Inserts a bookmarked GitHub repository item into the local database.
     *
     * @param bookmarkGithubRepoItem The item to be inserted.
     * @return A [LocalDBQueryResponse] indicating the result of the insertion operation.
     */
    suspend fun insertBookmarkGithubRepoItem(bookmarkGithubRepoItem: BookmarkGithubRepoItem): LocalDBQueryResponse

    /**
     * Retrieves all bookmarked GitHub repository items from the local database.
     *
     * @return A LiveData object containing a list of all bookmarked GitHub repository items,
     *         or null if no items are found.
     */
    fun getAllBookmarkGithubRepoItems(): LiveData<List<BookmarkGithubRepoItem>>?

    /**
     * Deletes a bookmarked GitHub repository item from the local database.
     *
     * @param id The ID of the item to be deleted.
     * @return A [LocalDBQueryResponse] indicating the result of the deletion operation.
     */
    suspend fun deleteBookmarkGithubRepoItem(id: Long): LocalDBQueryResponse
}