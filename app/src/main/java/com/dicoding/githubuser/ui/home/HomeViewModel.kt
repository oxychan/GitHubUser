package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.response.GitHubResponse
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.service.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {
    var usersDefault: LiveData<UserResult<List<ItemsItem>>> = getUsers("dina")
    fun getUsers(username: String): LiveData<UserResult<List<ItemsItem>>> {
        usersDefault = homeRepository.getUsers(username)

        return usersDefault
    }

}