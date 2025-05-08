package workwork.company.worldskillstest.presenter.createTicket.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.domain.models.local.ticket.TicketFilter
import workwork.company.worldskillstest.presenter.commons.components.TextInBoxWithShadow

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectTicketFilter(
    modifier: Modifier = Modifier,
    selectedFilter: TicketFilter?, // Выбранный фильтр (может быть null)
    onSelectionChange: (TicketFilter) -> Unit // Коллбэк для изменения выбора
) {
    val options = listOf(
        "Opening" to TicketFilter.OPEN,
        "Closing" to TicketFilter.CLOSE
    )

    val selectedColor = colorResource(R.color.main_blue)
    val unSelectedColor = colorResource(R.color.for_text_on_icons)

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { (text, filter) ->
            val isSelected = selectedFilter == filter

            TextInBoxWithShadow(
                text = text,
                isSelected = isSelected,
                onClick = {
                    onSelectionChange(filter) // Вызываем коллбэк с выбранным фильтром
                },
                selectedColor = selectedColor,
                unSelectedColor = unSelectedColor
            )
        }
    }
}