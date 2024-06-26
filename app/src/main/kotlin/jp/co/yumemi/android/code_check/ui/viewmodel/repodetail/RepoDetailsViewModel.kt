package jp.co.yumemi.android.code_check.ui.viewmodel.repodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.db.ObjectTransformer
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.model.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class responsible for managing the repository details screen.
 * This ViewModel interacts with the repository database and provides data to the associated fragment.
 *
 * @param bookmarkGitHubAccountRepository The repository for bookmarked GitHub accounts.
 */
@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val bookmarkGitHubAccountRepository: BookmarkGitHubAccountRepository
) : ViewModel() {

    private var _gitHubRepoDetails = MutableLiveData<GitHubAccount>(null)

    /**
     * LiveData representing the GitHub account details.
     */
    val gitHubRepoDetails: LiveData<GitHubAccount>
        get() = _gitHubRepoDetails

    /**
     * A MutableLiveData instance holding the response of a local database query operation.
     * Exposes a LiveData object to observe changes in the local database query response.
     */
    private val _localDbResponse = MutableLiveData<LocalDBQueryResponse?>()

    /**
     * Exposes a LiveData object to observe changes in the local database query response.
     */
    val localDbResponse: LiveData<LocalDBQueryResponse?>
        get() = _localDbResponse

    private val _favouriteStatus = MutableLiveData(false)
    val favouriteStatus get() = _favouriteStatus

    /**
     * Sets the GitHub account details.
     *
     * @param gitHubAccount The GitHubAccount object representing the account details.
     */
    fun setRepoDetails(gitHubAccount: GitHubAccount) {
        _gitHubRepoDetails.value = gitHubAccount
    }

    /**
     * Sets the favourite status of the repository.
     *
     * @param isFavourite Boolean indicating whether the repository is bookmarked.
     */
    fun setFavouriteStatus(isFavourite: Boolean) {
        _favouriteStatus.value = isFavourite
    }

    /**
     * Saves the current repository as a bookmark in the local database.
     * If the repository is already bookmarked, updates its status accordingly.
     */
    fun saveAsBookmark() {
        val gitHubRepoItem = gitHubRepoDetails.value

        if (gitHubRepoItem != null) {
            viewModelScope.launch {
                val gitHubRepoDataEntity = ObjectTransformer.transformObjectToEntity(gitHubRepoItem)
                val response = bookmarkGitHubAccountRepository.insertBookmarkGithubRepoItem(
                    gitHubRepoDataEntity
                )
                response.apply {
                    _localDbResponse.value = this
                    _favouriteStatus.value = this.isSuccess
                }
            }
        }
    }

    /**
     * Deletes the bookmarked repository from the local database.
     *
     * @param id The ID of the repository to be deleted.
     */
    fun deleteFavourite(id: Long) {
        viewModelScope.launch {
            val response = bookmarkGitHubAccountRepository.deleteBookmarkGithubRepoItem(id)
            response.also {
                _localDbResponse.value = it
                when {
                    it.isSuccess -> _favouriteStatus.value = false
                }
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