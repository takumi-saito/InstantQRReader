package com.kireaji.instantqrreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kireaji.instantqrreader.ui.theme.InstantQRReaderTheme
import com.kireaji.instantqrreader.ui.QRCodeReaderScreen
import android.content.Intent
import android.net.Uri
import android.widget.TextView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data
        val debugText = appLinkData?.let {
            // URIからクエリパラメータを取得
            val serial = it.getQueryParameter("serial")
            val param2 = it.getQueryParameter("param2")

            // 値を使用するロジック（例: TextViewに表示する）
            """
                serial: $serial, param2: $param2,
                it.toString(): $it,
            """.trimIndent()
        }
        setContent {
            InstantQRReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QRCodeReaderScreen(
                        debugText
                    )
                }
            }
        }
    }
}

