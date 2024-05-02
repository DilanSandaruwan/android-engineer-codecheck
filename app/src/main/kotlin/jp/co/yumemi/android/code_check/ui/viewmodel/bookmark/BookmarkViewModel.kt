package jp.co.yumemi.android.code_check.ui.viewmodel.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkGitHubAccountRepository: BookmarkGitHubAccountRepository
) : ViewModel() {
    val bookmarkedReposAll = bookmarkGitHubAccountRepository.getAllBookmarkGithubRepoItems()

    /**
     * A MutableLiveData instance holding the response of a local database query operation.
     * Exposes a LiveData object to observe changes in the local database query response.
     */
    private val _localDbResponse = MutableLiveData<LocalDBQueryResponse?>()

    /**
     * Exposes a LiveData object to observe changes in the local database query response.
     */
    val localDbResponse: MutableLiveData<LocalDBQueryResponse?>
        get() = _localDbResponse

    fun deleteFavourite(id: Long) {
        viewModelScope.launch {
            val response = bookmarkGitHubAccountRepository.deleteBookmarkGithubRepoItem(id)
            response.apply {
                _localDbResponse.value = this
            }
        }
    }

    fun resetLocalDbResponse() {
        _localDbResponse.value = null
    }
}