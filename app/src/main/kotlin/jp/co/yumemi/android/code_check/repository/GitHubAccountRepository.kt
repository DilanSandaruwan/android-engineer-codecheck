package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.service.api.GitHubAccountApiService
import javax.inject.Inject

class GitHubAccountRepository @Inject constructor(private val gitHubAccountApiService: GitHubAccountApiService){
}