package com.dicoding.githubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuser.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY username DESC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users where bookmarked = 1")
    fun getBookmarkedUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username AND bookmarked = 1)")
    suspend fun isNewsBookmarked(username: String): Boolean
}