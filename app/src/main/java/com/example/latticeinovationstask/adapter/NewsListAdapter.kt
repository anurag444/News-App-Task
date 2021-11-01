package com.example.latticeinovationstask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.latticeinovationstask.R
import com.example.latticeinovationstask.model.ArticleX
import android.text.format.DateUtils
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.text.Spanned

import android.graphics.Typeface

import android.text.style.StyleSpan

import android.text.SpannableString
import android.text.SpannableStringBuilder
import androidx.core.text.bold


class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private var newsList = ArrayList<ArticleX>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setData(data: ArrayList<ArticleX>){
        this.newsList = data
    }

    class NewsViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val newsTitle = view.findViewById<TextView>(R.id.newsTitle)
        private val newsDesc = view.findViewById<TextView>(R.id.newsDesc)
        private val newsTime = view.findViewById<TextView>(R.id.time_and_source)
        private val newsImage = view.findViewById<ImageView>(R.id.newsImage)

        fun bind(data: ArticleX){
            newsTitle.text = data.title
            newsDesc.text = data.description

            //Parse string to this format: x hours ago Source Name
            var ago = ""
            val myTime: String? = data.publishedAt
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val time: Long = sdf.parse(myTime).time
                val now = System.currentTimeMillis()
                ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
            } catch (e: ParseException) {
                Log.d("Adapter", e.message.toString())
            }
            newsTime.text = SpannableStringBuilder()
                .append(ago)
                .append(" ")
                .bold { append(data.source.name)}

            //Link Image using Glide
            val requestOption = RequestOptions()
                .placeholder(R.drawable.loading).centerCrop()
            Glide.with(newsImage).load(data.urlToImage).apply(requestOption).centerCrop().into(newsImage)
        }

    }

}