package com.trentzo.moengagenews.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.trentzo.moengagenews.ui.MainActivity
import com.trentzo.moengagenews.utils.ConstantUtils

class NewArticleReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ConstantUtils.NEW_ARTICLE_ARRIVED -> {
                when (context) {
                    is MainActivity -> context.updateData()

                }
            }
        }
    }

}