package com.costa.matheus.filmesapi.view.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import coil.api.load
import coil.transform.CircleCropTransformation
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.AccountModel
import com.costa.matheus.filmesapi.model.dto.GenreModel
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.utils.Constants
import com.costa.matheus.filmesapi.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        setToolbarTitle("Settings \u2699\uFE0F")
        setToolbarBackButtonEnabled(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            goToLogin()
        }

        observeGetAccountState()
        viewModel.getAccount()
    }

    private fun showAccountSyncView(account: AccountModel) {
        tv_username_settings.visibility = View.VISIBLE
        tv_username_settings.text = account.username
        iv_profile_image_settings.load("${Constants.imagePath1280}${account.avatar.tmdb.avatar_path}"){
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        btn_logout.visibility = View.VISIBLE
        btn_login.visibility = View.GONE
    }

    private fun showRegularView() {
        tv_username_settings.visibility = View.GONE
        btn_logout.visibility = View.GONE
        btn_login.visibility = View.VISIBLE
    }

    private fun goToLogin() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_settings_to_login)
    }

    private fun observeGetAccountState() {
        lifecycleScope.launch {
            viewModel.getAccountState.collect { state ->
                when(state) {
                    is RequestState.Loading -> {}

                    is RequestState.Success -> {
                        state.data?.let { accountModel ->
                            showAccountSyncView(accountModel)
                        }
                    }

                    is RequestState.Error -> {
                        showRegularView()
                    }
                }
            }
        }
    }

}