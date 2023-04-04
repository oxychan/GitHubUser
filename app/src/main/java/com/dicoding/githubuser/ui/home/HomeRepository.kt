package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.data.room.UserDao
import com.dicoding.githubuser.data.room.UserDatabase
import com.dicoding.githubuser.service.ApiService

class HomeRepository private constructor(
    private val apiService: ApiService,
    private val database: UserDatabase,
    private val dao: UserDao
) {
    fun getUsers(username: String): LiveData<UserResult<List<UserEntity>>> = liveData {
        emit(UserResult.Loading)
        try {
            val response = apiService.getUsers(username)
            val apiUsers = response.items.map { UserEntity(
                username = it.login,
                userProfile = it.avatarUrl,
                isBookmarked = false
            ) }
            val dbUsers = dao.getBookmarkedUsers().value ?: emptyList()
            val users = apiUsers.map { user ->
                val bookmarked = dbUsers.any { it.username == user.username }
                user.copy(isBookmarked = bookmarked)
            }
            if (users.isEmpty()) {
                emit(UserResult.Error("User not found!"));
            }
            emit(UserResult.Success(users))
        } catch (e: Exception) {
            emit(UserResult.Error(e.message.toString()))
        }
    }

    fun getBookmarkedUsers(): LiveData<List<UserEntity>> {
        return dao.getBookmarkedUsers()
    }

    suspend fun setBookmarkedUser(user: UserEntity, bookmakrkedState: Boolean) {
        user.isBookmarked = bookmakrkedState
        dao.updateUser(user)
    }

    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(
            apiService: ApiService,
            database: UserDatabase,
            dao: UserDao
        ): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(apiService, database, dao)
            }.also { instance = it }
    }
}