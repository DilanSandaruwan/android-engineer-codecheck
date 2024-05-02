package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.model.GitHubSearchResponse
import jp.co.yumemi.android.code_check.model.Owner
import jp.co.yumemi.android.code_check.service.api.GitHubAccountApiService
import jp.co.yumemi.android.code_check.util.network.ApiResultState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class GitHubAccountRepositoryTest{
    @Mock
    private lateinit var gitHubAccountApiService: GitHubAccountApiService

    private lateinit var repository: GitHubAccountRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubAccountRepository(gitHubAccountApiService)
    }

    @Test
    fun testGetGitHubAccountFromDataSource_Success() = runBlocking {
        val testDataList = GitHubSearchResponse(
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

        Mockito.`when`(gitHubAccountApiService.getGitHubRepositories(ArgumentMatchers.anyString()))
            .thenReturn(Response.success(testDataList))

        val result = repository.getGitHubAccountFromDataSource("kotlin")

        Mockito.verify(gitHubAccountApiService).getGitHubRepositories("kotlin")
        assert(result is ApiResultState.Success)
        assert((result as ApiResultState.Success).data == testDataList)
    }
}