package com.dicoding.githubuser.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.di.Injection
import com.dicoding.githubuser.di.ProfileInjection
import com.dicoding.githubuser.ui.home.HomeViewModelFactory

class ProfileViewModelFactory private constructor(private val profileRepository: ProfileRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(profileRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ProfileViewModelFactory? = null
        fun getInstance(context: Context): ProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ProfileViewModelFactory(ProfileInjection.providerRepository(context))
            }.also { instance = it }
    }
}