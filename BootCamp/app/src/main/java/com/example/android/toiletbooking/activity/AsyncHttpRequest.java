package com.example.android.toiletbooking.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.android.toiletbooking.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by usr0200475 on 15/07/08.
 */
public class AsyncHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

    private Activity mainActivity;
    String toire_toire_id = null;
    String user_session ;

    public AsyncHttpRequest(Activity activity) {
        this.mainActivity = activity;
    }

    @Override
    protected String doInBackground(Uri.Builder... builder) {
        HttpClient httpclient = new DefaultHttpClient();
        String post_url = "http://192.168.1.5/insert_wait_human.php";
        HttpPost httppost = new HttpPost(post_url);
        List<NameValuePair> sendData = new ArrayList<NameValuePair>(1);

        try {
            sendData.add(new BasicNameValuePair("toire_toire_id", toire_toire_id));
            Random random = new Random();
            user_session = random.toString();
            sendData.add(new BasicNameValuePair("user_sesion", user_session));
            httppost.setEntity(new UrlEncodedFormEntity(sendData));
            httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return sendData.toString();
    }

}