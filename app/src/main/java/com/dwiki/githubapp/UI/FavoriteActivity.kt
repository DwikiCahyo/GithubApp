package com.dwiki.githubapp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.githubapp.Adapter.ListUserAdapter
import com.dwiki.githubapp.Database.Favorite
import com.dwiki.githubapp.Model.FavoriteViewModel
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.R
import com.dwiki.githubapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: ListUserAdapter
    private val favoriteViewModel:FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userAdapter = ListUserAdapter(ArrayList())
        binding.apply {
            rvFav.adapter = userAdapter
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        userAdapter.setOnItemClickCallback(object :ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListUserResponse) {
                intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra("Data", data)
                startActivity(intent)
            }
        })

        favoriteViewModel.getFavUser()?.observe(this) {
            if (it != null){
                val data = listFavorite(it)
                userAdapter.getItem(data)
            }
        }


    }

    private fun listFavorite(users: List<Favorite>): ArrayList<ListUserResponse> {
        val listUsers = ArrayList<ListUserResponse>()
        for (user in users){
            val usersMap = ListUserResponse(
               user.avatar_url,
                user.id,
                user.login
            )
            listUsers.add(usersMap)
        }
        return listUsers
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}