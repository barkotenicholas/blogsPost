package com.example.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blog.api.RetrofitInstance
import com.example.blog.models.Post
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel(){

    private val _post: MutableLiveData<List<Post>> = MutableLiveData()
    val post : LiveData<List<Post>>
        get() = _post

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage :LiveData<String?>
        get() = _errorMessage

    private var currentPage = 1
    fun getPosts() {

        viewModelScope.launch {

            _isLoading.value = true
            try {
                val fetchPosts = RetrofitInstance.api.getPost(currentPage)
                currentPage +=1
                Log.i(TAG, "getPosts: $fetchPosts")
                val currentPost = _post.value ?: emptyList()
                _post.value = currentPost + fetchPosts
            }catch (e: Exception){
                _errorMessage.value = e.message
                Log.e(TAG, "getPostDetails: ${e.message}", )
            }finally {
                _isLoading.value = false

            }
        }

    }
}