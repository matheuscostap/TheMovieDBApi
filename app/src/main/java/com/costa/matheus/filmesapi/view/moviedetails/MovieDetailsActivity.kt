package com.costa.matheus.filmesapi.view.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.decode.DataSource
import coil.request.Request
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.*
import com.costa.matheus.filmesapi.utils.AlertUtils
import com.costa.matheus.filmesapi.utils.convertDate

import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movie: MovieModel
    private var similarMovies = arrayListOf<MovieModel>()
    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var adapter: SimilarListAdapter
    private var alertActive = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportPostponeEnterTransition()

        movie = intent.extras.getSerializable("movie") as MovieModel
        Log.i("Details", "name -> ${movie.name}")
        toolbar.title = movie.name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observeVM()
        viewModel.getGenres()
        viewModel.getSimilar(movie.id)
        //contentDetails.visibility = View.GONE

        setupViews()
    }

    private fun observeVM(){
        viewModel.event.observe(this, Observer<AbstractModel<GenreResponseModel>>{
            if(it.isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
                if(it.isSuccess){
                    it.obj?.let { res ->
                        updateGenres(res.genres)
                        contentDetails.visibility = View.VISIBLE
                    }
                }else{
                    showError()
                }
            }
        })

        viewModel.similarsEvent.observe(this, Observer<AbstractModel<PopularResponseModel>>{
            if(it.isLoading){
                progressSimilars.visibility = View.VISIBLE
            }else{
                progressSimilars.visibility = View.GONE
                if(it.isSuccess){
                    it.obj?.let { res ->
                        similarMovies.addAll(res.results)
                        Log.i("Details", "Similars -> $similarMovies")
                        adapter.notifyDataSetChanged()
                    }
                }else{
                    showError()
                }
            }
        })
    }

    private fun updateGenres(genres: List<GenreModel>){
        genres.forEach { genre ->
            movie.genre_ids.forEach { id ->
                if (genre.id == id){
                    if(tvGenre1.text.isEmpty()){
                        tvGenre1.text = genre.name.capitalize()
                    }else{
                        tvGenre1.text = "${tvGenre1.text}  |  ${genre.name.capitalize()}"
                    }
                }
            }
        }
    }

    private fun setupViews(){
        ivPoster.transitionName = movie.id.toString()

        tvName.text = movie.name
        tvDate.text = movie.first_air_date.convertDate()
        tvVotes.text = movie.vote_average.toString()
        val path = "https://image.tmdb.org/t/p/w200${movie.poster_path}"
        ivPoster.load(path){
            listener(object : Request.Listener{
                override fun onSuccess(data: Any, source: DataSource) {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(data: Any?, throwable: Throwable) {
                    supportStartPostponedEnterTransition()
                }
            })
        }
        tvSynopsis.text = movie.overview

        adapter = SimilarListAdapter(this, similarMovies)
        val llm = LinearLayoutManager(this)
        llm.orientation = RecyclerView.HORIZONTAL

        rvSimilars.layoutManager = llm
        rvSimilars.adapter = adapter
    }

    private fun showError(){
        if(!alertActive){
            alertActive = true
            AlertUtils().showErrorAlert(this){
                viewModel.getGenres()
                viewModel.getSimilar(movie.id)
                alertActive = false
            }
        }
    }

}
