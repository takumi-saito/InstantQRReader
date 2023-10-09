package com.kireaji.instantqrreader.ui.widget

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun HandleQRCodeActions(qrText: String) {
    val context = LocalContext.current
    Column {
        Button(onClick = {
            // ブラウザで開く
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(qrText))
            context.startActivity(intent)
        }) {
            Text("ブラウザで開く")
        }

        Button(onClick = {
            // クリップボードにコピー
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("QR Text", qrText)
            clipboard.setPrimaryClip(clip)
        }) {
            Text("コピーする")
        }

        Button(onClick = {
            // 共有する
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, qrText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }) {
            Text("共有する")
        }
    }
}