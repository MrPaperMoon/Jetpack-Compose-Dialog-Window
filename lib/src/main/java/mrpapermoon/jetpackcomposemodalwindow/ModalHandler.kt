package mrpapermoon.jetpackcomposemodalwindow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.staticCompositionLocalOf

val LocalModalHandler: ProvidableCompositionLocal<ModalHandler?> = staticCompositionLocalOf { null }

interface ModalHandler {

    val stateChangeValueHolder: State<StateValue>

    fun setContentBuilder(builder: Builder)

    @Composable
    fun Launch(
        //trigger recomposition
        state: StateValue
    )

    fun dismiss()

    interface Builder {
        val layoutBuilder: LayoutBuilder
    }

    interface LayoutBuilder

    interface StateValue
    private class NullStateValue : StateValue
    private class NewStateValue : StateValue

    companion object {
        val nullState: StateValue
            get() = NullStateValue()
        val newState: StateValue
            get() = NewStateValue()
    }
}