package com.worldwinner.demo.braze

import android.os.Bundle
import android.webkit.JavascriptInterface
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

        // Add JavaScript interface for communication with the WebView
        webView.addJavascriptInterface(BrazeBridge(), "BrazeBridge")

        // Load your web content
        webView.loadUrl("file:///android_asset/index.html")

        // Set the content view to the WebView
        setContentView(webView)
    }

    inner class BrazeBridge {

        // Expose deviceId to JavaScript
        @JavascriptInterface
        fun getDeviceId(): String? {
            return brazeInstance.deviceId
        }

        // Receive userId from JavaScript and change user in Braze
        @JavascriptInterface
        fun setUserId(userId: String) {
            runOnUiThread {
                brazeInstance.changeUser(userId)
            }
        }
    }
}


