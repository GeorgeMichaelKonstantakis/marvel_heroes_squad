package com.gkonstantakis.marvelsquads.ui.utils

class Utils {

    fun safeUrl(heroUrl: String): String{
        if (heroUrl.contains("http")) {
            return heroUrl.replace("http", "https")
        } else {
            return heroUrl
        }
    }
}