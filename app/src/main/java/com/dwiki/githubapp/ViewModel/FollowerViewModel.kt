package com.dwiki.githubapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.githubapp.API.ApiConfig
import com.dwiki.githubapp.Model.ListUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel:ViewModel(){

    val followerData = MutableLiveData<ArrayList<ListUserResponse>>()

    private val _isLoading  = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    companion object{

        private const val TAG = "FollowerViewModel"

    }

   fun getfollowerData(username:String){
       _isLoading.value = true

       ApiConfig.getApiService()
           .getFoll(username, type = "followers")
           .enqueue(object: Callback<ArrayList<ListUserResponse>>{
               override fun onResponse(
                   call: Call<ArrayList<ListUserResponse>>,
                   response: Response<ArrayList<ListUserResponse>>
               ) {
                   if (response.isSuccessful) {
                       _isLoading.value = false
                        followerData.postValue(response.body())
               }  else {
                       Log.e(TAG, "Tidak ada Data")
                   }
               }

               override fun onFailure(call: Call<ArrayList<ListUserResponse>>, t: Throwable) {
                   _isLoading.value = false
                   Log.e(TAG, "onFailure: ${t.message.toString()}")
               }

           })

   }

    fun getListFollower(): LiveData<ArrayList<ListUserResponse>>{
        return followerData
    }


}