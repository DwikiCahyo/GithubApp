package com.dwiki.githubapp.Model

import com.google.gson.annotations.SerializedName

data class ListDetailResponse(

	@field:SerializedName("hireable")
	val hireable: Any,

	@field:SerializedName("bio")
	val bio: Any,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("blog")
	val blog: String,

	@field:SerializedName("public_gists")
	val publicGists: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("gravatar_id")
	val gravatarId: String,

	@field:SerializedName("email")
	val email: Any
)
