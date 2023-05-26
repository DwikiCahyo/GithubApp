package com.dwiki.githubapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.githubapp.API.ApiConfig
import com.dwiki.githubapp.Model.ListDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel:ViewModel() {

    private val _detailUser = MutableLiveData<ListDetailResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    companion object{
        private  const val TAG = "DetailViewModel"
    }

    fun userDetail(username:String){
        _isLoading.value = true
        ApiConfig.getApiService()
            .detailUser(username)
            .enqueue(object : Callback<ListDetailResponse>{
                override fun onResponse(
                    call: Call<ListDetailResponse>,
                    response: Response<ListDetailResponse>
                ) {
                   _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful){
                        _detailUser.value = responseBody!!
                        _isLoading.value = false
                    } else {
                        Log.e(TAG,response.message())
                    }
                }

                override fun onFailure(call: Call<ListDetailResponse>, t: Throwable) {
                   Log.e(TAG,t.message.toString())
                    _isLoading.value =false
                }

            })

    }

    fun getUsersDetail():LiveData<ListDetailResponse>{
        _isLoading.value = true
        return _detailUser
    }

}