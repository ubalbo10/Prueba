package com.example.prueba.models

import com.google.gson.annotations.SerializedName

data class user (
    @SerializedName("nickname") val nickname : String,
    @SerializedName("password") val password : String

    )
