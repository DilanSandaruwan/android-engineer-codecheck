package jp.co.yumemi.android.code_check.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.co.yumemi.android.code_check.constant.DbConstant

/**
 * Entity class representing a bookmarked GitHub repository item in the Room database.
 *
 * @param id The ID of the repository.
 * @param name The name of the repository.
 * @param avatarUrl The URL of the avatar associated with the repository owner.
 * @param htmlUrl The URL of the repository.
 * @param ownerType The type of the repository owner.
 * @param language The programming language used in the repository.
 * @param stargazersCount The number of stars (likes) received by the repository.
 * @param watchersCount The number of users watching the repository for updates.
 * @param forksCount The number of times the repository has been forked.
 * @param openIssuesCount The number of open issues associated with the repository.
 */
@Entity(tableName = DbConstant.ROOM_DB_BOOKMARK_REPO_TABLE)
data class BookmarkGithubRepoItem(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val ownerType: String?,
    val language: String?,
    val stargazersCount: Long?,
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?
)
