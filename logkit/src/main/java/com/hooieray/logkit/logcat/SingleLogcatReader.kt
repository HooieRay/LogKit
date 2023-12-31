package com.hooieray.logkit.logcat

import java.io.BufferedReader
import java.io.InputStreamReader

class SingleLogcatReader(
    recordingMode:Boolean,
    logBuffer:String,
    lastLine:String?
) : LogcatReader {

    private var logcatProcess:Process
    private var bufferedReader:BufferedReader? =null
    private var recordingMode:Boolean =false
    private var logBuffer:String? =null
    private var lastLine:String? =null

    init {
        this.recordingMode=recordingMode
        this.logBuffer=logBuffer
        this.lastLine=lastLine
        this.logcatProcess= LogcatUtils.getLogcatProcess(logBuffer)
        this.bufferedReader= BufferedReader(InputStreamReader(logcatProcess.inputStream),8192)
    }

    override fun readLine(): String? {
        val line=bufferedReader?.readLine()
        if (recordingMode &&lastLine!=null){
            if (lastLine==line){
                return null
            }
        }
        return line
    }

    override fun killProcess() {
        RuntimeUtils.destroy(logcatProcess)
    }
}