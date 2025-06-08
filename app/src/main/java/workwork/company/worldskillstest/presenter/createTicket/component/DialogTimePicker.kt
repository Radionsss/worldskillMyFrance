package workwork.company.worldskillstest.presenter.createTicket.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTimePicker(
    onConfirm: (String) -> Unit,
    onCloseDialog: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    Dialog(onDismissRequest = onCloseDialog) {

        Column(
            modifier = Modifier
                .background(color = androidx.compose.ui.graphics.Color.White)
                .padding(16.dp)
        ) {
            TimePicker(
                state = timePickerState,
            )
            Row {
                Button(onClick = onCloseDialog) {
                    Text("Dismiss picker")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    val formattedTime =
                        String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                    onConfirm(formattedTime)
                    onCloseDialog()
                }) {
                    Text("Confirm selection")
                }
            }
        }
    }
}