package jp.co.yumemi.android.code_check.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem

/**
 * Room database class for managing bookmarked GitHub repository items.
 */
@Database(entities = [BookmarkGithubRepoItem::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {

    /**
     * Retrieves the Data Access Object (DAO) for interacting with bookmarked GitHub repository items.
     *
     * @return The DAO interface for bookmarked GitHub repository items.
     */
    abstract fun bookmarkDao(): BookmarkDao
}