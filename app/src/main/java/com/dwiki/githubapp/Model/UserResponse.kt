package com.dwiki.githubapp.Model

import com.google.gson.annotations.SerializedName

 data class UserResponse(
     @field:SerializedName("items")
     val items: ArrayList<ListUserResponse>
)