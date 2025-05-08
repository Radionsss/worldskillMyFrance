package workwork.company.worldskillstest.presenter.audio.playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File, onCompletion: () -> Unit)
    fun stop()
}