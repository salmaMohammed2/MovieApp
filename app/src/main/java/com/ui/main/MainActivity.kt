package com.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.RoomDatabase.AppDatabase
import com.example.movieapp.R
import com.google.android.material.tabs.TabLayout


import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.pojo.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var movieViewModel: MovieViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    var checkPosition=-1;
    lateinit var recycler:RecyclerView
    val searchList : MutableList<ResultX> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics
       // movieViewModel= ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(MovieViewModel::class.java)


/*        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
            param(FirebaseAnalytics.Param.ITEM_NAME, name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }*/
/*

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, MainActivity)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
*/


        movieViewModel= ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.getNowPlayingNow()
        mutableLiveDataMovie(movieViewModel)
        recycler = findViewById<RecyclerView>(R.id.myRec1)
        val guiTabs: TabLayout
        guiTabs = findViewById(R.id.tab_layout)
        guiTabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        checkPosition=tab.position
                        passAdapter(tab.position)
                    }

                    1 -> {
                        checkPosition=tab.position
                        passAdapter(tab.position)
                        firebaseAnalytics.logEvent("roll"){
                            param("movie","${tab.position}")

                        }
                    }

                    2 -> {
                        checkPosition=tab.position
                        passAdapter(tab.position)
     /*                   throw RuntimeException("Test Crash") // Force a crash
                        addContentView(guiTabs,ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT))*/
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        var search = findViewById<EditText>(R.id.search)
        search.addTextChangedListener {
            if(search.text.toString().contains("\n")){
                var titleSearch: String = search.text.toString().replace("\n", "")
                search.setText(titleSearch)
                if(checkPosition == 0){
                    searchInData(checkPosition,titleSearch)
                    search.setText("")

                }

                else if(checkPosition == 1){
                    searchInData(checkPosition,titleSearch)
                    search.setText("")
                }

                else if(checkPosition == 2){
                    searchInData(checkPosition,titleSearch)
                    search.setText("")
                }
            }

        }

    }

    fun passAdapter(myTabe:Int){

        when(myTabe){
            0-> movieViewModel.getNowPlayingNow()
            1->movieViewModel.getTopRated()
            2-> movieViewModel.getFavorites()
        }

        mutableLiveDataMovie(movieViewModel)
        mutableLiveDataFav(movieViewModel)

    }

    fun searchInData(myTabe:Int,titleSearch: String){

        when(myTabe){
            0-> movieViewModel.getNowPlayingNow()
            1->movieViewModel.getTopRated()
            2 -> movieViewModel.getFavorites()
        }

        searchLiveDataMovie(movieViewModel,titleSearch)
        searchLiveDataFav(movieViewModel,titleSearch)

    }

    fun setDatainSingleTone(item:ResultX){
        mySingletone.poster= item.poster_path
        mySingletone.backPoster=item.backdrop_path
        mySingletone.description=item.overview
        mySingletone.title=item.title
        mySingletone.voteAverage=item.vote_average.toString()
        mySingletone.voteCount=item.vote_count.toString()
        mySingletone.genre_ids=item.genre_ids

    }

    fun mutableLiveDataMovie(movieViewModel: MovieViewModel){
        movieViewModel!!.movieMutableLiveData?.observe(this@MainActivity, Observer { t ->
            val adapter = MovieAdapter(t,object:setOnclick{
                override fun onClicks(movie: ResultX, position: Int) {
                    setDatainSingleTone(movie)
                    val i = Intent(this@MainActivity, DetailedActivity::class.java)
                    startActivity(i)
                }
            },object :setOnclick2{
                override fun onClicks2(movie: ResultX, position: Int) {
                    movieViewModel.inserFavorites(movie)
                    Toast.makeText(this@MainActivity, "Added to your" +
                            " favorite list", Toast.LENGTH_SHORT).show()

                }
            })
            recycler.layoutManager = LinearLayoutManager(this@MainActivity)
            recycler.adapter = adapter


        })
    }

    fun mutableLiveDataFav(movieViewModel: MovieViewModel){
        movieViewModel!!.FavoritesMutableLiveData?.observe(this@MainActivity, Observer { t->
            val adapter = MovieAdapter(t,object:setOnclick{
                override fun onClicks(movie: ResultX, position: Int) {
                    setDatainSingleTone(movie)
                    val i = Intent(this@MainActivity, DetailedActivity::class.java)
                    startActivity(i)
                }
            },object :setOnclick2{
                override fun onClicks2(movie: ResultX, position: Int) {
                    Toast.makeText(this@MainActivity, "it's Already in Your " +
                            "Favorite list", Toast.LENGTH_SHORT).show()
                }
            })
            recycler.layoutManager = LinearLayoutManager(this@MainActivity)
            recycler.adapter = adapter
        })
    }

    fun searchLiveDataMovie(movieViewModel: MovieViewModel,titleSearch:String){

        movieViewModel!!.movieMutableLiveData?.observe(this@MainActivity, Observer { t ->
            for(item in t){
                item.title
                t[0].title
                titleSearch
                if(item.title.equals(titleSearch)){ searchList!!.add(item) }
            }
            if(searchList?.size != 0){

                val adapter = MovieAdapter(searchList.distinct() as MutableList<ResultX>,object:setOnclick{
                    override fun onClicks(movie: ResultX, position: Int) {
                        setDatainSingleTone(movie)
                        mySingletone
                        val i = Intent(this@MainActivity, DetailedActivity::class.java)
                        startActivity(i)
                    }
                },object :setOnclick2{
                    override fun onClicks2(movie: ResultX, position: Int) {
                        movieViewModel.inserFavorites(movie)
                    }
                })
                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                recycler.adapter = adapter
                searchList.clear()
            }

        })
    }

    fun searchLiveDataFav(movieViewModel: MovieViewModel,titleSearch:String){
        movieViewModel!!.FavoritesMutableLiveData?.observe(this@MainActivity, Observer { t ->
            for(item in t){
                item.title
                t[0].title
                titleSearch
                if(item.title.equals(titleSearch)){ searchList!!.add(item) }
            }
            if(searchList?.size != 0){

                val adapter = MovieAdapter(searchList.distinct() as MutableList<ResultX>,object:setOnclick{
                    override fun onClicks(movie: ResultX, position: Int) {
                        setDatainSingleTone(movie)
                        mySingletone
                        val i = Intent(this@MainActivity, DetailedActivity::class.java)
                        startActivity(i)
                    }
                },object :setOnclick2{
                    override fun onClicks2(movie: ResultX, position: Int) {
                        Toast.makeText(this@MainActivity, "it's Already in Your " +
                                "Favorite list", Toast.LENGTH_SHORT).show()
                    }
                })
                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                recycler.adapter = adapter
                searchList.clear()


            }

        })
    }


}