package com.costa.matheus.filmesapi.view.main

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.costa.matheus.filmesapi.R
import kotlinx.android.synthetic.main.content_navigation.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
                                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }
}