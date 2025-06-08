package workwork.company.worldskillstest.presenter.createTicket.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import workwork.company.worldskillstest.R
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    allowedDaysOfWeek: List<DayOfWeek>,
    dateDialogState: MaterialDialogState,
    onClickOK: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val allowedDateValidatorByDayOfWeek: (LocalDate) -> Boolean = { date ->
        allowedDaysOfWeek.contains(date.dayOfWeek)
    }



    MaterialDialog(
        backgroundColor = colorResource(id = R.color.date_picker),
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok",textStyle= TextStyle(
                color = colorResource(id = R.color.text_light_night)
            )){
                onClickOK()
            }
            negativeButton(text = stringResource(id = R.string.cancel),textStyle= TextStyle(
                color = colorResource(id = R.color.text_light_night)
            ))
        },
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(id = R.string.pick_date),
            colors = object : DatePickerColors {
                override val calendarHeaderTextColor: Color = colorResource(id = R.color.text_light_night)
                override val headerBackgroundColor: Color = colorResource(id = R.color.main_blue)
                override val headerTextColor: Color = Color.White

                @Composable
                override fun dateBackgroundColor(active: Boolean): State<Color> {
                    return rememberUpdatedState(if (active)colorResource(id = R.color.main_color_items) else Color.Transparent)   }

                @Composable
                override fun dateTextColor(active: Boolean): State<Color> {
                    return rememberUpdatedState(if (active)colorResource(id =R.color.background_light_night_reverse)else  colorResource(id = R.color.text_light_night))       }
            },
            allowedDateValidator = { date ->
                allowedDateValidatorByDayOfWeek(date)
            }
        ) {
            pickedDate = it
            onDateSelected(it)
        }
    }
}