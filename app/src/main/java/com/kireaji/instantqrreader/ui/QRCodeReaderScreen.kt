package com.kireaji.instantqrreader.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.kireaji.instantqrreader.ui.widget.HandleQRCodeActions
import com.kireaji.instantqrreader.ui.widget.HyperlinkText

@Composable
fun QRCodeReaderScreen() {
    var qrCodeContent by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val qrCodeResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            intentResult?.contents?.let {
                qrCodeContent = it
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            val integrator = IntentIntegrator(context as ComponentActivity)
            integrator.setOrientationLocked(false)
            qrCodeResultLauncher.launch(integrator.createScanIntent())
        } else {
            // アクセス許可が拒否されたケースを処理する
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { launcher.launch(Manifest.permission.CAMERA) },
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "カメラを起動する"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("カメラを起動する")
        }

        qrCodeContent?.let { qrCodeContent->
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    QRCodeView(qrCodeContent)
                    HyperlinkText(
                        text = "読み取り内容：$qrCodeContent",
                        linkText = qrCodeContent,
                        url = qrCodeContent,
                        onLinkClick = { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                    )
                }
            }
            HandleQRCodeActions(qrCodeContent)
        }
    }
}

@Composable
fun QRCodeView(text: String) {
    val bitmap = createQRCode(text)
    bitmap?.let {
        val imageBitmap = it.asImageBitmap()
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}

private fun createQRCode(text: String): Bitmap? {
    val multiFormatWriter = MultiFormatWriter()
    return try {
        val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.createBitmap(bitMatrix)
    } catch (e: Exception) {
        null
    }
}