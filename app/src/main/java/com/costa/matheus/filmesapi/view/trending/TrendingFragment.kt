package com.costa.matheus.filmesapi.view.trending

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.MovieModel
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class TrendingFragment : BaseFragment() {

    private val viewModel: TrendingViewModel by viewModel()
    private lateinit var adapter: TrendingListAdapter
    private var movies = arrayListOf<MovieModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trending, container, false)
        setToolbarTitle("Trending \uD83D\uDD25")
        setToolbarBackButtonEnabled(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        observeViewModel()
        viewModel.getTrending()
    }

    private fun setupList() {
        adapter = TrendingListAdapter(requireContext(), movies, onItemClick = {
            goToMovieDetails()
        })
        val llm = LinearLayoutManager(requireContext())
        rv_trending.adapter = adapter
        rv_trending.layoutManager = llm
    }

    private fun goToMovieDetails() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_trending_to_movie_details)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                when(state){
                    is RequestState.Loading -> {
                        trending_progress_bar.visibility = View.VISIBLE
                    }

                    is RequestState.Success -> {
                        trending_progress_bar.visibility = View.GONE
                        Log.i("Trending", state.data.toString())

                        state.data?.let { response ->
                            movies.clear()
                            movies.addAll(response.results)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    is RequestState.Error -> {
                        trending_progress_bar.visibility = View.GONE
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true

                            Toast.makeText(
                                requireContext(),
                                "Erro: ${state.throwable.message}",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

}