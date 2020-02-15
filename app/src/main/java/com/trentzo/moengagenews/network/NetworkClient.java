package com.trentzo.moengagenews.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.trentzo.moengagenews.R;
import com.trentzo.moengagenews.beans.ArticleCollectionData;
import com.trentzo.moengagenews.beans.OfflineArticleData;
import com.trentzo.moengagenews.storage.sqllite.ArticleDbHelper;
import com.trentzo.moengagenews.utils.ConstantUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class NetworkClient extends AsyncTask<Void,Void,Void> {

    private String getDataUrl = ConstantUtils.KEY_GET_DATA_API;
    private CallBack callBack;
    private String server_response;
    private Context context;

    public NetworkClient(Context context , CallBack callBack) {
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            InputStream is = context.getResources().openRawResource(R.raw.samplejson);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            String jsonString = writer.toString();

            Gson gson = new Gson();
            ArticleCollectionData articleCollectionData = gson.fromJson(jsonString,ArticleCollectionData.class);
            if(articleCollectionData.getArticles()!=null) {
                for (int i = 0; i < articleCollectionData.getArticles().size(); i++) {
                    ArticleDbHelper.INSTANCE.insertArticle(articleCollectionData.getArticles().get(i));
                }
            }
            ArrayList<OfflineArticleData> offlineArticleDataArrayList = ArticleDbHelper.INSTANCE.getOfflineArticles();

            Log.i("response",offlineArticleDataArrayList.toString());

            if(callBack!=null){
                callBack.onSuccess(offlineArticleDataArrayList);
            }


        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailure();
        }

        return null;
    }


    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
