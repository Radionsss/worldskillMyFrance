package workwork.company.worldskillstest.presenter.audio.playback

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File
class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File, onCompletion: () -> Unit) {
        stop() // Останавливаем предыдущее аудио перед началом нового
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            setOnCompletionListener {
                onCompletion() // Вызываем колбэк при завершении
                stop()
            }
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}