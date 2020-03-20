package com.costa.matheus.filmesapi.view.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.AbstractModel
import com.costa.matheus.filmesapi.model.MovieModel
import com.costa.matheus.filmesapi.model.PopularResponseModel
import com.costa.matheus.filmesapi.repository.MovieRepository
import com.costa.matheus.filmesapi.utils.AlertUtils
import com.costa.matheus.filmesapi.view.moviedetails.MovieDetailsActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListActivity : AppCompatActivity() {

    private val viewModel: MovieListViewModel by viewModel()
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        progressBar.visibility = View.GONE
        contentList.visibility = View.GONE
        observeVM()

        adapter = MovieListAdapter(this){
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movie", it)
            startActivity(intent)
        }

        val llm = LinearLayoutManager(this)
        llm.orientation = RecyclerView.VERTICAL

        rvMovies.layoutManager = llm
        rvMovies.adapter = adapter

        viewModel.moviesList.observe(this, Observer<PagedList<MovieModel>>{
            adapter.submitList(it)
        })
    }

    private fun observeVM(){
        viewModel.event?.observe(this, Observer<AbstractModel<PopularResponseModel>>{
            if (it.isLoading){
                //loading
                progressBar.visibility = View.VISIBLE
            }else{
                if (it.isSuccess){
                    progressBar.visibility = View.GONE
                    contentList.visibility = View.VISIBLE
                }else{
                    AlertUtils().showErrorAlert(this){
                        viewModel.refresh()
                    }
                }
            }
        })
    }
}
