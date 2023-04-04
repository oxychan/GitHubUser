package com.dicoding.githubuser.ui.profile

sealed class ProfileResult<out R> private constructor() {
    data class User<out T>(val data: T) : ProfileResult<T>()
    data class Following<out T>(val data: T) : ProfileResult<T>()
    data class Follower<out T>(val data: T) : ProfileResult<T>()
    data class Error(val message: String) : ProfileResult<Nothing>()
    object Loading : ProfileResult<Nothing>()
    object LoadingFollowing : ProfileResult<Nothing>()
    object LoadingFollower : ProfileResult<Nothing>()
}