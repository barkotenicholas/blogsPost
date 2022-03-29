package com.example.blog

import android.util.Log
import androidx.lifecycle.*
import com.example.blog.api.RetrofitInstance
import com.example.blog.models.Post
import com.example.blog.models.User
import kotlinx.coroutines.launch

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
    fun getPostDetails(postId: Int) {
        val api = RetrofitInstance.api
        viewModelScope.launch {
            _isLoading.value = true
            val fetchPost = api.getPost(postId)
            val fetchUser = api.getUser(fetchPost.userId)

            Log.i(TAG, "getPostDetails : ${fetchPost}")

            _post.value = fetchPost
            _user.value = fetchUser
            _isLoading.value = false


        }
    }
}