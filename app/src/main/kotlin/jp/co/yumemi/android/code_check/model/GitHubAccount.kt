package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Parcelable data class representing a GitHub account.
 * This class holds information about a GitHub repository, including its name, full name, owner details,
 * URL, description, programming language, star count, watchers count, fork count, and open issues count.
 *
 * @param id The id of the GitHub repository.
 * @param name The name of the GitHub repository.
 * @param fullName The full name of the GitHub repository.
 * @param owner The owner details of the GitHub repository.
 * @param htmlUrl The URL of the GitHub repository.
 * @param description The description of the GitHub repository.
 * @param language The programming language used in the GitHub repository.
 * @param stargazersCount The number of stars (likes) received by the GitHub repository.
 * @param watchersCount The number of watchers (followers) of the GitHub repository.
 * @param forksCount The number of forks (copies) of the GitHub repository.
 * @param openIssuesCount The number of open issues in the GitHub repository.
 * @param watchers The number of watchers (followers) of the GitHub repository.
 */
@Parcelize
data class GitHubAccount(
    @SerializedName("id") val id:Long,
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("owner") val owner: Owner?,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stargazersCount: Long?,
    @SerializedName("watchers_count") val watchersCount: Long?,
    @SerializedName("forks_count") val forksCount: Long?,
    @SerializedName("open_issues_count") val openIssuesCount: Long?,
    @SerializedName("watchers") val watchers: Long?,
):Parcelable
