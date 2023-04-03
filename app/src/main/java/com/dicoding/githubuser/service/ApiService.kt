package com.dicoding.githubuser.service

import com.dicoding.githubuser.response.GitHubResponse
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowings(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}