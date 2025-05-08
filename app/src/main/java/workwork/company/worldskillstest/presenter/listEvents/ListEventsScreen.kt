package workwork.company.worldskillstest.presenter.listEvents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import workwork.company.worldskillstest.presenter.commons.components.BackButtonWithTitle
import workwork.company.worldskillstest.core.Constant.PADDING_HORIZONTAL
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.presenter.commons.components.BoxWithShadow
import workwork.company.worldskillstest.presenter.commons.components.TabRowCustom
import workwork.company.worldskillstest.presenter.listEvents.data.EventFilter
import workwork.company.worldskillstest.ui.theme.mainFont

@Composable
fun ListEventsScreen(
    viewModel: ListEventsScreenViewModel,
    onClickDetailsEvent: (LocalEvent) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    // val addStudentState = viewModel.addStudentStateFlow.collectAsState().value
    val context = LocalContext.current
    val tabItems = listOf(
        "All",
        "Unread",
        "Read",
    )
    val selectedTabIndex = remember {
        mutableIntStateOf(1)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    val events = viewModel.events.collectAsState().value
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
                .padding(top = 20.dp)
                .background(color = colorResource(R.color.main_color_default)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackButtonWithTitle("Events List")
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
                        LazyColumnGroups(events, EventFilter.ALL, onClick = {
                            viewModel.markAsRead(it.id)
                            viewModel.incrementViewCount(it.id)
                            onClickDetailsEvent(it)
                        })
                    }

                    1 -> {
                        LazyColumnGroups(events, EventFilter.UNREAD, onClick = {
                            viewModel.markAsRead(it.id)
                            viewModel.incrementViewCount(it.id)
                            onClickDetailsEvent(it)
                        })
                    }

                    else -> {
                        LazyColumnGroups(events, EventFilter.READ, onClick = {
                            viewModel.markAsRead(it.id)
                            viewModel.incrementViewCount(it.id)
                            onClickDetailsEvent(it)
                        })
                    }
                }
            }
        }
    }
}


@Composable
private fun LazyColumnGroups(
    events: List<LocalEvent>,
    filter: EventFilter, // Используем enum
    onClick: (LocalEvent) -> Unit // Колбэк для обработки нажатия
) {
    // Фильтрация событий в зависимости от выбранного фильтра
    val filteredEvents = when (filter) {
        EventFilter.READ -> events.filter { it.isRead }
        EventFilter.UNREAD -> events.filter { !it.isRead }
        EventFilter.ALL -> events // Все события
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(filteredEvents) { event ->
         EventListItem(
                title = event.title,
                text = event.description,
                isUnread = !event.isRead,
                imageRes = event.imageRes, // Замените на свой ресурс
                modifier = Modifier.padding(
                    end = PADDING_HORIZONTAL.dp,
                    start = PADDING_HORIZONTAL.dp
                ),
                onClick = {
                    onClick(event)
                }
            )
        }
    }
}

@Composable
private fun EventListItem(
    title: String,
    text: String,
    isUnread: Boolean,
    imageRes: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit // Колбэк для обработки нажатия
) {
    BoxWithShadow(modifier=modifier,content = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.background_for_inactive_icons))
                .clip(RoundedCornerShape(16.dp))
                .height(100.dp)

                .clickable { onClick() } // Обрабатываем нажатие
        ) {
            // Изображение

            AsyncImage(
                model = imageRes, // URL или ресурс изображения
                contentDescription = null,
                contentScale = ContentScale.Crop, // Изображение растягивается, чтобы заполнить доступное пространство
                modifier = Modifier
                    .fillMaxHeight() // Высота изображения равна высоте родительского контейнера
                    .weight(0.3f) // 30% ширины
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)) // Округление углов
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Текстовые элементы
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontFamily = mainFont,
                    fontSize = 14.sp,
                    maxLines = 1, // Ограничение на 2 строки
                    overflow = TextOverflow.Ellipsis ,// Добавляет "..." при превышении длины
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.main_blue),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = text,
                    fontFamily = mainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.text_light_night),
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 2, // Ограничение на 2 строки
                    overflow = TextOverflow.Ellipsis // Добавляет "..." при превышении длины
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (isUnread) "Unread" else "Read",
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (isUnread) colorResource(R.color.for_text_on_icons) else colorResource(
                            R.color.main_blue
                        ),
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                }
            }
        }
    })
}
