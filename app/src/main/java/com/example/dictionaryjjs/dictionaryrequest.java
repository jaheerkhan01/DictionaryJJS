package com.example.dictionaryjjs;

//add dependencies to your class
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dictionaryrequest extends AsyncTask<String,Integer,String> {
    Context context;
    TextView showDef;

        dictionaryrequest(Context context,TextView textView) {
            this.context=context;
            showDef=textView;
    }


    @Override
    protected String doInBackground(String... params) {

        //TODO: replace with your own app id and app key
        final String app_id = "a5d2b2d3";
        final String app_key = "1645c3ef60de1d78e154c49dc3289199";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String def;
        try {
//            Toast.makeText(context, "Running req" + String.valueOf(result), Toast.LENGTH_SHORT).show();
            Log.e("Data",result);
            JSONObject js = new JSONObject(result);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject de = sensesArray.getJSONObject(0);
            JSONArray d = de.getJSONArray("definitions");

            def = d.getString(0);
//            Toast.makeText(context, def, Toast.LENGTH_SHORT).show();
            showDef.setText(def);
        } catch (JSONException e) {
            e.printStackTrace();
//            Toast.makeText(context, "Error" + e, Toast.LENGTH_SHORT).show()LENGTH_SHORT;
            Log.e("Req error:", String.valueOf(e));


        }
//        Toast.makeText(context, "result", Toast.LENGTH_SHORT).show();

    }
}