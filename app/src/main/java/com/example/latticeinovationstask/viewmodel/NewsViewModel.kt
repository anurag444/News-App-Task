package com.example.latticeinovationstask.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latticeinovationstask.model.Article
import com.example.latticeinovationstask.remote.RetroInstance
import com.example.latticeinovationstask.remote.RetroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private var newsListData: MutableLiveData<Article> = MutableLiveData()

    private val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)

    private val TAG = "NewsViewModel"

    fun getNewsLiveData(): MutableLiveData<Article> {
        return newsListData
    }

    fun apiCall() {

        val call = retroInstance.dataFromAPI()
        call.enqueue(object : Callback<Article> {
            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                if (response.isSuccessful) {
                    newsListData.postValue(response.body())
                } else {
                    newsListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                newsListData.postValue(null)
            }
        })
    }

    fun searchNews(searchQuery:String){
        val call = retroInstance.searchForNews(searchQuery)

        call.enqueue(object : Callback<Article> {
            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                if(response.isSuccessful){
                    newsListData.postValue(response.body())

                }else{
                    newsListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                newsListData.postValue(null)
            }
        })
    }


}