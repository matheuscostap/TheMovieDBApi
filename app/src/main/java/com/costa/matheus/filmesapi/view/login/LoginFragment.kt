package com.costa.matheus.filmesapi.view.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import coil.api.load
import coil.transform.CircleCropTransformation
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.CreateSessionRequestBody
import com.costa.matheus.filmesapi.model.dto.GetAccessTokenRequestBody
import com.costa.matheus.filmesapi.model.dto.LoginRequestTokenBody
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.utils.Constants
import com.costa.matheus.filmesapi.view.base.BaseFragment
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigationBackCallBack: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@LoginFragment).navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, navigationBackCallBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        setToolbarTitle("Login")
        setToolbarBackButtonEnabled(true)

        view.btn_ok_login.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_logged_in.visibility = View.GONE

        observeGetRequestTokenState()
        observeGetAccessTokenState()
        observeCreateSessionState()
        observeGetAccountState()
        viewModel.getRequestToken(LoginRequestTokenBody(Constants.redirectUrl))
    }

    private fun openWebView(requestToken: String) {
        val dialog = WebViewDialogFragment(onFlowFinish = {
            viewModel.getAccessToken(GetAccessTokenRequestBody(Hawk.get(Constants.requestTokenKey)))
        }, onCancel = {
            showErrorAlert()
        })

        dialog.requestToken = requestToken
        dialog.show(parentFragmentManager, "webviewfragment")
    }

    private fun observeGetRequestTokenState() {
        lifecycleScope.launch {
            viewModel.getRequestTokenState.collect { state ->

                when(state) {
                    is RequestState.Loading -> {

                    }

                    is RequestState.Success -> {
                        state.data?.let { appTokenModel ->
                            viewModel.saveRequestToken(appTokenModel.request_token)
                            openWebView(appTokenModel.request_token)
                        }
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true
                            showErrorAlert()
                        }
                    }
                }

            }
        }
    }

    private fun observeGetAccessTokenState() {
        lifecycleScope.launch {
            viewModel.getAccessTokenState.collect { state ->

                when(state) {
                    is RequestState.Loading -> {

                    }

                    is RequestState.Success -> {
                        state.data?.let { userAccessToken ->
                            viewModel.saveAccessToken(userAccessToken.access_token)
                            viewModel.createSession(CreateSessionRequestBody(userAccessToken.access_token))
                        }
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true
                            showErrorAlert()
                        }
                    }
                }

            }
        }
    }

    private fun observeCreateSessionState() {
        lifecycleScope.launch {
            viewModel.createSessionState.collect { state ->

                when(state) {
                    is RequestState.Loading -> {

                    }

                    is RequestState.Success -> {
                        state.data?.let { sessionModel ->
                            viewModel.saveSessionId(sessionModel.session_id)
                            viewModel.getAccount()
                        }
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true
                            showErrorAlert()
                        }
                    }
                }

            }
        }
    }

    private fun observeGetAccountState() {
        lifecycleScope.launch {
            viewModel.getAccountState.collect { state ->

                when(state) {
                    is RequestState.Loading -> {

                    }

                    is RequestState.Success -> {
                        state.data?.let { accountModel ->
                            layout_logged_in.visibility = View.VISIBLE
                            iv_profile_image_login.load("${Constants.imagePath1280}${accountModel.avatar.tmdb.avatar_path}"){
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }

                            tv_welcome_login.text = "Bem vindo, ${accountModel.username}!"
                        }
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true

                            showErrorAlert()
                        }
                    }
                }

            }
        }
    }

    private fun showErrorAlert(){
        AlertDialog.Builder(requireContext())
            .setTitle("Erro")
            .setMessage("Erro ao vincular conta. Tente novamente.")
            .setNeutralButton("Ok") { dialog, which ->
                dialog.dismiss()
                requireActivity().onBackPressed()
            }
            .setCancelable(false)
            .show()
    }

}