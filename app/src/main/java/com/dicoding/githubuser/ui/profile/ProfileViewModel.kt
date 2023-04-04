package com.dicoding.githubuser.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    var currentUser: LiveData<ProfileResult<UserResponse>> = getUser("oxychan")
    fun getUser(username: String): LiveData<ProfileResult<UserResponse>> {
        this.currentUser = profileRepository.getUser(username)
        return currentUser
    }

    fun getFollower(username: String): LiveData<ProfileResult<List<UserEntity>>> =
        profileRepository.getFollowers(username)


    fun getFollowing(username: String): LiveData<ProfileResult<List<UserEntity>>> =
        profileRepository.getFollowings(username)

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                profileRepository.setBookmarkedUser(user)
            }
        }
    }

    suspend fun isExists(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            profileRepository.isExists(username) != null
        }
    }

}