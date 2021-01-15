package com.costa.matheus.filmesapi.view.moviedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import coil.api.load
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.MovieDetailModel
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.utils.Constants
import com.costa.matheus.filmesapi.view.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment() {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var movieDetailModel: MovieDetailModel? = null
    private var movieId = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigationBackCallBack: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@MovieDetailsFragment).navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, navigationBackCallBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        movieId = arguments?.getLong("movieId") ?: 0L
        val name = arguments?.getString("movieName")

        setToolbarTitle(name ?: "Detalhes")
        setToolbarBackButtonEnabled(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(movie_details_player)
        observeViewModel()
        viewModel.getMovieDetail(movieId)
    }


    private val playerListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            movieDetailModel?.let {
                if(viewModel.canShowVideo(it.videos)) {
                    showVideo(youTubePlayer, it.videos.results[0].key)
                }else {
                    showBackdropPoster(it.backdrop_path)
                }
            }
        }
    }

    private fun showVideo(youTubePlayer: YouTubePlayer, video: String) {
        iv_movie_backdrop_no_video.visibility = View.GONE
        movie_details_player.visibility = View.VISIBLE
        youTubePlayer.loadVideo(video, 0f)
    }

    private fun showBackdropPoster(path: String) {
        iv_movie_backdrop_no_video.visibility = View.VISIBLE
        movie_details_player.visibility = View.GONE
        iv_movie_backdrop_no_video.load("${Constants.imagePath1280}${path}")
    }

    private fun setupView() {
        movieDetailModel?.let {
            tv_movie_genres.visibility = View.VISIBLE
            var genres = ""
            it.genres.forEach { genre -> genres = "$genres ${genre.name} |" }
            tv_movie_genres.text = genres

            tv_movie_year.visibility = View.VISIBLE
            tv_movie_year.text = it.release_date.split("-")[0]

            tv_movie_rating.visibility = View.VISIBLE
            tv_movie_rating.text = it.vote_average.toString()

            tv_movie_runtime.visibility = View.VISIBLE
            tv_movie_runtime.text = "${it.runtime}\nmin"

            tv_movie_tagline.visibility = View.VISIBLE
            tv_movie_tagline.text = it.tagline

            tv_movie_overview.visibility = View.VISIBLE
            tv_movie_overview.text = it.overview

            iv_movie_details_poster.visibility = View.VISIBLE
            iv_movie_details_poster.load("${Constants.imagePath1280}${it.poster_path}") {
                crossfade(true)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                when(state) {
                    is RequestState.Loading -> {

                    }

                    is RequestState.Success -> {
                        movie_details_player.addYouTubePlayerListener(playerListener)

                        state.data.let {
                            movieDetailModel = it
                            setupView()
                        }
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true

                            Toast.makeText(
                                requireContext(),
                                "Erro: ${state.throwable.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}