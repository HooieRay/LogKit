package com.hooieray.logger.logcat

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.jvm.Throws

object LogcatUtils {

    private const val BUFFER_MAIN = "main"
    private const val BUFFER_RADIO = "radio"
    private const val BUFFER_EVENTS = "events"
    private const val BUFFER_SYSTEM = "system"
    private const val BUFFER_CRASH = "crash"
    private const val BUFFER_ALL = "all"
    private const val BUFFER_DEFAULT = "default"

    @Throws(IOException::class)
    fun getLogcatProcess(buffer:String):Process{
        val args= getLogcatArgs(buffer)
        return RuntimeUtils.exec(args)
    }

    fun getLastLogLine(buffer: String):String?{
        val args= getLogcatArgs(buffer).apply {
            add("-d")
        }
        val dumpLogcatProcess= RuntimeUtils.exec(args)
        val bufferedReader=BufferedReader(InputStreamReader(dumpLogcatProcess.inputStream),8192)

        var result:String? =null
        bufferedReader.use {
            while (true){
                result= it.readLine() ?: break
            }
        }

        RuntimeUtils.destroy(dumpLogcatProcess)

        return result
    }

    private fun getLogcatArgs(buffer: String):MutableList<String>{
        val args= arrayListOf("logcat","-v","time")
        //加载日志缓冲区，默认main缓冲区
        if (buffer!= BUFFER_MAIN){
            args.add("-b")
            args.add(buffer)
        }
        return args
    }
}