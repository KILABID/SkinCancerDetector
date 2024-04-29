package com.dicoding.asclepius.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.local.entity.DataEntity
import com.dicoding.asclepius.local.repository.DataRepository
import com.dicoding.asclepius.remote.api.ApiConfig
import com.dicoding.asclepius.remote.response.ArticlesItem
import com.dicoding.asclepius.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.Callback

class ResultViewModel(application: Application) : AndroidViewModel(application) {
    private var _listItem = MutableLiveData<List<ArticlesItem?>?>()
    var listItem: LiveData<List<ArticlesItem?>?> = _listItem

    private val repository: DataRepository = DataRepository(application)


    fun getHealthNews() {
        val client = ApiConfig.getApiService().getTopHeadlines()
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: retrofit2.Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val filteredArticles =
                            responseBody.articles?.filter { !it?.title.equals("[Removed]") }
                        _listItem.value = filteredArticles
                        Log.d("ResultActivity", "onResponse: $filteredArticles")
                    } else {
                        Log.e("ResultActivity", "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("ResultActivity", "onFailure: ${t.message}")
            }
        })
    }

    fun addData(data: DataEntity){
        repository.addData(data)
    }
}