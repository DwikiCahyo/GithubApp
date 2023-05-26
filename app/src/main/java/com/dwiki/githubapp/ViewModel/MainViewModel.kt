package com.dwiki.githubapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.githubapp.API.ApiConfig
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.Model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel:ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchUser = MutableLiveData<Boolean>()
    val searchUser: LiveData<Boolean> = _searchUser

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _listUser = MutableLiveData<ArrayList<ListUserResponse>>()
    val listUser: LiveData<ArrayList<ListUserResponse>> = _listUser

    companion object{
        private const val TAG = "MainViewModel"
    }

    fun userSearch(query: String){
        _isLoading.value = true
        _searchUser.value = false


        ApiConfig.getApiService()
            .searchUser(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    _searchUser.value = false

                    // call response body
                    val responseBody = response.body()
                    if(response.isSuccessful) {
                        _listUser.value = responseBody?.items
                    } else {
                        _isError.value =true
                        Log.e(TAG, response.message())
                    }
                }
                //Onfailure
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = true
                    Log.e(TAG,"onFailure: ${t.message.toString()}")
                }

            })
    }

}