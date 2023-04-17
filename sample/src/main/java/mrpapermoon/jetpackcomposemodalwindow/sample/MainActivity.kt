package mrpapermoon.jetpackcomposemodalwindow.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mrpapermoon.jetpackcomposemodalwindow.DefaultModalHandlerProvider
import mrpapermoon.jetpackcomposemodalwindow.LocalModalHandler
import mrpapermoon.jetpackcomposemodalwindow.ModalHandler
import mrpapermoon.jetpackcomposemodalwindow.bottomsheet.BottomSheetBuilder
import mrpapermoon.jetpackcomposemodalwindow.modalwindow.ModalAlertWindowBuilder
import mrpapermoon.jetpackcomposemodalwindow.modalwindow.ModalDialogWindowBuilder
import mrpapermoon.jetpackcomposemodalwindow.sample.ui.theme.JetpackComposeModalWindowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeModalWindowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DefaultModalHandlerProvider {

                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                24.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Text(text = "Select action")

                            ModalMenu()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModalMenu() {
    val modalHandler = LocalModalHandler.current ?: return

    Button(onClick = {
        modalHandler.windowBuilder()
    }) {
        Text(text = "ModalDialogWindowBuilder")
    }

    Button(onClick = {
        modalHandler.alertBuilder()
    }) {
        Text(text = "ModalAlertWindowBuilder")
    }

    Button(onClick = {
        modalHandler.sheetBuilder()
    }) {
        Text(text = "BottomSheetBuilder")
    }

    Button(onClick = modalHandler::dismiss) {
        Text(text = "Dismiss")
    }
}

private fun ModalHandler.sheetBuilder() {
    setContentBuilder(
        object : BottomSheetBuilder() {
            @OptIn(ExperimentalMaterialApi::class)
            @Composable
            override fun ColumnScope.Build(
                bottomSheetState: ModalBottomSheetState
            ) {
                Card(Modifier.defaultMinSize(minHeight = 64.dp)) {
                    Text("BottomSheet")
                }

                ModalMenu()
            }
        }
    )
}

private fun ModalHandler.alertBuilder() {
    setContentBuilder(
        object : ModalAlertWindowBuilder {
            @Composable
            override fun Buttons() {
                LazyRow {
                    item {
                        ModalMenu()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            @Composable
            override fun Title() {
                Text("ModalAlertWindow Title")
            }

            @Composable
            override fun Body() {
                Card() {
                    Text("ModalAlertWindow Body")
                }
            }
        }
    )
}

private fun ModalHandler.windowBuilder() {
    setContentBuilder(
        object : ModalDialogWindowBuilder {
            @Composable
            override fun Build() {
                Card {
                    Column {
                        Text("ModalDialogWindow")
                        ModalMenu()
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeModalWindowTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            Button(onClick = {
            }) {
                Text(text = "ModalDialogWindowBuilder")
            }

            Button(onClick = {
            }) {
                Text(text = "ModalAlertWindowBuilder")
            }

            Button(onClick = {
            }) {
                Text(text = "BottomSheetBuilder")
            }

            Button(onClick = {}) {
                Text(text = "Dismiss")
            }
        }
    }
}