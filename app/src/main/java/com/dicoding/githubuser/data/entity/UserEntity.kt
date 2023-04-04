package com.dicoding.githubuser.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    val username: String,

    @field:ColumnInfo(name = "userProfile")
    val userProfile: String,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)