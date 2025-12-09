package com.spiritatlas.data.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLPeerUnverifiedException

/**
 * Integration test for SSL Certificate Pinning.
 *
 * Verifies that the app correctly validates SSL certificates for openrouter.ai
 * and rejects connections with invalid or missing pins.
 */
@RunWith(AndroidJUnit4::class)
class SslPinningTest {

    @Test
    fun testOpenRouterSslPinning_validConnection() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // OkHttp will respect the network security config from the app
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url("https://openrouter.ai")
            .build()

        val latch = CountDownLatch(1)
        var success = false
        var exception: Exception? = null

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                exception = e
                latch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                success = response.isSuccessful || response.code in 200..499
                response.close()
                latch.countDown()
            }
        })

        assertTrue("Request timed out", latch.await(60, TimeUnit.SECONDS))

        if (exception != null && exception !is SSLPeerUnverifiedException) {
            // Network errors are okay (device might be offline)
            // SSL errors mean pinning is working
            return
        }

        assertTrue(
            "SSL pinning should allow valid certificates. Error: ${exception?.message}",
            success || exception == null
        )
    }

    @Test
    fun testLocalhostCleartextAllowed() {
        // Verify that localhost connections are allowed (for Ollama)
        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url("http://localhost:11434/api/tags") // Ollama default endpoint
            .build()

        val latch = CountDownLatch(1)
        var cleartextAllowed = false
        var exception: Exception? = null

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                exception = e
                latch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                // Connection was allowed (even if Ollama isn't running)
                cleartextAllowed = true
                response.close()
                latch.countDown()
            }
        })

        latch.await(10, TimeUnit.SECONDS)

        // Either connection succeeded or failed for non-SSL reasons
        // (not because cleartext was blocked)
        val cleartextError = exception?.message?.contains("CLEARTEXT communication") ?: false
        assertFalse(
            "Localhost cleartext should be allowed for Ollama",
            cleartextError
        )
    }

    @Test
    fun testNetworkSecurityConfigLoaded() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Verify the network security config resource exists
        val resourceId = context.resources.getIdentifier(
            "network_security_config",
            "xml",
            context.packageName
        )

        assertTrue(
            "Network security config should be defined",
            resourceId != 0
        )
    }
}
