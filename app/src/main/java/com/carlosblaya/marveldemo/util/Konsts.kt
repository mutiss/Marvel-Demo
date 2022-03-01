package com.carlosblaya.marveldemo.util

import com.carlosblaya.marveldemo.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Konsts {

    companion object {
        const val SPLASH_TIME_OUT: Long = 1000

        //Database
        const val DATABASE_NAME = "marveldemo_database.db"

        //Network
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val PUBLIC_KEY = BuildConfig.MARVEL_API_PUBLIC_KEY
        const val PRIVATE_KEY = BuildConfig.MARVEL_API_PRIVATE_KEY
        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
            val md = MessageDigest.getInstance("md5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

        //Paging
        const val NETWORK_PAGE_SIZE = 20
        const val INITIAL_LOAD_SIZE = 0
    }


}