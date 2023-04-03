package com.dicoding.githubuser.response

import com.google.gson.annotations.SerializedName

data class FollowingsFollowersResponseItem(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)
