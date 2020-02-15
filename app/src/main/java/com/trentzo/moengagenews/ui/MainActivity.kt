package com.trentzo.moengagenews.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.trentzo.moengagenews.R
import com.trentzo.moengagenews.UpdateDataReceiver
import com.trentzo.moengagenews.ViewArticleActivity
import com.trentzo.moengagenews.adpters.ArticleAdapter
import com.trentzo.moengagenews.beans.OfflineArticleData
import com.trentzo.moengagenews.callBacks.OnArticleClickListener
import com.trentzo.moengagenews.network.CallBack
import com.trentzo.moengagenews.network.GetUpdatedDataTask
import com.trentzo.moengagenews.network.NetworkClient
import com.trentzo.moengagenews.services.UpdateDataService
import com.trentzo.moengagenews.storage.sqllite.ArticleDbHelper
import com.trentzo.moengagenews.storage.sqllite.StorageConstants
import com.trentzo.moengagenews.utils.AppPreference
import com.trentzo.moengagenews.utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),CallBack,OnArticleClickListener {

    private var adapter:ArticleAdapter?=null
    private var updateDataReceiver = UpdateDataReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        articlesRv.layoutManager = linearLayoutManager

        ArticleDbHelper.getOfflineArticles().let {
            if(it.size>0){
                setAdapter(it)
            } else {
                NetworkClient(this,this).execute()
            }
        }
    }

    private fun setAdapter(articles: ArrayList<OfflineArticleData>){
        if(adapter==null){
            adapter = ArticleAdapter(this,articles,this)
            articlesRv.adapter = adapter
        }else{
            adapter?.updateAdapter(articles)
        }
    }

    override fun onArticleClick(article: OfflineArticleData?) {
        val intent = Intent(this@MainActivity,ViewArticleActivity::class.java)
        intent.putExtra(ConstantUtils.KEY_ARTICLE_URL,article?.url)
        startActivity(intent)
    }

    override fun onSuccess(articles : ArrayList<OfflineArticleData>?) {
        articles?.let { setAdapter(it) }
    }

    override fun onFailure() {

    }

    override fun onStart() {
        super.onStart()
        val filterNewActivity = IntentFilter().apply { addAction(ConstantUtils.KEY_UPLOAD_DATA) }
        registerReceiver(updateDataReceiver, filterNewActivity)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(updateDataReceiver)
    }

    fun updateData() {
        GetUpdatedDataTask(this).execute()
    }

    fun startRegularAlarm() {
        if (!AppPreference.getBooleanPreference(StorageConstants.IS_ALARM_SET)) {
            AppPreference.saveBooleanPreference(StorageConstants.IS_ALARM_SET,true)
            startService(Intent(this,UpdateDataService::class.java))
        }
    }

}
