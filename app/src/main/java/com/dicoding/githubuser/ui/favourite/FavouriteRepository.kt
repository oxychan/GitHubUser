package com.dicoding.githubuser.ui.favourite

import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.data.room.UserDao

class FavouriteRepository private constructor(
    private val dao: UserDao
) {
    fun getBookmarkedUsers(): LiveData<MutableList<UserEntity>> {
        return dao.getBookmarkedUsers()
    }

    companion object {
        @Volatile
        private var instance: FavouriteRepository? = null
        fun getInstance(
            dao: UserDao,
        ): FavouriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavouriteRepository(dao)
            }.also { instance = it }
    }
}