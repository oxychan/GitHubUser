package com.dicoding.githubuser.ui.favourite

import androidx.lifecycle.ViewModel

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository) : ViewModel() {
    fun getBookmarkedUsers() = favouriteRepository.getBookmarkedUsers()
}