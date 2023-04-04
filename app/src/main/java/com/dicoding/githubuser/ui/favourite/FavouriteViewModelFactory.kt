package com.dicoding.githubuser.ui.favourite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.di.FavouriteInjection

class FavouriteViewModelFactory private constructor(private val favouriteRepository: FavouriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(favouriteRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavouriteViewModelFactory? = null
        fun getInstance(context: Context): FavouriteViewModelFactory =
            instance ?: synchronized(this) {
                instance
                    ?: FavouriteViewModelFactory(FavouriteInjection.providerRepository(context))
            }.also { instance = it }
    }
}