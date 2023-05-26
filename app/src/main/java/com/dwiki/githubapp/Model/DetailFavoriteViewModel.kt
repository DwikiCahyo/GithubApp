package com.dwiki.githubapp.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dwiki.githubapp.Database.DatabaseUser
import com.dwiki.githubapp.Database.Favorite
import com.dwiki.githubapp.Database.FavoriteDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFavoriteViewModel(application: Application):AndroidViewModel(application) {

    private var favoriteDao:FavoriteDAO?
    private var dbFavorite:DatabaseUser?

    init {
        dbFavorite = DatabaseUser.getDatabase(application)
        favoriteDao = dbFavorite?.favoriteDao()
    }

    fun addFavorite(username:String, id:Int, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(
                username,
                id,
                avatarUrl
            )
            favoriteDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favoriteDao?.checkFavorite(id)

    fun removeFavorite(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.removeFav(id)
        }
    }

}