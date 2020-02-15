package com.trentzo.moengagenews

import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.trentzo.moengagenews.utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_view_article.*

class ViewArticleActivity : AppCompatActivity() {

    private val TAG = "ViewArticleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_article)

        val url = intent.getStringExtra(ConstantUtils.KEY_ARTICLE_URL)

        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "URL not provided", Toast.LENGTH_SHORT).show()
            finish()
        } else setupWebView(url)


        menuClose.setOnClickListener { finish() }
    }
    private fun setupWebView(url: String) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE

//                webView.loadUrl("javascript:(function() { " +
//                        "document.getElementsByClassName('ndfHFb-c4YZDc-GSQQnc-LgbsSe ndfHFb-c4YZDc-to915-LgbsSe VIpgJd-TzA9Ye-eEGnhe ndfHFb-c4YZDc-LgbsSe')[0].style.display='none'; })()")
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Log.i(TAG,error.toString())
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.i(TAG,errorResponse.toString())
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                Log.i(TAG,error.toString())
            }
        }
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }

    override fun onDestroy() {

        webView?.clearCache(true)
        webView?.loadUrl("")

        super.onDestroy()
    }
}
