package jp.co.yumemi.android.code_check.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.constant.DbConstant
import jp.co.yumemi.android.code_check.db.BookmarkDao
import jp.co.yumemi.android.code_check.db.BookmarkDatabase
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepository
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepositoryImpl
import javax.inject.Singleton

/**
 * Module providing dependencies related to the database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides an instance of the [BookmarkDatabase] using the application context.
     *
     * @param application The application context.
     * @return An instance of [BookmarkDatabase].
     */
    @Provides
    @Singleton
    fun provideBookmarkDatabase(application: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            application,
            BookmarkDatabase::class.java,
            DbConstant.ROOM_DB_GITHUB_REPO_DATABASE
        ).build()
    }

    /**
     * Provides an instance of [BookmarkDao] using the [BookmarkDatabase].
     *
     * @param database The database instance.
     * @return An instance of [BookmarkDao].
     */
    @Provides
    @Singleton
    fun provideGitHubObjectDao(database: BookmarkDatabase): BookmarkDao {
        return database.bookmarkDao()
    }

    /**
     * Provides an instance of [BookmarkGitHubAccountRepository] using the [BookmarkDao].
     *
     * @param dao The Data Access Object for bookmarked GitHub accounts.
     * @return An instance of [BookmarkGitHubAccountRepository].
     */
    @Provides
    @Singleton
    fun provideBookmarkGitHubAccountRepository(
        dao: BookmarkDao
    ): BookmarkGitHubAccountRepository {
        return BookmarkGitHubAccountRepositoryImpl(dao)
    }
}