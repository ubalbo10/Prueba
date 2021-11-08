package com.example.prueba.models

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class trabajadorResponse(
    @SerializedName("nickname") val nickname : String,
    @SerializedName("password") val password : String,
    @SerializedName("nombres") val nombres : String,
    @SerializedName("apellidos") val apellidos : String,
    @SerializedName("edad") val edad : Int,
    @SerializedName("puesto") val puesto : String)