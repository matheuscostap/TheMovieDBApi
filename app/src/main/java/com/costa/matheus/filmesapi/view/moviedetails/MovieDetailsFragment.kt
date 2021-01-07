package com.costa.matheus.filmesapi.view.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.view.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : BaseFragment() {


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

        val id = arguments?.getInt("movieId")
        val name = arguments?.getString("movieName")

        setToolbarTitle(name ?: "Detalhes")
        setToolbarBackButtonEnabled(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(youtubePlayer)
        youtubePlayer.addYouTubePlayerListener(playerListener)
    }

    private val playerListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            val video = "1AaBnOLALFY"
            youTubePlayer.loadVideo(video, 0f)
        }
    }
}