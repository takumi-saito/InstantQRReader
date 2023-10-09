package com.kireaji.instantqrreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.kireaji.instantqrreader.ui.theme.InstantQRReaderTheme
import com.kireaji.instantqrreader.ui.widget.HandleQRCodeActions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var qrText by remember { mutableStateOf<String?>(null) }

            InstantQRReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (qrText == null) {
                        QRCodeScanner {
                            // QRコードを読み取った時の処理
                            // ここで it を使ってQRコードを生成・表示します
                            qrText = it
                        }
                    } else {
                        HandleQRCodeActions(qrText!!)
                    }
                }
            }
        }
    }
}

@Composable
fun QRCodeScanner(onScanned: (String) -> Unit) {
    val context = LocalContext.current
    AndroidView(
        factory = { ctx ->
            BarcodeView(ctx).apply {
                // QRコード読み取りのセットアップ
                decoderFactory = DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE))
                this.decodeSingle {
                    val resultText = it.text
                    onScanned(resultText)
                }
            }
        },
        update = {}
    )
}
