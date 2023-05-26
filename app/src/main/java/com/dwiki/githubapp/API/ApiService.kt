package com.dwiki.githubapp.API

import com.dwiki.githubapp.BuildConfig
import com.dwiki.githubapp.Model.ListDetailResponse
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.Model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface ApiService {

  companion object{
      const val API_KEY = BuildConfig.API_KEY
  }

    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username:String
    ): Call<ListDetailResponse>

    @GET("users/{username}/{type}")
    fun getFoll(
        @Path("username") username: String,
        @Path("type") type:String
    ):Call <ArrayList<ListUserResponse>>

}