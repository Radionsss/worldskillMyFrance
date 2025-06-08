package workwork.company.worldskillstest.presenter.eventDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import workwork.company.worldskillstest.presenter.commons.components.BackButtonWithTitle
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.presenter.eventDetails.dialogs.DialogPhoto
import workwork.company.worldskillstest.ui.theme.mainFont

@Composable
fun EventDetailsScreen(
    viewModel: EventDetailsViewModel,
    onClickExit: () -> Unit,
    localEventData: LocalEvent?,
) {
    var selectedPhoto by remember { mutableStateOf("") }
    val imageDialog = remember { mutableStateOf(false) }


    if (imageDialog.value) {
        DialogPhoto(onCloseDialog = { imageDialog.value = false }, imageUrl =selectedPhoto)
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                .background(color = colorResource(R.color.main_color_default)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackButtonWithTitle(localEventData?.title?:"",onClickExit)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_eye),
                    contentDescription = "Views Icon",
                    tint = colorResource(R.color.main_blue),
                    modifier = Modifier.size(16.dp).padding(end = 4.dp)
                )
                Text(
                    text = localEventData?.viewCount.toString(),
                    fontFamily = mainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.main_blue),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text ="Count view",
                    fontFamily = mainFont,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.for_text_on_icons),
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
         ImageRow(
                localEventData?.images ?: emptyList(), onImageClick = {
                    selectedPhoto = it
                    imageDialog.value = true
                })
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = localEventData?.description?:"",
                fontFamily = mainFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.text_light_night),
            )
        }
    }
}


@Composable
private fun ImageRow(images: List<String>, onImageClick: (String) -> Unit) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        images.forEach { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f)
                    .padding(4.dp)
                    .clickable { onImageClick(imageUrl) }
            )
        }
    }
}