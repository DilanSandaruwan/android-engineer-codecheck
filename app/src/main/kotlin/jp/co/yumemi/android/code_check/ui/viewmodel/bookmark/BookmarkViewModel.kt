package jp.co.yumemi.android.code_check.ui.viewmodel.bookmark

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepository
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkGitHubAccountRepository: BookmarkGitHubAccountRepository
) : ViewModel() {
    val bookmarkedReposAll = bookmarkGitHubAccountRepository.getAllBookmarkGithubRepoItems()
}