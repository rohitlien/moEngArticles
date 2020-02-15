package com.trentzo.moengagenews.services

import android.app.IntentService
import android.content.Intent
import android.app.PendingIntent
import android.app.AlarmManager
import android.content.Context
import android.os.SystemClock
import com.trentzo.moengagenews.receivers.UpdateDataReceiver
import com.trentzo.moengagenews.beans.OfflineArticleData
import com.trentzo.moengagenews.network.CallBack
import com.trentzo.moengagenews.network.NetworkClient


class UpdateDataService : IntentService("UpdateDataService"), CallBack {
    override fun onSuccess(articles: ArrayList<OfflineArticleData>?) {

    }

    override fun onFailure() {
    }

    var alarmMgr: AlarmManager? = null


    override fun onHandleIntent(p0: Intent?) {

        NetworkClient(this, this)
    }

    private fun scheduleAlarm() {

        alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, UpdateDataReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }


        alarmMgr?.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
            AlarmManager.INTERVAL_HALF_HOUR,
            alarmIntent
        )


    }

}