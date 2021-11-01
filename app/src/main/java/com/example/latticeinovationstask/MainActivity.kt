package com.example.latticeinovationstask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latticeinovationstask.adapter.NewsListAdapter
import com.example.latticeinovationstask.databinding.ActivityMainBinding
import com.example.latticeinovationstask.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsadapter: NewsListAdapter
    private lateinit var viewModel: NewsViewModel

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initRecyclerView()
        addNewsData()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {

                if(newText.isNotEmpty()) viewModel.searchNews(newText)
                else viewModel.searchNews("bitcoin")
                return false
            }
        })



    }

    private fun initRecyclerView() {
        binding.newsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            newsadapter = NewsListAdapter()
            adapter = newsadapter
        }
    }

    private fun addNewsData() {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getNewsLiveData().observe(this, {
            if (it != null) {
                if (it.articles.size == 0){
                    binding.noPosts.visibility = View.VISIBLE
                    binding.newsList.visibility = View.GONE
                    binding.articleCount.visibility = View.GONE
                }else{
                    binding.noPosts.visibility = View.GONE
                    binding.newsList.visibility = View.VISIBLE
                    binding.articleCount.visibility = View.VISIBLE
                }
                newsadapter.setData(it.articles)
                binding.progressBar.visibility = View.GONE
                val count = "Articles: ${it.articles.size}"
                binding.articleCount.text = count

                newsadapter.notifyDataSetChanged()

            } else {

                Log.e(TAG,"Error")
            }
        })
        viewModel.apiCall()
    }

}