package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.ui.home.HomeRepository
import com.dicoding.githubuser.ui.profile.ProfileRepository

object Injection {
    fun providerRepository(context: Context): HomeRepository {
        val apiService = ApiConfig.getApiService()

        return HomeRepository.getInstance(apiService)
    }
}