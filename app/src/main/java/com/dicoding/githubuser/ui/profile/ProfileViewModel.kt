package com.dicoding.githubuser.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.response.FollowingsFollowersResponseItem
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.response.UserResponse
import com.dicoding.githubuser.service.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    var currentUser: LiveData<ProfileResult<UserResponse>> = getUser("oxychan")
    fun getUser(username: String): LiveData<ProfileResult<UserResponse>> {
        this.currentUser = profileRepository.getUser(username)
        return currentUser
    }

    fun getFollower(username: String): LiveData<ProfileResult<List<ItemsItem>>> = profileRepository.getFollowers(username)


    fun getFollowing(username: String): LiveData<ProfileResult<List<ItemsItem>>> = profileRepository.getFollowings(username)

}