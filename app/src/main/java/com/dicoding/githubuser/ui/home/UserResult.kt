package com.dicoding.githubuser.ui.home

sealed class UserResult<out R> private constructor() {
    data class Success<out T>(val data: T) : UserResult<T>()
    data class Error(val error: String) : UserResult<Nothing>()
    object Loading : UserResult<Nothing>()
}