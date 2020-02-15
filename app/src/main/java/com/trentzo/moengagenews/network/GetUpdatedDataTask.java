package com.trentzo.moengagenews.network;

import android.os.AsyncTask;
import android.util.Log;

import com.trentzo.moengagenews.beans.OfflineArticleData;
import com.trentzo.moengagenews.storage.sqllite.ArticleDbHelper;

import java.util.ArrayList;

public class GetUpdatedDataTask extends AsyncTask<Void,Void,Void> {

    private CallBack callBack;

    public GetUpdatedDataTask(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected Void doInBackground(Void... voids) {


        ArrayList<OfflineArticleData> offlineArticleDataArrayList = ArticleDbHelper.INSTANCE.getOfflineArticles();

        Log.i("response",offlineArticleDataArrayList.toString());

        if(callBack!=null){
            callBack.onSuccess(offlineArticleDataArrayList);
        }


        return null;
    }
}
