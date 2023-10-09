package com.kireaji.instantqrreader.ui.widget

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HyperlinkText(
    text: String,
    linkText: String,
    url: String,
    onLinkClick: (String) -> Unit
) {
    val linkStart = text.indexOf(linkText)
    val linkEnd = linkStart + linkText.length

    if (linkStart == -1) {
        // リンクテキストが本文中に存在しない場合は通常のテキストとして表示
        ClickableText(
            text = AnnotatedString(text),
            style = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onClick = { offset ->
                // none
            }
        )
        return
    }

    val annotatedText = AnnotatedString.Builder(text).apply {
        addStyle(
            SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Blue),
            linkStart, linkEnd
        )
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = linkStart,
            end = linkEnd
        )
    }.toAnnotatedString()

    ClickableText(
        text = annotatedText,
        style = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onLinkClick(annotation.item)
                }
        }
    )
}


@Preview
@Composable
fun PreviewHyperlinkText() {
    HyperlinkText(
        text = "利用規約に同意する",
        linkText = "利用規約",
        url = "https://www.google.com/",
        onLinkClick = {
            // none
        }
    )
}