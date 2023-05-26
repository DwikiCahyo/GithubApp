package com.dwiki.githubapp.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dwiki.githubapp.Database.DatabaseUser
import com.dwiki.githubapp.Database.Favorite
import com.dwiki.githubapp.Database.FavoriteDAO

class FavoriteViewModel(application: Application):AndroidViewModel(application) {
    private var favDAO:FavoriteDAO?
    private var favDB:DatabaseUser?

    private val _searchFavorite = MutableLiveData<Boolean>()
    val searchFavorite: LiveData<Boolean> = _searchFavorite

    init {
        favDB = DatabaseUser.getDatabase(application)
        favDAO =favDB?.favoriteDao()
    }

    fun getFavUser(): LiveData<List<Favorite>>? {
        return favDAO?.getFavorite()
    }




}