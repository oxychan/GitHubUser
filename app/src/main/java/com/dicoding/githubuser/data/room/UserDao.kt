package com.dicoding.githubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuser.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY username DESC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users")
    fun getBookmarkedUsers(): LiveData<MutableList<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users where username = :username")
    fun getById(username: String): UserEntity

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}