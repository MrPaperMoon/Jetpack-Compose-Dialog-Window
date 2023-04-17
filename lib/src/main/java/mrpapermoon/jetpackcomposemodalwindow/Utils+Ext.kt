package mrpapermoon.jetpackcomposemodalwindow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ModalHandler.Builder.TextButton(
    textId: Int,
    modifier: Modifier = Modifier,
    isAlert: Boolean = false,
    isDefault: Boolean = false,
    onClick: () -> Unit,
) {
    val color = if (isAlert) MaterialTheme.colors.error else MaterialTheme.colors.primary
    val style = if (isDefault) MaterialTheme.typography.button else MaterialTheme.typography.body2

    Text(
        modifier = modifier
            .height(32.dp)
            .padding(4.dp)
            .clickable(onClick = onClick),
        text = stringResource(id = textId),
        textAlign = TextAlign.Center,
        color = color,
        style = style,
    )
}