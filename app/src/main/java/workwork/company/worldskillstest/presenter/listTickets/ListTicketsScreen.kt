package workwork.company.worldskillstest.presenter.listTickets

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.presenter.commons.components.BackButtonWithTitle
import workwork.company.worldskillstest.core.Constant.PADDING_HORIZONTAL
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.presenter.commons.components.BoxWithShadow
import workwork.company.worldskillstest.presenter.commons.components.TabRowCustom
import workwork.company.worldskillstest.domain.models.local.ticket.TicketFilter
import workwork.company.worldskillstest.ui.theme.mainFont
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import workwork.company.worldskillstest.presenter.commons.components.ButtonCustom

@Composable
fun ListTicketsScreen(
    viewModel: ListTicketsScreenViewModel,
    onClickDetailsTicket: (TicketEntity) -> Unit,
    onClickCreateTicket: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    // val addStudentState = viewModel.addStudentStateFlow.collectAsState().value
    val context = LocalContext.current
    val tabItems = listOf(
        "Opening",
        "Closing",
    )
    val selectedTabIndex = remember {
        mutableIntStateOf(1)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    val tickets = viewModel.tickets.collectAsState().value
    LaunchedEffect(selectedTabIndex.intValue) {
        pagerState.animateScrollToPage(selectedTabIndex.intValue)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex.intValue = pagerState.currentPage
        }
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                .background(color = colorResource(R.color.main_color_default)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackButtonWithTitle("Tickets List")
            Spacer(modifier = Modifier.height(16.dp))
            ButtonCustom("Create a new ticket", onClick = onClickCreateTicket)
            Spacer(modifier = Modifier.height(16.dp))
            TabRowCustom(
                tabItems = tabItems,
                selectedIndex = selectedTabIndex.intValue,
                onTabSelected = { index ->
                    selectedTabIndex.intValue = index
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false, // Отключаем скролл через палец
                modifier = Modifier
                    .fillMaxWidth()
                // .weight(1f)
            ) { index ->
                when (index) {
                    0 -> {
                        LazyColumnTickets(tickets, TicketFilter.OPEN, onClick = {
                            onClickDetailsTicket(it)
                        }, onDelete = {
                            viewModel.deleteTicket(it)
                        },     onReorder = { reorderedList ->
                            viewModel.updateTicketsOrder(reorderedList)
                        })
                    }

                    else -> {
                        LazyColumnTickets(tickets, TicketFilter.CLOSE, onClick = {
                            onClickDetailsTicket(it)
                        }, onDelete = {
                            viewModel.deleteTicket(it)
                        },    onReorder = { reorderedList ->
                            viewModel.updateTicketsOrder(reorderedList)

                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun LazyColumnTickets(
    tickets: List<TicketEntity>,
    filter: TicketFilter,
    onClick: (TicketEntity) -> Unit,
    onDelete: (TicketEntity) -> Unit,
    onReorder: (List<TicketEntity>) -> Unit
) {
    val filteredTickets = tickets.filter { it.ticketType == filter }
    val reorderState = rememberReorderableLazyListState(onMove = { from, to ->
        // Создаем новый список с обновленным порядком
        if (filteredTickets.isNotEmpty()) {
            val updatedList = filteredTickets.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            Log.d("LazyColumnTickets", "Updated List: $updatedList")
            onReorder(updatedList) // Передаем обновленный список через callback
        }
    })
    Log.d("fefewwefwf", "Up $filteredTickets")

    if (filteredTickets.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tickets available",
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        LazyColumn(
            state = reorderState.listState,
            modifier = Modifier
                .fillMaxSize()
                .reorderable(reorderState)
                .detectReorderAfterLongPress(reorderState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTickets, key = { it.id }) { ticket ->
                ReorderableItem(reorderState, key = ticket.id) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp)
                    SwipeableActionsBox(
                        modifier = Modifier
                         //   .shadow(elevation.value)
                           // .background(MaterialTheme.colorScheme.surface)
                        ,
                        swipeThreshold = 200.dp,
                        startActions = listOf(
                            SwipeAction(
                                onSwipe = { onDelete(ticket) },
                                icon = {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                },
                                background = Color.Red.copy(alpha = 0.5f),
                                isUndo = true
                            )
                        )
                    ) {
                        EventListItem(
                            name = ticket.name,
                            seat = ticket.seat,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = PADDING_HORIZONTAL.dp),
                            onClick = { onClick(ticket) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventListItem(
    name: String,
    seat: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit // Колбэк для обработки нажатия
) {
    BoxWithShadow(modifier = modifier, content = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.background_for_inactive_icons))
                .clip(RoundedCornerShape(16.dp))
                .height(100.dp)

                .clickable { onClick() } // Обрабатываем нажатие
        ) {

            Spacer(modifier = Modifier.width(8.dp))

            // Текстовые элементы
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontFamily = mainFont,
                    fontSize = 14.sp,
                    maxLines = 1, // Ограничение на 2 строки
                    overflow = TextOverflow.Ellipsis,// Добавляет "..." при превышении длины
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.main_blue),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = seat,
                    fontFamily = mainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.text_light_night),
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 2, // Ограничение на 2 строки
                    overflow = TextOverflow.Ellipsis // Добавляет "..." при превышении длины
                )

            }
        }
    })
}
