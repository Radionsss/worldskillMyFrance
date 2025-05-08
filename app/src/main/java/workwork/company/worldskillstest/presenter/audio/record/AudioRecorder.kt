package workwork.company.worldskillstest.presenter.audio.record

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}