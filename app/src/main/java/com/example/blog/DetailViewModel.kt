package com.example.blog

import android.util.Log
import androidx.lifecycle.*
import com.example.blog.api.RetrofitInstance
import com.example.blog.models.Post
import com.example.blog.models.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DetailViewModel"

class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage :LiveData<String?>
        get() = _errorMessage

    fun getPostDetails(postId: Int) {
        val api = RetrofitInstance.api
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            val fetchPost = api.getPost(postId)
            val fetchUser = api.getUser(fetchPost.userId)
            Log.i(TAG, "getPostDetails : $fetchPost")
            _post.value = fetchPost
            _user.value = fetchUser
            _isLoading.value = false

            fetchDataCallbackStyle(postId)
        }
    }

    private fun fetchDataCallbackStyle(postId:Int) {
        val api = RetrofitInstance.api

        _isLoading.value = true
        api.getPostViaCallBack(postId).enqueue(object : Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {

                if(response.isSuccessful){
                    val fetchPost = response.body()!!
                    api.getUserViaCallBack(fetchPost.userId).enqueue(object :Callback<User>{
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if(response.isSuccessful){
                                val fetchUser = response.body()!!
                                _post.value = fetchPost
                                _user.value = fetchUser

                                _isLoading.value = false
                                Log.i(TAG, "onResponse: user $fetchUser")
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })

                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}