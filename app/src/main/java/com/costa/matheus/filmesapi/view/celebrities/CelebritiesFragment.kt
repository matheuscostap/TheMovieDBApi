package com.costa.matheus.filmesapi.view.celebrities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.utils.ImagePath
import com.costa.matheus.filmesapi.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_celebrities.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class CelebritiesFragment : BaseFragment() {

    private val viewModel: CelebritiesViewModel by viewModel()
    private val imagePath = ImagePath(get())
    private lateinit var adapter: CelebritiesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbarTitle("Celebrities \uD83E\uDDB8\u200D")
        setToolbarBackButtonEnabled(false)
        return inflater.inflate(R.layout.fragment_celebrities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        observeStatesFromAdapter()
        getCelebrities()
    }

    private fun setupList() {
        adapter = CelebritiesListAdapter(requireContext(), CelebritiesItemComparator, imagePath)
        val llm = LinearLayoutManager(requireContext())
        rv_celebrities.adapter = adapter
        rv_celebrities.layoutManager = llm
    }

    private fun getCelebrities() {
        lifecycleScope.launch {
            viewModel.getCelebrities().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun observeStatesFromAdapter() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                val loadState = state.refresh
                when(loadState) {
                    is LoadState.Loading -> {
                        celebrities_progress_bar.visibility = View.VISIBLE
                    }

                    is LoadState.NotLoading -> {
                        celebrities_progress_bar.visibility = View.GONE
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