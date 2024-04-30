package jp.co.yumemi.android.code_check.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.db.BookmarkDao
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem
import jp.co.yumemi.android.code_check.model.LocalDBQueryResponse
import javax.inject.Inject

class BookmarkGitHubAccountRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkGitHubAccountRepository {
    override suspend fun insertBookmarkGithubRepoItem(bookmarkGithubRepoItem: BookmarkGithubRepoItem): LocalDBQueryResponse {
        return try {
            bookmarkDao.insertBookmarkGithubRepoItem(bookmarkGithubRepoItem)
            LocalDBQueryResponse(
                true,
                "Inserted Successfully"
            )
        } catch (e: SQLiteConstraintException) {
            if (e.message?.contains("UNIQUE constraint failed") == true) {
                // Handle the case where the record already exists (primary key constraint violation)
                Log.d("Android Engineer Code Check", "Data already exists")
                LocalDBQueryResponse(
                    false,
                    "Record is already existed"
                )
            } else {
                // Handle other SQLiteConstraintExceptions
                // Return DB exception
                LocalDBQueryResponse(false, e.message.toString())

            }
        }
    }

    override fun getAllBookmarkGithubRepoItems(): LiveData<List<BookmarkGithubRepoItem>>? {
        return bookmarkDao.getAllBookmarkGithubRepoItems()
    }

    override suspend fun deleteBookmarkGithubRepoItem(id: Long): LocalDBQueryResponse {
        return try {
            bookmarkDao.deleteBookmarkGithubRepoItem(id)
            LocalDBQueryResponse(
                true,
                "Deleted successfully"
            )
        } catch (e: SQLiteConstraintException) {
            // Handle other SQLiteConstraintExceptions
            // Return DB exception
            LocalDBQueryResponse(false, e.message.toString())
        }
    }


}