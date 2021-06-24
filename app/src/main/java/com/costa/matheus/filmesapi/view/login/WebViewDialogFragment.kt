package com.costa.matheus.filmesapi.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.utils.Constants
import kotlinx.android.synthetic.main.webview_dialog_layout.*
import kotlinx.android.synthetic.main.webview_dialog_layout.view.*

class WebViewDialogFragment(
    private val onFlowFinish: () -> Unit,
    private val onCancel: () -> Unit): DialogFragment() {

    var requestToken = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.webview_dialog_layout, container, false)

        view.btn_close.setOnClickListener {
            dismiss()
            onCancel()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                request?.let {
                    if(it.url.toString().startsWith(Constants.redirectUrl)) {
                        onFlowFinish()
                        dismiss()
                    }
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.loadUrl("https://www.themoviedb.org/auth/access?request_token=$requestToken")
    }

}