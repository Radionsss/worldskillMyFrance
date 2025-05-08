package workwork.company.worldskillstest.presenter.ticketDetails

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Picture
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap
import coil.compose.rememberAsyncImagePainter
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
    val picture = remember { Picture() }
    var isImageLoaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackButtonWithTitle("Ticket Details", onClickExit)
        val picture = remember { Picture() }

        TicketDetailsContent(
            ticketData, Modifier
                .drawWithCache {
                    // Example that shows how to redirect rendering to an Android Picture and then
                    // draw the picture into the original destination
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
    Log.d("fefwfwfwefwf", "picture.width: ${picture.width}")
    Log.d("fefwfwfwefwf", "picture.width: ${picture.height}")
    if (width <= 0 || height <= 0) {
        throw IllegalArgumentException("Invalid Picture dimensions: width=$width, height=$height")
    }

    // ✅ Создаем программный (Software) Bitmap
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    // ✅ Рисуем только если есть контент
    val canvas = android.graphics.Canvas(bitmap)
    try {
        canvas.drawColor(android.graphics.Color.WHITE)
        canvas.drawPicture(picture) // 🔥 Теперь `Picture` всегда будет Software
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


// ✅ Реализуем функцию scanFilePath (чтобы добавить в галерею)
private fun scanFilePath(context: Context, filePath: String): Uri? {
    val file = File(filePath)
    val uri = Uri.fromFile(file)

    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    mediaScanIntent.data = uri
    context.sendBroadcast(mediaScanIntent)

    return uri
}