package com.hooieray.logger.logcat

import java.io.IOException
import kotlin.jvm.Throws

interface LogcatReader {

    /**
     * 读取一行日志
     */
    @Throws(IOException::class)
    fun readLine() :String?

    fun killProcess()
}