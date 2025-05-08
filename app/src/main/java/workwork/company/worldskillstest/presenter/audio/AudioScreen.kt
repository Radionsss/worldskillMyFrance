package workwork.company.worldskillstest.presenter.audio

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.res.painterResource
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.presenter.audio.playback.AndroidAudioPlayer
import workwork.company.worldskillstest.presenter.audio.record.AndroidAudioRecorder
import workwork.company.worldskillstest.presenter.commons.components.BackButtonWithTitle
import java.io.File


@Composable
fun AudioScreen(viewModel: AudioViewModel) {
    val context = LocalContext.current
    val audioList by viewModel.audios.collectAsState()

    val recorder = remember { mutableStateOf(AndroidAudioRecorder(context)) }
    val player = remember { mutableStateOf(AndroidAudioPlayer(context)) }

    var audioFile by remember { mutableStateOf<File?>(null) }
    var currentlyPlayingId by remember { mutableStateOf<String?>(null) }
    var isRecording by remember { mutableStateOf(false) }
    var isAudioAvailable by remember { mutableStateOf(false) } // Флаг наличия записанного аудио

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackButtonWithTitle("Records")

        Button(onClick = {
            File(context.cacheDir, "audio_${System.currentTimeMillis()}.mp3").also {
                recorder.value.start(it)
                audioFile = it
                currentlyPlayingId = null
                isRecording = true
                isAudioAvailable = false
            }
        }) {
            Text(text = "Start recording")
        }

        Button(onClick = {
            recorder.value.stop()
            isRecording = false
            isAudioAvailable = audioFile?.exists() == true // Проверяем, записался ли файл
        }, enabled = isRecording) {
            Text(text = "Stop recording")
        }

        Button(onClick = {
            if (audioFile?.exists() == true) {
                player.value.playFile(audioFile ?: return@Button) {
                    currentlyPlayingId = null
                }
            } else {
                Toast.makeText(context, "Файл не найден", Toast.LENGTH_SHORT).show()
            }
        }, enabled = isAudioAvailable && !isRecording) { // Отключаем кнопку, если аудио не записано
            Text(text = "Play")
        }

        Button(onClick = {
            if (audioFile?.exists() == true) {
                viewModel.addAudio(audioFile!!.absolutePath)
                Toast.makeText(context, "Отправить успешно", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Нет записи для отправки", Toast.LENGTH_SHORT).show()
            }
        }, enabled = isAudioAvailable && !isRecording) { // Отключаем кнопку, если аудио не записано
            Text(text = "Submit")
        }

        LazyColumn {
            items(audioList) { audio ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Audio ${audio.id}", modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            if (currentlyPlayingId == audio.id.toString()) {
                                player.value.stop()
                                currentlyPlayingId = null
                            } else {
                                player.value.stop()
                                currentlyPlayingId = audio.id.toString()

                                player.value.playFile(File(audio.filePath)) {
                                    currentlyPlayingId = null
                                }
                            }
                        },
                        enabled = !isRecording // Отключаем кнопку Play во время записи
                    ) {
                        Icon(
                            painter = if (currentlyPlayingId == audio.id.toString())
                                painterResource(R.drawable.ic_stop)
                            else
                                painterResource(R.drawable.ic_play),
                            contentDescription = if (currentlyPlayingId == audio.id.toString())
                                "Pause Audio"
                            else
                                "Play Audio"
                        )
                    }
                }
            }
        }
    }
}