package com.dicoding.githubuser.service

import com.dicoding.githubuser.response.GitHubResponse
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.response.UserResponse
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") username: String
    ): GitHubResponse

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getUser(
        @Path("username") username: String
    ): UserResponse

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<ItemsItem>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    suspend fun getFollowings(
        @Path("username") username: String
    ): List<ItemsItem>
}