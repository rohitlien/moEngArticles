package com.trentzo.moengagenews.utils

import android.content.Context

object AppUtils {
    fun uploadFcmToken(token: String?, context: Context) {
        syncTokenToServer(token)
    }

    private fun syncTokenToServer(token: String?) {

    }
}