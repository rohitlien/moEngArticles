package com.trentzo.moengagenews.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.trentzo.moengagenews.R
import com.trentzo.moengagenews.receivers.UpdateDataReceiver
import com.trentzo.moengagenews.ViewArticleActivity
import com.trentzo.moengagenews.adpters.ArticleAdapter
import com.trentzo.moengagenews.beans.OfflineArticleData
import com.trentzo.moengagenews.callBacks.OnArticleClickListener
import com.trentzo.moengagenews.network.CallBack
import com.trentzo.moengagenews.network.GetUpdatedDataTask
import com.trentzo.moengagenews.network.NetworkClient
import com.trentzo.moengagenews.receivers.NewArticleReceiver
import com.trentzo.moengagenews.storage.sqllite.ArticleDbHelper
import com.trentzo.moengagenews.storage.sqllite.StorageConstants
import com.trentzo.moengagenews.utils.AppPreference
import com.trentzo.moengagenews.utils.AppUtils
import com.trentzo.moengagenews.utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CallBack, OnArticleClickListener {

    private var adapter: ArticleAdapter? = null
    private var updateDataReceiver = UpdateDataReceiver()
    private var onNewArticleArrived = NewArticleReceiver()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        articlesRv.layoutManager = linearLayoutManager

        ArticleDbHelper.getOfflineArticles().let {
            if (it.size > 0) {
                setAdapter(it)
            } else {
                NetworkClient(this, this).execute()
            }
        }
    }

    private fun setAdapter(articles: ArrayList<OfflineArticleData>) {
        if (shimmerView.visibility == View.VISIBLE) {
            shimmerView.visibility = View.GONE
        }
        if (adapter == null) {
            adapter = ArticleAdapter(this, articles, this)
            articlesRv.adapter = adapter
        } else {
            adapter?.updateAdapter(articles)
        }
    }

    override fun onArticleClick(article: OfflineArticleData?) {
        val intent = Intent(this@MainActivity, ViewArticleActivity::class.java)
        intent.putExtra(ConstantUtils.KEY_ARTICLE_URL, article?.url)
        startActivity(intent)
    }

    override fun onSuccess(articles: ArrayList<OfflineArticleData>?) {
        articles?.let { setAdapter(it) }
    }

    override fun onFailure() {

    }

    override fun onStart() {
        super.onStart()
        val filterNewActivity = IntentFilter().apply { addAction(ConstantUtils.KEY_UPLOAD_DATA) }
        registerReceiver(updateDataReceiver, filterNewActivity)

        val newEcgReceivedIntentFilter = IntentFilter()
        newEcgReceivedIntentFilter.addAction(ConstantUtils.NEW_ARTICLE_ARRIVED)
        registerReceiver(onNewArticleArrived, newEcgReceivedIntentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(updateDataReceiver)
        unregisterReceiver(onNewArticleArrived)
    }

    fun updateData() {
        GetUpdatedDataTask(this).execute()
    }

    override fun onResume() {
        super.onResume()
        checkForFcmTokenAndUpdate()
    }

    fun checkForFcmTokenAndUpdate() {
        //Log.d("FCMTest", "Check for FCM token update called")
        var savedFcmId = AppPreference.getPreference(StorageConstants.FCM_ID)
        if (savedFcmId.isNullOrEmpty()) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) return@OnCompleteListener

                    val token = task.result?.token // Get new Instance ID token
                    savedFcmId = token
                    token?.let {
                        AppPreference.apply {
                            setPreference(StorageConstants.FCM_ID, it)
                            removePreference(StorageConstants.FCM_ID_SYNCED)
                        }
                    }
                })
        }

        val isFcmIdSynced = AppPreference.getPreference(StorageConstants.FCM_ID_SYNCED)
        if (!savedFcmId.isNullOrEmpty() && (isFcmIdSynced.isNullOrEmpty()
                    || isFcmIdSynced != StorageConstants.FCM_ID_SYNCED)) {
            AppUtils.uploadFcmToken(savedFcmId, getApplication())
        }

    }




}
