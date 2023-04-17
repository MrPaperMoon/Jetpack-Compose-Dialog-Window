package mrpapermoon.jetpackcomposemodalwindow.bottomsheet

import androidx.compose.material.*
import androidx.compose.runtime.*
import mrpapermoon.jetpackcomposemodalwindow.ModalHandler

@OptIn(ExperimentalMaterialApi::class)
internal class BottomSheetHandler : ModalHandler {

    private var show = true
    private var suspendDismiss = false
    private var builder: BottomSheetBuilder? = null
    private var _bottomSheetState: ModalBottomSheetState? = null

    private val bottomSheetState: ModalBottomSheetState
        get() = _bottomSheetState!!

    override val stateChangeValueHolder = mutableStateOf(ModalHandler.nullState)

    override fun setContentBuilder(builder: ModalHandler.Builder) {
        if (this.builder != null) throw IllegalStateException("BottomSheetHandler cannot process multiple call, dismiss current bottom sheet before setting new one")
        this.builder = builder as? BottomSheetBuilder
            ?: throw IllegalArgumentException("BottomSheetHandler expect only BottomSheetBuilder")

        stateChangeValueHolder.value = ModalHandler.newState
    }

    @Composable
    override fun Launch(state: ModalHandler.StateValue) {
        LaunchedEffect(suspendDismiss, state) {
            if (suspendDismiss) {
                bottomSheetState.hide()
            }
        }

        val builder = builder ?: return

        if (_bottomSheetState == null) {
            _bottomSheetState = builder.layoutBuilder.run {
                rememberModalBottomSheetState(initialValue, animationSpec, confirmStateChange)
            }
        }

        val layoutBuilder = builder.layoutBuilder
        val sheetValue = bottomSheetState.currentValue

        ModalBottomSheetLayout(
            modifier = builder.layoutBuilder.modifier,
            sheetContent = {
                builder.BuildIn(this, bottomSheetState)
            },
            sheetState = bottomSheetState,
            sheetShape = layoutBuilder.sheetShape,
            sheetElevation = layoutBuilder.sheetElevation,
            sheetBackgroundColor = layoutBuilder.sheetBackgroundColor,
            sheetContentColor = layoutBuilder.sheetContentColor,
            scrimColor = layoutBuilder.scrimColor,
            content = {
            },
        )

        LaunchedEffect(sheetValue, state) {
            when {
                show -> {
                    show = false
                    bottomSheetState.show()
                }
                sheetValue == ModalBottomSheetValue.Hidden -> {
                    dismiss()
                }
            }
        }
    }

    override fun dismiss() {
        builder = null
        suspendDismiss = true

        stateChangeValueHolder.value = ModalHandler.nullState
    }
}