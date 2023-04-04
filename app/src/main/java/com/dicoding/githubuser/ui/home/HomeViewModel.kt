package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.response.GitHubResponse
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.service.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {
    var usersDefault: LiveData<UserResult<List<UserEntity>>> = getUsers("dina")
    fun getUsers(username: String): LiveData<UserResult<List<UserEntity>>> {
        usersDefault = homeRepository.getUsers(username)

        return usersDefault
    }

    fun getBookmarkedUsers() = homeRepository.getBookmarkedUsers()

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            homeRepository.setBookmarkedUser(user, true)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            homeRepository.setBookmarkedUser(user, false)
        }
    }

}