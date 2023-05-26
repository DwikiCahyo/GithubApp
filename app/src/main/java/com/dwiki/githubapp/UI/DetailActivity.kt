package com.dwiki.githubapp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dwiki.githubapp.Adapter.SectionPagerAdapter
import com.dwiki.githubapp.Model.DetailFavoriteViewModel
import com.dwiki.githubapp.Model.ListDetailResponse
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.R
import com.dwiki.githubapp.ViewModel.DetailViewModel
import com.dwiki.githubapp.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val detailFavoriteViewModel:DetailFavoriteViewModel by viewModels()
    private var checkFavorite = false
    private val bundle = Bundle()

    companion object{
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data  = intent.getParcelableExtra<ListUserResponse>("Data")
        val username = data?.login
        val id = data?.id
        val avatar =data?.avatarUrl
        bundle.putString(USERNAME,username)

        setTabLayout()

        viewModel.userDetail(username.toString())
        viewModel.getUsersDetail().observe(this){
            if (it != null) getDetailUser(it)
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        //Check like or non
        checkButtonFav(id)
        //Clickable button like
        setClickFavorite(id, username, avatar)
    }



    private fun setClickFavorite(id: Int?, username: String?, avatar: String?) {
        binding.toggleFav.setOnClickListener {
            checkFavorite = !checkFavorite
            if (checkFavorite) {
                if (id != null) {
                    detailFavoriteViewModel.addFavorite(username.toString(), id, avatar.toString())
                    val intent = Intent(applicationContext, FavoriteActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        this,
                        "$username Berhasil Ditambahkan ke Favorit",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (id != null) {
                    detailFavoriteViewModel.removeFavorite(id)
                    Toast.makeText(
                        this, "$username Berhasil Dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.toggleFav.isChecked = checkFavorite
        }
    }

    private fun checkButtonFav(id: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            val check = detailFavoriteViewModel.checkUser(id!!)
            withContext(Dispatchers.Main) {
                if (check != null) {
                    if (check > 0) {
                        binding.toggleFav.isChecked = true
                        checkFavorite = true

                    } else {
                        binding.toggleFav.isChecked = false
                        checkFavorite = false
                    }
                }
            }
        }
    }


    private fun setTabLayout() {
        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabFollow.setupWithViewPager(viewPager)
        }
    }


    private fun getDetailUser(it: ListDetailResponse) {
        Glide.with(this)
            .load(it.avatarUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(binding.imgUser)

        binding.apply {
            tvName.text = it.name
            if (it.name == null) tvName.visibility = View.GONE
            numRepo.text = it.publicRepos.toString()
            numFollower.text = it.followers.toString()
            numFollowing.text = it.following.toString()
            company.text = getString(R.string.company_string, it.company)
            if (it.company == null) company.visibility = View.GONE
            tvLocation.text = getString(R.string.location_string, it.location)
            if (it.location == null) tvLocation.visibility = View.GONE

        }
        supportActionBar?.title = it.login

    }

    private fun showLoading(isLoading:Boolean) {
        if (isLoading) {
            binding.apply {
                detailActivity.visibility = View.GONE
                tabFollow.visibility = View.GONE
                viewPager.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                detailActivity.visibility = View.VISIBLE
                tabFollow.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting -> {
                val intent = Intent(applicationContext,SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return false
        }

    }

}