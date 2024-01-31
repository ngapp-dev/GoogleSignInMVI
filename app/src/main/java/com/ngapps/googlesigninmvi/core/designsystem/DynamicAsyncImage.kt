package com.ngapps.googlesigninmvi.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.ngapps.googlesigninmvi.core.ui.theme.LocalTintTheme

/**
 * A wrapper around [AsyncImage] which determines the colorFilter based on the theme
 */
@Composable
fun DynamicAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Painter? = null,
) {
    val iconTint = LocalTintTheme.current.iconTint
    AsyncImage(
        placeholder = placeholder,
        model = imageUrl,
        contentDescription = contentDescription,
        colorFilter = if (iconTint != null) ColorFilter.tint(iconTint) else null,
        contentScale = contentScale,
        modifier = modifier,
    )
}
