package jp.co.yumemi.android.code_check.db

import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem
import jp.co.yumemi.android.code_check.model.GitHubAccount

/**
 * Object containing methods for transforming objects.
 */
object ObjectTransformer {

    /**
     * Transforms a GitHubAccount object into a BookmarkGithubRepoItem entity.
     *
     * @param gitHubRepoItem The GitHubAccount object to transform.
     * @return The transformed BookmarkGithubRepoItem entity.
     */
    fun transformObjectToEntity(gitHubRepoItem: GitHubAccount): BookmarkGithubRepoItem {
        return BookmarkGithubRepoItem(
            id = gitHubRepoItem.id,
            name = gitHubRepoItem.name ?: "",
            language = gitHubRepoItem.language,
            stargazersCount = gitHubRepoItem.stargazersCount,
            watchersCount = gitHubRepoItem.watchersCount,
            forksCount = gitHubRepoItem.forksCount,
            openIssuesCount = gitHubRepoItem.openIssuesCount,
            avatarUrl = gitHubRepoItem.owner?.avatarUrl,
            ownerType = gitHubRepoItem.owner?.type,
            htmlUrl = gitHubRepoItem.htmlUrl,
        )
    }
}