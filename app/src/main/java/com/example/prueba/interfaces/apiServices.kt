package com.example.prueba.interfaces

import com.example.prueba.models.trabajadorResponse
import com.example.prueba.models.user
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface apiServices {
        //lista de pokemones
        @Headers("Content-Type: application/json")
        @POST("api/trabajadors/login")
        fun obtenerUsuario(@Body user: user):retrofit2.Call<trabajadorResponse>?
        @GET("api/trabajadors/guille")
        fun obtener():retrofit2.Call<trabajadorResponse>?


    companion object {
            const val URL_BASE = "http://userandroid.somee.com/"


        }
    }

