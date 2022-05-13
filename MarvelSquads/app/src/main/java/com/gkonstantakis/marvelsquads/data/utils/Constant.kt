package com.gkonstantakis.marvelsquads.data.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constant {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        val ts = Timestamp(System.currentTimeMillis()).time.toString()
        const val API_KEY = "819cd562b7fc4e508a388e0fc4d20176"
        const val PRIVATE_KEY = "da9347d5abe08d126f6efba7303977d600798fb8"

        fun computeHash(): String {
            val input = ts + PRIVATE_KEY + API_KEY
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}