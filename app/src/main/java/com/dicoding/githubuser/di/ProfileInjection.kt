package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.data.room.UserDatabase
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.ui.profile.ProfileRepository

object ProfileInjection {
    fun providerRepository(context: Context): ProfileRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()

        return ProfileRepository.getInstance(apiService, database, dao)
    }
}