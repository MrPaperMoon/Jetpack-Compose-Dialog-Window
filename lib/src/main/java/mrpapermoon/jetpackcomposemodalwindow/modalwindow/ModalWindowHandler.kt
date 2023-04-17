package mrpapermoon.jetpackcomposemodalwindow.modalwindow

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog
import mrpapermoon.jetpackcomposemodalwindow.ModalHandler

internal class ModalWindowHandler : ModalHandler {

    private var builder: ModalWindowBuilder? = null

    override val stateChangeValueHolder = mutableStateOf(ModalHandler.nullState)

    override fun setContentBuilder(builder: ModalHandler.Builder) {
        if (this.builder != null) throw IllegalStateException("ModalWindowHandler cannot process multiple call, dismiss current dialog before setting new one")
        this.builder = builder as? ModalWindowBuilder
            ?: throw IllegalArgumentException("ModalWindowHandler expect only ModalDialogBuilder")

        stateChangeValueHolder.value = ModalHandler.newState
    }

    @Composable
    override fun Launch(state: ModalHandler.StateValue) {
        val builder = builder ?: return
        val layoutBuilder = builder.layoutBuilder

        when (builder) {
            is ModalAlertWindowBuilder -> {
                AlertDialog(
                    onDismissRequest = {
                        dismiss()
                    },
                    buttons = {
                        builder.Buttons()
                    },
                    title = {
                        builder.Title()
                    },
                    text = {
                        builder.Body()
                    },
                    shape = layoutBuilder.shape,
                    backgroundColor = layoutBuilder.backgroundColor,
                    contentColor = layoutBuilder.contentColor,
                    properties = layoutBuilder.properties,
                )
            }
            is ModalDialogWindowBuilder -> {
                Dialog(
                    onDismissRequest = {
                        dismiss()
                    },
                    content = {
                        builder.Build()
                    }
                )
            }
        }
    }

    override fun dismiss() {
        val layoutBuilder = builder?.layoutBuilder
        builder = null
        layoutBuilder?.onDismissRequest?.invoke()

        stateChangeValueHolder.value = ModalHandler.nullState
    }
}