package com.pojo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.bumptech.glide.Glide

val IMAGE_PATH="https://image.tmdb.org/t/p/w500"
class MovieAdapter(private val result: MutableList<ResultX>, var interActor:setOnclick, var interActor2:setOnclick2):
            RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.movie_item, parent, false)
        UserViewHolder(view).apply {

        }
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val myMovieData : ResultX =result[position]


                holder.title.text = myMovieData.title
                holder.rateStar.text = " "+myMovieData.vote_average.toString()
                holder.index.text=(position+1).toString()
                myMovieData.poster_path?.let {
                    Glide
                        .with(holder.poster.rootView)
                        .load(IMAGE_PATH+myMovieData.poster_path)
                        .placeholder(R.drawable.loading1)
                        .into(holder.poster)
                }
                holder.mainView.setOnClickListener {
                    interActor.onClicks(myMovieData,position)
                }

                holder.clicks.setOnClickListener {

                    interActor2.onClicks2(myMovieData,position)
                }


            }


            /*
            is FooterViewHolder->{
                holder.footerName.text="I am in the FOOTER of the screen"
            }*/

        }

    }

}

interface setOnclick{
    fun onClicks(movie: ResultX, position: Int)
}

interface setOnclick2{
    fun onClicks2(movie: ResultX, position: Int)
}

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var title: TextView = view.findViewById(R.id.movieTitle)
    var rateStar: TextView = view.findViewById(R.id.ratestar)
    var poster: ImageView =view.findViewById(R.id.poster)
    var index: Button=view.findViewById(R.id.index)
    var mainView: TextView=view.findViewById(R.id.mainView)
    var clicks:Button=view.findViewById(R.id.ClickME)

}