package mrpapermoon.jetpackcomposemodalwindow.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import mrpapermoon.jetpackcomposemodalwindow.ModalHandler

abstract class BottomSheetBuilder : ModalHandler.Builder {
    override val layoutBuilder: LayoutBuilder
        get() = LayoutBuilder()

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BuildIn(columnScope: ColumnScope, bottomSheetState: ModalBottomSheetState) {
        columnScope.Build(bottomSheetState)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    abstract fun ColumnScope.Build(bottomSheetState: ModalBottomSheetState)

    @OptIn(ExperimentalMaterialApi::class)
    open class LayoutBuilder(
        val initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Hidden,
        val animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
        val confirmStateChange: (ModalBottomSheetValue) -> Boolean = { true },
    ) : ModalHandler.LayoutBuilder {

        open val modifier: Modifier = Modifier.systemBarsPadding()

        open val sheetShape: Shape
            @Composable get() = MaterialTheme.shapes.large

        open val sheetElevation: Dp
            @Composable get() = ModalBottomSheetDefaults.Elevation

        open val sheetBackgroundColor: Color
            @Composable get() = MaterialTheme.colors.background

        open val sheetContentColor: Color
            @Composable get() = contentColorFor(sheetBackgroundColor)

        open val scrimColor: Color
            @Composable get() = Color.Transparent.copy(alpha = 0.4f)
    }
}