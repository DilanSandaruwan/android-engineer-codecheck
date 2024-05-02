package jp.co.yumemi.android.code_check.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.co.yumemi.android.code_check.constant.DbConstant.ROOM_DB_BOOKMARK_REPO_TABLE
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem

/**
 * Data Access Object (DAO) interface for interacting with bookmarked GitHub repository items in the database.
 */
@Dao
interface BookmarkDao {
    /**
     * Inserts a bookmarked GitHub repository item into the database.
     *
     * @param bookmarkGithubRepoItem The item to be inserted.
     * @return The ID of the inserted item.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmarkGithubRepoItem(bookmarkGithubRepoItem: BookmarkGithubRepoItem): Long

    /**
     * Retrieves all bookmarked GitHub repository items from the database.
     *
     * @return A LiveData object containing a list of all bookmarked GitHub repository items,
     *         or null if no items are found.
     */
    @Query("SELECT * FROM $ROOM_DB_BOOKMARK_REPO_TABLE")
    fun getAllBookmarkGithubRepoItems(): LiveData<List<BookmarkGithubRepoItem>>?

    /**
     * Deletes a bookmarked GitHub repository item from the database.
     *
     * @param id The ID of the item to be deleted.
     */
    @Query("DELETE FROM $ROOM_DB_BOOKMARK_REPO_TABLE WHERE id = :id")
    suspend fun deleteBookmarkGithubRepoItem(id: Long)

}