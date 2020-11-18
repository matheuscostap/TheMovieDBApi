package com.costa.matheus.filmesapi.view.celebrities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.view.base.BaseFragment

class CelebritiesFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbarTitle("Celebrities \uD83E\uDDB8\u200D")
        return inflater.inflate(R.layout.fragment_celebrities, container, false)
    }

}