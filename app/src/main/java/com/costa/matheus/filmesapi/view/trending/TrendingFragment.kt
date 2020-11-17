package com.costa.matheus.filmesapi.view.trending

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.repository.state.RequestState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment() {

    private val viewModel: TrendingViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        observeViewModel()
        viewModel.getTrending()
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                when(state){
                    is RequestState.Loading -> {
                        Log.i("Trending", "Loading")
                    }

                    is RequestState.Success -> {
                        Log.i("Trending", state.data.toString())
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true
                        }
                    }
                }
            }
        }
    }

}