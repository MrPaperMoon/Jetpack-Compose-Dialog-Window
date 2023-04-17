package mrpapermoon.jetpackcomposemodalwindow.modalwindow

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.window.DialogProperties
import mrpapermoon.jetpackcomposemodalwindow.ModalHandler

sealed interface ModalWindowBuilder : ModalHandler.Builder {
    override val layoutBuilder: LayoutBuilder
        get() = LayoutBuilder()

    open class LayoutBuilder : ModalHandler.LayoutBuilder {
        open val onDismissRequest: () -> Unit
            get() = {}

        open val properties: DialogProperties = DialogProperties()

        open val shape: Shape
            @Composable get() = MaterialTheme.shapes.medium

        open val backgroundColor: Color
            @Composable get() = MaterialTheme.colors.surface

        open val contentColor: Color
            @Composable get() = contentColorFor(backgroundColor)
    }

    open class AlertLayoutBuilder : LayoutBuilder() {
        open val modifier: Modifier = Modifier.fillMaxWidth()
    }
}

interface ModalDialogWindowBuilder : ModalWindowBuilder {
    @Composable
    fun Build()
}

interface ModalAlertWindowBuilder : ModalWindowBuilder {
    @Composable
    fun Buttons()

    @Composable
    fun Title()

    @Composable
    fun Body()
}