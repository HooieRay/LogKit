package com.hooieray.logger.logcat

object RuntimeUtils{

    fun exec(args:List<String>):Process{
        return Runtime.getRuntime().exec(args.toTypedArray())
    }

    fun destroy(process: Process){
        process.destroy()
    }

}