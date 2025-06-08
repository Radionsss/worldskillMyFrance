package workwork.company.worldskillstest.presenter.createTicket

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import workwork.company.worldskillstest.presenter.commons.components.BackButtonWithTitle
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.domain.models.local.ticket.TicketFilter
import workwork.company.worldskillstest.presenter.commons.components.BasicInputField
import workwork.company.worldskillstest.presenter.commons.components.ButtonCustom
import workwork.company.worldskillstest.presenter.createTicket.component.DatePicker
import workwork.company.worldskillstest.presenter.createTicket.component.DialogTimePicker
import workwork.company.worldskillstest.presenter.createTicket.component.SelectTicketFilter
import java.io.InputStream
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTicketScreen(
    viewModel: CreateTicketScreenViewModel,
    onExitClick: () -> Unit,
) {
    val context = LocalContext.current
    val tabItems = listOf(
        "Opening ceremonies",
        "Closing ceremonies\n",
    )
    val textName = remember { mutableStateOf("") }
    val textAudienceName = remember { mutableStateOf("") }

    var selectedFilter by remember { mutableStateOf(TicketFilter.OPEN) }
    val selectedTabIndex = remember {
        mutableIntStateOf(1)
    }
    var time by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap: Bitmap? = remember(imageUri) {
        imageUri?.let {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                BitmapFactory.decodeStream(inputStream)
            } catch (_: Exception) {
                null
            }
        }
    }
    val timeDialog = remember { mutableStateOf(false) }

    val pagerState = rememberPagerState {
        tabItems.size
    }
    var date by remember { mutableStateOf("") }
    val dateDialogState = rememberMaterialDialogState()
    if (timeDialog.value) {
        DialogTimePicker(onCloseDialog = { timeDialog.value = false }, onConfirm = {
            time = it
        })
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                imageUri = uri
            }
        }
    }
    DatePicker(
        allowedDaysOfWeek = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
        ),
        dateDialogState = dateDialogState,
        onClickOK = {
            // dateDialogState.
        },
        onDateSelected = { dateSelectedValue ->
            date = formatDate(dateSelectedValue)
        }
    )
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
            BackButtonWithTitle("Ticket Create", onExitClick = onExitClick)
            Spacer(modifier = Modifier.height(16.dp))

            BasicInputField(
                value = textName.value,
                onValueChange = { textName.value = it },
                placeholder = "Input your name",
            )
            BasicInputField(
                value = textAudienceName.value,
                onValueChange = { textAudienceName.value = it },
                placeholder = "Input audience",
            )
            BasicInputField(
                value = date,
                placeholder = "Date",
                onClick = {
                    dateDialogState.show()
                }
            )
            BasicInputField(
                value = time,
                placeholder = "Time",
                onClick = {
                    timeDialog.value = true
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            SelectTicketFilter(
                selectedFilter = selectedFilter,
                onSelectionChange = { filter ->
                    selectedFilter = filter
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ImageTicket(bitmap,launcher)
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            ButtonCustom("Create") {
                val formattedDateTime = "$date $time"
                val uriString: String = imageUri.toString()
                viewModel.createTicket(
                    TicketEntity(
                        ticketType = selectedFilter,
                        name = textName.value,
                        audienceName = textAudienceName.value,
                        ticketImagePath = uriString,
                        time = formattedDateTime,
                        seat = generateSeat(),
                        orderIndex = 0
                    )
                )
                onExitClick()
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

fun generateSeat(): String {
    val letter = listOf("A", "B", "C").random()
    val number1 = Random.nextInt(1, 11)
    val number2 = Random.nextInt(1, 11)
    val number3 = Random.nextInt(1, 11)

    return "$letter$number1 Row$number2 Column$number3"
}

@Composable
fun ImageTicket(bitmap: Bitmap?, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        val intent = Intent(Intent.ACTION_PICK).apply {
                            type = "image/*"
                        }
                        launcher.launch(intent)
                    }
                    .background(colorResource(R.color.main_blue).copy(0.1f)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        val intent = Intent(Intent.ACTION_PICK).apply {
                            type = "image/*"
                        }
                        launcher.launch(intent)
                    }
                    .background(colorResource(R.color.main_blue).copy(0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_not_have_image),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = colorResource(R.color.main_blue).copy(alpha = 0.4f)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return date.format(formatter)
}
