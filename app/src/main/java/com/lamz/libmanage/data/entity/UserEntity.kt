package com.lamz.libmanage.data.entity

data class UserEntity(
    val id: Int = 0,
    val username: String,
    val password: String,
    val role: String
)