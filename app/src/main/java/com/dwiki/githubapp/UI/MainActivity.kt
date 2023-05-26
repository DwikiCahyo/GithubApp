package com.dwiki.githubapp.UI

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.githubapp.Adapter.ListUserAdapter
import com.dwiki.githubapp.DataStore.SettingPreferences
import com.dwiki.githubapp.DataStore.ViewModelFactory
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.Model.SettingViewModel
import com.dwiki.githubapp.R
import com.dwiki.githubapp.ViewModel.MainViewModel
import com.dwiki.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel:MainViewModel by viewModels()
    private lateinit var userAdapter: ListUserAdapter
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showNoSearch(true)

        userAdapter = ListUserAdapter(ArrayList())

        binding.rvUsers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }
        viewModel.searchUser.observe(this){
            showNoSearch(it)
        }
        viewModel.isError.observe(this){
            error -> if (error) {
                showError()
            }
        }
        viewModel.listUser.observe(this){
            if (it != null){
                userAdapter.getItem(it)
                userResult(it)
            }
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val settingModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingModel.getThemeSetting().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }



    private fun userResult(user:ArrayList<ListUserResponse>) {

        userAdapter = ListUserAdapter(user)
        binding.rvUsers.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUserResponse) {
                intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("Data", data)
                startActivity(intent)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        //Create search Manager
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val vSearch = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        vSearch.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.userSearch(query)
                    }

                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        return true
    }

    private fun showLoading(isLoading:Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoSearch(isUser:Boolean){
        binding.imgSearchUser.visibility = if (isUser) View.VISIBLE else View.GONE
    }

    private fun showError() {
        Toast.makeText(this,"Telah Terjadi Error",Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting -> {
                val intent = Intent(applicationContext,SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favorite ->{
                val intent2 = Intent(applicationContext, FavoriteActivity::class.java)
                startActivity(intent2)
                return true
            }
            else -> return false
        }

    }


}