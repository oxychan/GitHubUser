package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.data.room.UserDatabase
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.ui.home.HomeRepository
import com.dicoding.githubuser.ui.profile.ProfileRepository

object Injection {
    fun providerRepository(context: Context): HomeRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()

        return HomeRepository.getInstance(apiService, database, dao)
    }
}