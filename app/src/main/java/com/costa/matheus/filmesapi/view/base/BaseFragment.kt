package com.costa.matheus.filmesapi.view.base

import androidx.fragment.app.Fragment
import com.costa.matheus.filmesapi.view.main.NavigationActivity

open class BaseFragment : Fragment() {

    fun setToolbarTitle(title: String) {
        val activity = requireActivity() as NavigationActivity
        activity.supportActionBar?.title = title.toUpperCase()
    }

    fun setToolbarBackButtonEnabled(enabled: Boolean) {
        val activity = requireActivity() as NavigationActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
    }

}