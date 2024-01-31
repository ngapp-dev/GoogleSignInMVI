package com.ngapps.googlesigninmvi.core.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ngapps.googlesigninmvi.core.designsystem.AppButtonDefaults.ButtonContainerAlpha

/**
 * Google Sign In MVI filled button with generic content slot. Wraps Material 3 [Button].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(65.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = ButtonContainerAlpha,
            ),
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

/**
 * Google Sign In MVI filled button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AppButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        AppButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

/**
 * Internal Google Sign In MVI button content layout for arranging the text label and leading icon.
 *
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Default is `null` for no leading icon.
 * @param trailingIcon The button trailing icon content. Default is `null` for no trailing icon.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 */
@Composable
private fun AppButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    if (!enabled && leadingIcon != null && trailingIcon != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            leadingIcon()
            text()
            trailingIcon()
        }
    } else {
        text()
    }
}

/**
 * Google Sign In MVI button default values.
 */
object AppButtonDefaults {

    const val ButtonContainerAlpha = 0.2f
}
