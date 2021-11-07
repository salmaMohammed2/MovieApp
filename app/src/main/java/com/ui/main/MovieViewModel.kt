package com.ui.main
import androidx.lifecycle.*
import com.repositories.MovieRepository
import com.pojo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    var movieMutableLiveData = MutableLiveData<MutableList<ResultX>>()
    var GenersMutableLiveData = MutableLiveData<MutableList<Genre>>()
    var FavoritesMutableLiveData = MutableLiveData<MutableList<ResultX>>()
    //var movieDatabase: AppDatabase = AppDatabase.getInstance(this.getApplication())
    //var getFavoritesMovies= MutableLiveData<MutableList<ResultX>>()


    fun getNowPlayingNow(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val movieResponse: Response<NowPlayingResponse> = repository.getNowPlayingNow()
                if(movieResponse.isSuccessful){
                    withContext(Dispatchers.Main){
                        movieMutableLiveData.value= movieResponse.body()?.results as MutableList<ResultX>
                    }

                }
            }
        }

/*        val movieCall: Call<NowPlayingResponse> = JSONRetriever().getNowPlayingNow()
        movieCall.enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(call: Call<NowPlayingResponse>, response: Response<NowPlayingResponse>) {
                movieMutableLiveData.value= response.body()?.results as MutableList<ResultX>
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {

            }
        })*/
    }

    fun getTopRated(){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val movieResponse: Response<TopRatedResponse> = repository.getTopRated()
                if(movieResponse.isSuccessful){
                    withContext(Dispatchers.Main){
                        movieMutableLiveData.value= movieResponse.body()?.results as MutableList<ResultX>
                    }

                }
            }
        }

/*        val movieCall: Call<TopRatedResponse> = JSONRetriever().getTopRated()
        movieCall.enqueue(object : Callback<TopRatedResponse> {
            override fun onResponse(call: Call<TopRatedResponse>, response: Response<TopRatedResponse>) {
                movieMutableLiveData.value = response.body()?.results as MutableList<ResultX>
            }

            override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {

            }
        })*/

    }


    fun getGeners(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val movieResponse: Response<GenersResponse> = repository.getGener()
                if(movieResponse.isSuccessful){
                    withContext(Dispatchers.Main){
                        movieMutableLiveData.value= movieResponse.body()?.genres as MutableList<ResultX>
                    }

                }
            }
        }



/*        val movieCall: Call<Geners> = JSONRetriever().getGener()
        movieCall.enqueue(object : Callback<Geners> {
            override fun onResponse(call: Call<Geners>, response: Response<Geners>) {
                GenersMutableLiveData.value= response.body()?.genres as MutableList<Genre>
            }

            override fun onFailure(call: Call<Geners>, t: Throwable) {

            }
        })*/
    }

    fun getFavorites(){
        viewModelScope.launch {
            //FavoritesMutableLiveData.postValue(movieDatabase.getMovieDao().getAllMovies() as MutableList<ResultX>)
            FavoritesMutableLiveData.postValue(repository.getAllMovies() as MutableList<ResultX>?)
        }

    }

    fun inserFavorites(movie: ResultX){
        viewModelScope.launch {
            repository.insertMovie(movie)
        }

    }


}

