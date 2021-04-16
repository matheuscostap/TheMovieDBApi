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
import kotlinx.android.synthetic.main.webview_dialog_layout.*

class WebViewDialogFragment(
    private val onFlowFinish: (String?) -> Unit): DialogFragment() {

    var requestToken = ""
    private val redirectUrl = "https://filmesapi"
    private val queryParameter = "request_token"


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
                    if(it.url.toString().startsWith(redirectUrl)) {
                        val token = it.url.getQueryParameter(queryParameter)
                        onFlowFinish(token)
                        dismiss()
                    }
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.loadUrl("https://www.themoviedb.org/authenticate/$requestToken?redirect_to=$redirectUrl")
    }

}