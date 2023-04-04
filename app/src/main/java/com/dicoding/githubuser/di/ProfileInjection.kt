package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.service.ApiConfig
import com.dicoding.githubuser.ui.profile.ProfileRepository

object ProfileInjection {
    fun providerRepository(context: Context): ProfileRepository {
        val apiService = ApiConfig.getApiService()

        return ProfileRepository.getInstance(apiService)
    }
}