package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.service.ApiService

class HomeRepository private constructor(
    private val apiService: ApiService,
) {
    fun getUsers(username: String): LiveData<UserResult<List<UserEntity>>> = liveData {
        emit(UserResult.Loading)
        try {
            val response = apiService.getUsers(username)
            val users = response.items.map {
                UserEntity(
                    username = it.login,
                    userProfile = it.avatarUrl,
                    isBookmarked = false
                )
            }
            if (users.isEmpty()) {
                emit(UserResult.Error("User not found!"))
            }
            emit(UserResult.Success(users))
        } catch (e: Exception) {
            emit(UserResult.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(apiService)
            }.also { instance = it }
    }
}