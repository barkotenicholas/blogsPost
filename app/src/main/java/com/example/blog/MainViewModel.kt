package com.example.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blog.api.RetrofitInstance
import com.example.blog.models.Post
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel(){

    private val _post: MutableLiveData<List<Post>> = MutableLiveData()
    val post : LiveData<List<Post>>
        get() = _post

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getPosts() {

        viewModelScope.launch {

            _isLoading.value = true
            val fetchPosts = RetrofitInstance.api.getPost()
            Log.i(TAG, "getPosts: $fetchPosts")
            _post.value =fetchPosts
            _isLoading.value = false
        }

    }
}