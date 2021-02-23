package com.costa.matheus.filmesapi.view.catalogue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.GenreModel
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_catalogue.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class CatalogueFragment : BaseFragment() {

    private val viewModel: CatalogueViewModel by viewModel()
    private var genres = arrayListOf<GenreModel>()
    private lateinit var adapter: CatalogueListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbarTitle("Catalogue \uD83D\uDCD6")
        setToolbarBackButtonEnabled(false)
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        observeViewModel()
        viewModel.getCatalogue()
    }

    private fun setupList() {
        adapter = CatalogueListAdapter(requireContext(), genres)
        val glm = GridLayoutManager(requireContext(), 2)
        rv_catalogue.adapter = adapter
        rv_catalogue.layoutManager = glm
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                when(state) {
                    is RequestState.Loading -> {
                        catalogue_progress_bar.visibility = View.VISIBLE
                    }

                    is RequestState.Success -> {
                        catalogue_progress_bar.visibility = View.GONE

                        state.data?.let { result ->
                            genres.addAll(result.genres)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    is RequestState.Error -> {
                        catalogue_progress_bar.visibility = View.GONE
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