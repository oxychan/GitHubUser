package com.dicoding.githubuser.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.data.room.UserDatabase
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.response.UserResponse
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository private constructor(
    private val apiService: ApiService,
    private val database: UserDatabase
) {

    fun getUser(username: String): LiveData<ProfileResult<UserResponse>> = liveData {
        emit(ProfileResult.Loading)
        try {
            val user = apiService.getUser(username)
            emit(ProfileResult.User(user))
            getFollowers(username)
            getFollowings(username)
        } catch (e: Exception) {
            emit(ProfileResult.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<ProfileResult<List<UserEntity>>> = liveData {
        emit(ProfileResult.LoadingFollower)
        try {
            val followersApi = apiService.getFollowers(username)
            val followers = followersApi.map { UserEntity(username = it.login, userProfile = it.avatarUrl, false) }
            emit(ProfileResult.Follower(followers))
        } catch (e: Exception) {
            emit(ProfileResult.Error(e.message.toString()))
        }
    }

    fun getFollowings(username: String): LiveData<ProfileResult<List<UserEntity>>> = liveData {
        emit(ProfileResult.LoadingFollowing)
        try {
            val followingApi = apiService.getFollowings(username)
            val following = followingApi.map { UserEntity(username = it.login, userProfile = it.avatarUrl, false) }
            emit(ProfileResult.Following(following))
        } catch (e: Exception){
            emit(ProfileResult.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(
            apiService: ApiService,
            database: UserDatabase
        ): ProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(apiService, database)
            }.also { instance = it }
    }
}