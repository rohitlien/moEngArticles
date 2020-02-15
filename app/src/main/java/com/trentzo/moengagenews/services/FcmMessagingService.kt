package com.trentzo.moengagenews.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.trentzo.moengagenews.R
import com.trentzo.moengagenews.beans.ArticleData
import com.trentzo.moengagenews.storage.sqllite.ArticleDbHelper
import com.trentzo.moengagenews.ui.MainActivity
import com.trentzo.moengagenews.utils.ConstantUtils

class FcmMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val dataMap = remoteMessage.data
        val channelId = "MoEngage-notification"
        val channelName = "Mo Engage Channel"
        val messageText = "Hello from MoEngage"

        var message = dataMap["message"]
        var title = dataMap["title"]

        if (message.isNullOrEmpty()) {
            message = messageText
        }
        if (title.isNullOrEmpty()) {
            title = getString(R.string.app_name)
        }


        val articleData = dataMap["data"]

        try {
            if(!articleData.isNullOrEmpty()){
                val article = Gson().fromJson(articleData,ArticleData::class.java)
                ArticleDbHelper.insertArticle(article)
            }
        } catch (e: Exception) {
        }


        val intent = Intent(this, MainActivity::class.java)

        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        val pIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), intent, 0)

        val mBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)

        mBuilder.build().flags = NotificationCompat.FLAG_AUTO_CANCEL

        notificationManager.notify(0, mBuilder.build())

        val localBroadCast = Intent(ConstantUtils.NEW_ARTICLE_ARRIVED)
        sendBroadcast(localBroadCast)


    }
}