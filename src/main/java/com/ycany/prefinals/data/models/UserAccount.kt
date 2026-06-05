package com.ycany.prefinals.data.models

data class UserAccount(
    val username: String,
    val password: String,
    val fullName: String = "",
    val email: String = ""
)




