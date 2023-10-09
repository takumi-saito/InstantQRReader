package com.kireaji.instantqrreader.ui.widget

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HandleQRCodeActions(qrText: String) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        if (URLUtil.isValidUrl(qrText)) {
            Button(onClick = {
                // ブラウザで開く
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(qrText))
                context.startActivity(intent)
            }) {
                // アイコンを表示
                Icon(
                    imageVector = Icons.Default.OpenInBrowser,
                    contentDescription = "ブラウザで開く"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("ブラウザで開く")
            }
        }
        Button(onClick = {
            // クリップボードにコピー
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("QR Text", qrText)
            clipboard.setPrimaryClip(clip)
        }) {
            // アイコンを表示
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "コピーする"
            )
            Spacer(modifier = Modifier.width(8.dp))
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
            // アイコンを表示
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "共有する"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("共有する")
        }
    }
}

@Preview
@Composable
fun HandleQRCodeActionsPreview() {
    HandleQRCodeActions(
        qrText = "https://www.google.com/"
    )
}