package com.ngapps.googlesigninmvi.core.designsystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.ngapps.googlesigninmvi.core.designsystem.DividerDefaults.DividerAlpha

/**
 * Google Sign In MVI default Divider. Wraps Material 3 [Divider].
 *

 * @param modifier Modifier to be applied to the divider.
 * @param thickness Controls the thickness of the divider through modifier height parameter.
 */
@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
) {
    Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = thickness,
        color = MaterialTheme.colorScheme.primary.copy(
            alpha = DividerAlpha,
        ),
    )
}

/**
 * Shot in time chip default values.
 */
object DividerDefaults {
    const val DividerAlpha = 0.12f
}