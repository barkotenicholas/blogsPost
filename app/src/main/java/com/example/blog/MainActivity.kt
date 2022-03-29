package com.example.blog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blog.databinding.ActivityMainBinding
import com.example.blog.models.Post
private const val TAG = "MainActivity"
const val EXTRA_POST_ID = "EXTRA_POST_ID"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var blogPostAdapter: BlogPostAdapter
    private val blogPosts = mutableListOf<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.post.observe(this, Observer { posts ->
            Log.i(TAG, "Number of Post ${posts.size}")
            blogPosts.addAll(posts)
            blogPostAdapter.notifyDataSetChanged()
        })
        viewModel.isLoading.observe(this, Observer { isLoading ->
            Log.i(TAG, "isLoading $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        blogPostAdapter = BlogPostAdapter(this, blogPosts, object : BlogPostAdapter.ItemClickListener{
            override fun onClick(post: Post) {
                val intent = Intent(this@MainActivity,DetailActivity::class.java);
                intent.putExtra(EXTRA_POST_ID,post.id)
                startActivity(intent)
            }
        })
        binding.rvPosts.adapter = blogPostAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)

        binding.button.setOnClickListener {
            viewModel.getPosts()
        }
    }
}