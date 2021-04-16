package com.costa.matheus.filmesapi.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.base.BaseFragment
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
        setToolbarTitle("Login")
        setToolbarBackButtonEnabled(true)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewModel.getRequestToken()
    }

    private fun openWebView(requestToken: String) {
        val dialog = WebViewDialogFragment(onFlowFinish = {
            Log.i("LoginFragment", "onFlowFinish token: $it")
        })

        dialog.requestToken = requestToken
        dialog.show(parentFragmentManager, "webviewfragment")
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                when(state) {
                    is RequestState.Loading -> {

                    }

                    is RequestState.Success -> {
                        state.data?.let { appTokenModel ->
                            openWebView(appTokenModel.request_token)
                        }
                    }

                    is RequestState.Error -> {
                        if(!state.consumed) {
                            Log.i("Trending", state.throwable.message)
                            state.consumed = true

                            Toast.makeText(
                                requireContext(),
                                "Erro: ${state.throwable.message}",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }
    }

}