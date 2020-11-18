package com.costa.matheus.filmesapi.view.catalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.view.base.BaseFragment


class CatalogueFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbarTitle("Catalogue \uD83D\uDCD6")
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

}