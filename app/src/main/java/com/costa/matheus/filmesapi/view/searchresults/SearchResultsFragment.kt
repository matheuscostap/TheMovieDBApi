package com.costa.matheus.filmesapi.view.searchresults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_celebrities.*
import kotlinx.android.synthetic.main.fragment_search_results.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchResultsFragment : BaseFragment() {

    private val viewModel: SearchResultsViewModel by viewModel()
    private lateinit var adapter: SearchResultsListAdapter
    private var genreId = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigationBackCallBack: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@SearchResultsFragment).navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, navigationBackCallBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_results, container, false)

        genreId = arguments?.getLong("genreId") ?: 0L
        val genreName = arguments?.getString("genreName") ?: "Filmes"

        setToolbarTitle(genreName)
        setToolbarBackButtonEnabled(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        observeStatesFromAdapter()
        searchByGenre()
    }

    private fun searchByGenre() {
        lifecycleScope.launch {
            viewModel.searchByGenre(genreId = genreId).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        adapter = SearchResultsListAdapter(requireContext(), SearchResultItemComparator)
        val glm = GridLayoutManager(requireContext(), 2)
        rv_search_results.adapter = adapter
        rv_search_results.layoutManager = glm
    }

    private fun observeStatesFromAdapter() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                val loadState = state.refresh
                when(loadState) {
                    is LoadState.Loading -> {
                        search_result_progress_bar.visibility = View.VISIBLE
                    }

                    is LoadState.NotLoading -> {
                        search_result_progress_bar.visibility = View.GONE
                    }

                    is LoadState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Erro: ${loadState.error.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}