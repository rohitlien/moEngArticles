package com.trentzo.moengagenews

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.trentzo.moengagenews.ui.MainActivity
import com.trentzo.moengagenews.utils.ConstantUtils

class UpdateDataReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ConstantUtils.KEY_UPLOAD_DATA -> {
                when (context) {
                    is MainActivity -> {
                        context.updateData()
                    }
                }
            }
        }
    }


}