package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.data.room.UserDatabase
import com.dicoding.githubuser.ui.favourite.FavouriteRepository

object FavouriteInjection {
    fun providerRepository(context: Context): FavouriteRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()

        return FavouriteRepository.getInstance(dao)
    }
}