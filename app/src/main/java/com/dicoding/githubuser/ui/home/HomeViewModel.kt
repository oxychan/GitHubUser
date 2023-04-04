package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.entity.UserEntity

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    var usersDefault: LiveData<UserResult<List<UserEntity>>> = getUsers("dina")

    fun getUsers(username: String): LiveData<UserResult<List<UserEntity>>> {
        usersDefault = homeRepository.getUsers(username)

        return usersDefault
    }

}