package com.dicoding.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    val userProfile: String,
    val username: String,
    val name: String?,
    val followers: Int,
    val followings: Int
) : Parcelable
