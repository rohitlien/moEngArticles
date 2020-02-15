package com.trentzo.moengagenews.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.trentzo.moengagenews.storage.sqllite.StorageConstants
import com.trentzo.moengagenews.utils.AppPreference
import com.trentzo.moengagenews.utils.AppUtils

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)


        if (!token.isNullOrEmpty()) {
            val savedFcmId = AppPreference.getPreference(StorageConstants.FCM_ID)

            if (savedFcmId.isNullOrEmpty() || savedFcmId != token) {
                AppPreference.setPreference(StorageConstants.FCM_ID, token)
                AppPreference.removePreference(StorageConstants.FCM_ID_SYNCED)

                AppUtils.uploadFcmToken(savedFcmId, this)
            }
        }
    }
}