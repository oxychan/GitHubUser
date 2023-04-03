package com.dicoding.githubuser.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.response.FollowingsFollowersResponseItem
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.response.UserResponse
import com.dicoding.githubuser.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val _isLoadingFollowings = MutableLiveData<Boolean>()
    val isLoadingFollowings: LiveData<Boolean> = _isLoadingFollowings

    private val _userDetail = MutableLiveData<UserResponse>()
    val userDetail: LiveData<UserResponse> = _userDetail

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowings = MutableLiveData<List<ItemsItem>>()
    val listFollowings: LiveData<List<ItemsItem>> = _listFollowings

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.value = response.body()
                        getFollowers(username)
                        getFollowings(username)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = true
            }

        })
    }

    fun getFollowers(username: String) {
        _isLoadingFollowers.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = response.body()
                    }
                }
                _isLoadingFollowers.value = false
            }

            override fun onFailure(
                call: Call<List<ItemsItem>>,
                t: Throwable
            ) {
                Log.d("Prik", t.message.toString())
            }

        })

    }

    fun getFollowings(username: String) {
        _isLoadingFollowings.value = true
        val client = ApiConfig.getApiService().getFollowings(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowings.value = response.body()
                    }
                }
                _isLoadingFollowings.value = false
            }

            override fun onFailure(
                call: Call<List<ItemsItem>>,
                t: Throwable
            ) {
                Log.d("Prik", "follwing error")
            }

        })
    }
}