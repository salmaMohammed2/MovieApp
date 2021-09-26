package com.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.R
import androidx.lifecycle.Observer
import com.pojo.IMAGE_PATH
import com.pojo.mySingletone


class DetailedActivity : AppCompatActivity() {


    lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.movieapp.R.layout.activity_detailed)
        var poster = findViewById<ImageView>(R.id.posterDetailed)
        mySingletone.poster?.let {
            Glide
                .with(poster.rootView)
                .load(IMAGE_PATH + it)
                .placeholder(R.drawable.loading1)
                .into(poster)
        }
        findViewById<TextView>(R.id.movieTitle2).text = mySingletone.title
        findViewById<TextView>(R.id.movieAvg2).text = mySingletone.voteAverage
        findViewById<TextView>(R.id.movieCount).text = mySingletone.voteCount
        var desc = findViewById<TextView>(R.id.overView)
        desc.text = mySingletone.description
        desc.movementMethod = ScrollingMovementMethod()


        movieViewModel = ViewModelProvider(this@DetailedActivity).get(MovieViewModel::class.java)
        movieViewModel.getGeners()
        movieViewModel!!.GenersMutableLiveData?.observe(this@DetailedActivity, Observer { t ->
                var movieGenre=" "
                var index=0
                for(item in t){
                    if(index < mySingletone.genre_ids?.size!!){
                        if(item.id==mySingletone.genre_ids?.get(index)){
                            item.name
                            movieGenre=movieGenre+item.name+" | "
                            index++
                        }
                    }

                }
            movieGenre=movieGenre.removeRange(movieGenre.length-2,movieGenre.length-1)
            findViewById<TextView>(R.id.genres).text = movieGenre


        })



    }
}