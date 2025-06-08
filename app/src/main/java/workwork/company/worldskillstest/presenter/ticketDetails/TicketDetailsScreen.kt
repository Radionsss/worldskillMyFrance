package workwork.company.worldskillstest.presenter.ticketDetails

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Picture
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.presenter.commons.components.BackButtonWithTitle
import workwork.company.worldskillstest.presenter.commons.components.ButtonCustom
import java.io.File

@Composable
fun TicketDetailsScreen(
    ticketData: TicketEntity?,
    onClickExit: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackButtonWithTitle("Ticket Details", onClickExit)
        val picture = remember { Picture() }

        TicketDetailsContent(
            ticketData, Modifier
                .drawWithCache {
                    val width = this.size.width.toInt()
                    val height = this.size.height.toInt()
                    onDrawWithContent {
                        val pictureCanvas =
                            androidx.compose.ui.graphics.Canvas(
                                picture.beginRecording(
                                    width,
                                    height
                                )
                            )
                        draw(this, this.layoutDirection, pictureCanvas, this.size) {
                            this@onDrawWithContent.drawContent()
                        }
                        picture.endRecording()

                        drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                    }
                }
        )



        Spacer(modifier = Modifier.height(16.dp))

        ButtonCustom("Download", onClick = {
            val bitmap = createBitmapFromPicture(picture)

            CoroutineScope(Dispatchers.IO).launch {
                val uri = bitmap.saveToDisk(context)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Saved to Gallery: $uri", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

@Composable
fun TicketDetailsContent(ticketData: TicketEntity?, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ttt),
            contentDescription = "Ticket Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ticket Type: ${ticketData?.ticketType}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Text(
            text = "Audience: ${ticketData?.audienceName}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Text(
            text = "Time: ${ticketData?.time}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Text(
            text = "Seat: ${ticketData?.seat}",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val width = picture.width
    val height = picture.height

    if (width <= 0 || height <= 0) {
        throw IllegalArgumentException("Invalid Picture dimensions: width=$width, height=$height")
    }
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = android.graphics.Canvas(bitmap)
    try {
        canvas.drawColor(android.graphics.Color.WHITE)
        canvas.drawPicture(picture)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }

    return bitmap
}

private suspend fun Bitmap.saveToDisk(context: Context): Uri {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "screenshot-${System.currentTimeMillis()}.png"
    )

    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

    return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}


private fun scanFilePath(context: Context, filePath: String): Uri? {
    val file = File(filePath)
    val uri = Uri.fromFile(file)

    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    mediaScanIntent.data = uri
    context.sendBroadcast(mediaScanIntent)

    return uri
}