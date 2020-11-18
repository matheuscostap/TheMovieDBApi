package com.costa.matheus.filmesapi.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.view.base.BaseFragment

class SettingsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbarTitle("Settings \u2699\uFE0F")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}