package com.fancysoftwarehouse.voicecommunicationapp.ChatApplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.Chat;
import com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.UserDetails;
import com.fancysoftwarehouse.voicecommunicationapp.ChatApplication.test;
import com.fancysoftwarehouse.voicecommunicationapp.getset.insert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class

    TranslatorBackgroundTask extends AsyncTask<String, Void, String> {
     Context ctx;
    //Set Context
    public TranslatorBackgroundTask(Context ctx){
        this.ctx = ctx;
    }

    String resultString;

    @Override
    protected String doInBackground(String... params) {
        //String variables
        String textToBeTranslated = params[0];
        String languagePair = params[1];

        String jsonString;

        try {

            //Set up the translation call URL
            String yandexKey = "trnsl.1.1.20181022T105246Z.9f9e11d774940bf0.11d361f1960cd12091776d03b04e11c216e86573";
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair;
            URL yandexTranslateURL = new URL(yandexUrl);

            //Set Http Conncection, Input Stream, and Buffered Reader
            HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
            InputStream inputStream = httpJsonConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Set string builder and insert retrieved JSON result into it
            StringBuilder jsonStringBuilder = new StringBuilder();
            while ((jsonString = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(jsonString + "\n");
            }

            //Close and disconnect
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputStream.close();
            httpJsonConnection.disconnect();

            //Making result human readable
            resultString = jsonStringBuilder.toString().trim();

            //Getting the characters between [ and ]

            resultString = resultString.substring(resultString.indexOf('[')+1);
            resultString = resultString.substring(0,resultString.indexOf("]"));

            //Getting the characters between " and "
            resultString = resultString.substring(resultString.indexOf("\"")+1);
            resultString = resultString.substring(0,resultString.indexOf("\""));

            Log.d("Translation Result:", resultString);
            return jsonStringBuilder.toString().trim();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //String text = String.valueOf(resultString);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {

          UserDetails.transltedText=resultString;
      //  Toast.makeText(ctx, resultString, Toast.LENGTH_LONG).show();
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
