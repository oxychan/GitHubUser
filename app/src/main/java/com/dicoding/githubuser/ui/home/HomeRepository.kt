package com.dicoding.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.githubuser.response.GitHubResponse
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.service.ApiService
import com.dicoding.githubuser.service.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository private constructor(
    private val apiService: ApiService
) {
    private val result = MediatorLiveData<UserResult<List<ItemsItem>>>()
//    private val _listUsers = MutableLiveData<List<ItemsItem>>()
//    val listUsers: LiveData<List<ItemsItem>> = _listUsers
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _errorMessage = MutableLiveData<Event<String>>()
//    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun getUsers(username: String): LiveData<UserResult<List<ItemsItem>>> = liveData {
//        _isLoading.value = true
        emit(UserResult.Loading)
        try {
            val response = apiService.getUsers(username)
            val users = response.items
            emit(UserResult.Success(users))
        } catch (e: Exception) {
            emit(UserResult.Error(e.message.toString()))
        }
//        val client = ApiConfig.getApiService().getUsers(username)
//        client.enqueue(object : Callback<GitHubResponse> {
//            override fun onResponse(
//                call: Call<GitHubResponse>,
//                response: Response<GitHubResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        if (responseBody.items.isEmpty()) {
//                            _errorMessage.value = Event("User not found!")
//                        }
//                        _listUsers.value = response.body()?.items
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
//                _isLoading.value = true
//                _errorMessage.value = Event(t.message.toString())
//            }
//
//        })
    }

    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(
            apiService: ApiService
        ): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(apiService)
            }.also { instance = it }
    }
}