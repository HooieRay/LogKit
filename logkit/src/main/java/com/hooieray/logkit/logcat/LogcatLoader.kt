package com.hooieray.logkit.logcat

/**
 * 缓冲区：https://blog.51cto.com/u_16099228/6366032
 * logcat分析：https://www.cnblogs.com/zhengtu2015/p/5134012.html
 */
class LogcatLoader private constructor(
    buffers:List<String>,
    recordingMode:Boolean
){
    companion object{

        private const val BUFFER_MAIN:String="main"

        fun create(recordingMode: Boolean): LogcatLoader {
            val buffers= arrayListOf(BUFFER_MAIN)
            return LogcatLoader(buffers,recordingMode)
        }
    }


    private var lastLines:MutableMap<String,String?> = hashMapOf()
    private var recordingMode: Boolean = false

    init {
        this.recordingMode=recordingMode
        buffers.forEach {buffer->
            val lastLine:String? =if (recordingMode) LogcatUtils.getLastLogLine(buffer) else null
            this.lastLines[buffer] =lastLine
        }
    }

    fun logReader(): LogcatReader {
        val buffer=lastLines.keys.iterator().next()
        val lastLine=lastLines.values.iterator().next()
        return SingleLogcatReader(true,buffer,lastLine)
    }
}