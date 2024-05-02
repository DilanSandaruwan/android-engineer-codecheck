package jp.co.yumemi.android.code_check.ui.viewmodel.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class responsible for managing the bookmarked repositories screen.
 * This ViewModel interacts with the repository database to provide data to the associated fragment.
 *
 * @param bookmarkGitHubAccountRepository The repository for bookmarked GitHub accounts.
 */
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

    /**
     * Deletes a bookmarked repository from the local database.
     *
     * @param id The ID of the repository to be deleted.
     */
    fun deleteFavourite(id: Long) {
        viewModelScope.launch {
            val response = bookmarkGitHubAccountRepository.deleteBookmarkGithubRepoItem(id)
            response.apply {
                _localDbResponse.value = this
            }
        }
    }

    /**
     * Resets the local database query response.
     */
    fun resetLocalDbResponse() {
        _localDbResponse.value = null
    }
}