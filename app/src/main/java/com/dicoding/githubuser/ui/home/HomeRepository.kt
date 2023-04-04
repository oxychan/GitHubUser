package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.service.ApiService

class HomeRepository private constructor(
    private val apiService: ApiService
) {
    fun getUsers(username: String): LiveData<UserResult<List<ItemsItem>>> = liveData {
        emit(UserResult.Loading)
        try {
            val response = apiService.getUsers(username)
            val users = response.items
            if (users.isEmpty()) {
                emit(UserResult.Error("User not found!"));
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
            apiService: ApiService
        ): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(apiService)
            }.also { instance = it }
    }
}