package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Parcelable data class representing the owner of a GitHub repository.
 * This class holds information about the owner, including their avatar URL and login name.
 *
 * @param avatarUrl The URL of the owner's avatar image.
 * @param login The login name of the owner.
 */
@Parcelize
data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("login") val login: String?,
) : Parcelable