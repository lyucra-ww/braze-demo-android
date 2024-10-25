package com.worldwinner.demo.braze

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.braze.Braze
import com.braze.configuration.BrazeConfig

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var brazeInstance: Braze

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Braze Android SDK directly
        val brazeConfig = BrazeConfig.Builder()
            .setApiKey("ANDROID-API-KEY")
            .setCustomEndpoint("sdk.iad-05.braze.com")
            .build()

        Braze.configure(applicationContext, brazeConfig)
        brazeInstance = Braze.getInstance(applicationContext)

        // Set up the WebView
        webView = WebView(this)
        webView.settings.javaScriptEnabled = true

        val javascriptString = BrazeFileUtils.getAssetFileStringContents(applicationContext.getAssets(), "appboy-html-in-app-message-javascript-component.js")
        webView.loadUrl("javascript:" + javascriptString!!)

        val javascriptInterface = InAppMessageJavascriptInterface(applicationContext, inAppMessage)
        webView.addJavascriptInterface(javascriptInterface, "brazeInternalBridge")

        // Load your web content
        webView.loadUrl("file:///android_asset/index.html")

        // Set the content view to the WebView
        setContentView(webView)
    }

}


