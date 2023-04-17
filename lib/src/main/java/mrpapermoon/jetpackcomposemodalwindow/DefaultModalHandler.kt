package mrpapermoon.jetpackcomposemodalwindow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import mrpapermoon.jetpackcomposemodalwindow.bottomsheet.BottomSheetBuilder
import mrpapermoon.jetpackcomposemodalwindow.bottomsheet.BottomSheetHandler
import mrpapermoon.jetpackcomposemodalwindow.modalwindow.ModalWindowBuilder
import mrpapermoon.jetpackcomposemodalwindow.modalwindow.ModalWindowHandler

private val ModalHandler?.state
    get() = this?.stateChangeValueHolder?.value ?: ModalHandler.nullState

@Composable
fun DefaultModalHandlerProvider(content: @Composable () -> Unit) {
    val handler = remember { DefaultModalHandler() }
    CompositionLocalProvider(LocalModalHandler provides handler) {
        content()
        LocalModalHandler.current?.run {
            Launch(stateChangeValueHolder.value)
        }
    }
}

open class DefaultModalHandler : ModalHandler {

    override val stateChangeValueHolder = mutableStateOf(ModalHandler.nullState)

    private val stack = mutableListOf<ModalHandler>()

    override fun setContentBuilder(builder: ModalHandler.Builder) {
        val handler = modalHandlerProvider(builder)
        stack += handler
        handler.setContentBuilder(builder)
        stateChangeValueHolder.value = handler.state
    }

    @Composable
    override fun Launch(state: ModalHandler.StateValue) {
        stack.forEach { handler ->
            handler.Launch(handler.state)
        }
    }

    override fun dismiss() {
        stack.removeLastOrNull()?.dismiss()
        stateChangeValueHolder.value = stack.lastOrNull().state
    }

    protected open fun modalHandlerProvider(builder: ModalHandler.Builder): ModalHandler {
        return when (builder) {
            is ModalWindowBuilder -> ModalWindowHandler()
            is BottomSheetBuilder -> BottomSheetHandler()
            else -> throw IllegalStateException("DefaultModalHandler don't support $builder builder")
        }
    }
}