package jp.co.yumemi.android.code_check.ui.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.model.GitHubSearchResponse
import jp.co.yumemi.android.code_check.model.Owner
import jp.co.yumemi.android.code_check.repository.BookmarkGitHubAccountRepository
import jp.co.yumemi.android.code_check.repository.GitHubAccountRepository
import jp.co.yumemi.android.code_check.util.exception.CustomErrorModel
import jp.co.yumemi.android.code_check.util.network.ApiResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var gitHubAccountRepository: GitHubAccountRepository

    @Mock
    private lateinit var bookmarkGitHubAccountRepository: BookmarkGitHubAccountRepository

    @Mock
    private lateinit var showLoaderObserver: Observer<Boolean>

    @Mock
    private lateinit var showErrorObserver: Observer<CustomErrorModel?>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(gitHubAccountRepository, bookmarkGitHubAccountRepository)
        viewModel.showLoader.observeForever(showLoaderObserver)
        viewModel.showError.observeForever(showErrorObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.showLoader.removeObserver(showLoaderObserver)
        viewModel.showError.removeObserver(showErrorObserver)
    }

    @Test
    fun testSearchRepositories_Success() = runBlocking {
        val testDatalist = GitHubSearchResponse(
            total_count = 283514,
            incomplete_results = false,
            items = arrayListOf(
                GitHubAccount(
                    id = 1,
                    name = "kotlin",
                    fullName = "JetBrains/kotlin",
                    owner = Owner(
                        login = "JetBrains",
                        avatarUrl = "https://avatars.githubusercontent.com/u/878437?v=4",
                        type = "User"
                    ),
                    htmlUrl = "https://github.com/JetBrains/kotlin",
                    description = "The Kotlin Programming Language. ",
                    stargazersCount = 46145,
                    watchersCount = 46145,
                    language = "Kotlin",
                    forksCount = 5698,
                    openIssuesCount = 162,
                    watchers = 46145,
                )
            )
        )

        Mockito.`when`(gitHubAccountRepository.getGitHubAccountFromDataSource(ArgumentMatchers.anyString()))
            .thenReturn(ApiResultState.Success(testDatalist))

        viewModel.searchRepositories("kotlin")

        Mockito.verify(showLoaderObserver).onChanged(true)

        Mockito.verify(showLoaderObserver).onChanged(false)

        // Can assert the LiveData values like this:
        assert(viewModel.gitHubRepoList.value == testDatalist.items)
    }

    @Test
    fun testSearchRepositories_Failure() = runBlocking {
        val errorMessage = "Error message"
        Mockito.`when`(gitHubAccountRepository.getGitHubAccountFromDataSource(ArgumentMatchers.anyString()))
            .thenReturn(ApiResultState.Failed(R.string.something_wrong, errorMessage))

        viewModel.searchRepositories("query")

        Mockito.verify(showLoaderObserver).onChanged(true)
        Mockito.verify(showLoaderObserver).onChanged(false)
        Mockito.verify(showErrorObserver).onChanged(
            CustomErrorModel(R.string.something_wrong, errorMessage)
        )
        Mockito.verify(showLoaderObserver).onChanged(false)
    }

    @Test
    fun testResetShowError() {
        viewModel.resetShowError()

        Mockito.verify(showErrorObserver).onChanged(null)
    }

}