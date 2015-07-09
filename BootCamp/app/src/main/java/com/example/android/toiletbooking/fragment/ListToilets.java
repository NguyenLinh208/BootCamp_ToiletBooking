package com.example.android.toiletbooking.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.activity.WaitingActivity;
import com.example.android.toiletbooking.activity.MyCounter;
import com.example.android.toiletbooking.model.GridViewAdapter;
import com.example.android.toiletbooking.model.GridViewItem;
import com.example.android.toiletbooking.model.Toilet;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by usr0200475 on 15/06/29.
 */
public class ListToilets extends Fragment implements DialogListener,AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    public ArrayList <Toilet> listToilets = new ArrayList<>();
    TextView textView;
    private List<GridViewItem> mItems;
    private GridViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    String get_url = "http://192.168.1.5/return_toilet_json.php";
    String insertWaitHumanUrl = "http://192.168.1.5/insert_wait_human.php";
    String removeHumanUrl = "http://192.168.1.5/humanremove.php";
    String humanAddUrl = "http://192.168.1.5/humanadd.php";
    String deleteHumanUrl = "http://192.168.1.5/delete_wait_human.php";
    RequestQueue queue = null;

    String cancelFromMyCounter = null ;

    String cancelFromWaiting = null;

    String finishCounter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<GridViewItem>();

        queue = Volley.newRequestQueue(getActivity());

        //非同期 json request
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, get_url, (JSONArray)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Resources resources = getResources();
                        String json = jsonArray.toString();
                        convertStandardJSONString(json);
                        try {
                            JSONArray array = new JSONArray(json);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject toiletObject = array.getJSONObject(i);

                                //Log.v("test", toiletObject.toString());

                                String toilet_id = toiletObject.getString("toire_id");

                                String toilet_name = toiletObject.getString("toire_name");

                                String toilet_status = toiletObject.getString("toire_status");

                                String toilet_human = toiletObject.getString("toire_human");

                                Toilet toilet = new Toilet();

                                toilet.setName(toilet_name);

                                toilet.setNumber(toilet_id);

                                toilet.setWaiting(Integer.parseInt(toilet_human));

                                if (toilet.getWaiting()>0) toilet.setStatus(true);

                                if (Integer.parseInt(toilet_status) == 0) {
                                    toilet.setStatus(false);
                                } else toilet.setStatus(true);

                                Random random = new Random();
                                toilet.setSession(random.toString());

                                toilet.setWaiting(Integer.parseInt(toilet_human));

                                listToilets.add(toilet);
                                Log.v("test", listToilets.toString());

                                String waiting = Integer.toString(toilet.getWaiting());

                                String title = toilet.getName();

                                if (toilet.getWaiting() > 0) {
                                    mItems.add(new GridViewItem(resources.getDrawable(R.drawable.restroom_active), title, waiting));
                                } else {
                                    mItems.add(new GridViewItem(resources.getDrawable(R.drawable.restroom), title, waiting));
                                }
                              //  mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.e("volley", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Volley", volleyError.getMessage());
                        volleyError.printStackTrace();
                    }
                }
        );

        queue.add(jsonRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            cancelFromMyCounter = getArguments().getString("cancelFromCounter");

            cancelFromWaiting = getArguments().getString("cancelFromWaiting");

            finishCounter = getArguments().getString("finishCounter");
        }

//        Log.d("cancel",cancelFromMyCounter);
        View fragmentView = inflater.inflate(R.layout.fragment_list_toilet, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipelayout);
        mAdapter = new GridViewAdapter(getActivity(), mItems,listToilets);
        GridView gridView = (GridView) fragmentView.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.notifyDataSetChanged();
        return fragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GridViewItem item = mItems.get(position);

        int toiletPositionNumber = position;

        int thisWaiting = listToilets.get(toiletPositionNumber).getWaiting();
        String title = listToilets.get(toiletPositionNumber).toString();
        Resources resources = getResources();

        switch (listToilets.get(toiletPositionNumber).getWaiting()) {
            case (0):{
                Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
                String waiting = Integer.toString(thisWaiting + 1);
                Drawable image = resources.getDrawable(R.drawable.restroom);
                mItems.set(position, (new GridViewItem(resources.getDrawable(R.drawable.restroom_active), title, waiting)));
                listToilets.get(toiletPositionNumber).setStatus(true);
                Intent intent = new Intent(getActivity(), MyCounter.class);
                startActivity(intent);
                if (cancelFromMyCounter == "FromMyCounter") {
                    sendPostRequest(deleteHumanUrl,null,listToilets.get(toiletPositionNumber).getSession());
                }
                break;
            }
            default:{
                listToilets.get(toiletPositionNumber).setWaiting(thisWaiting + 1);
                String waiting = Integer.toString(thisWaiting + 1);
                Intent intent = new Intent(getActivity(), WaitingActivity.class);
                Toilet sendData = listToilets.get(toiletPositionNumber);
                mItems.set(position, (new GridViewItem(resources.getDrawable(R.drawable.restroom_active), title, waiting)));
                intent.putExtra("send", sendData);
                startActivity(intent);
                if (cancelFromWaiting == "FromWaiting") {
                    sendPostRequest(deleteHumanUrl,null,listToilets.get(toiletPositionNumber).getSession());
                    Log.v("cancel", cancelFromWaiting);
                }

                Log.d(getTag(), "onListItemClick position => " + position + " : id => " + id);
            }
        }

        sendPostRequest(humanAddUrl,listToilets.get(toiletPositionNumber).getNumber(),listToilets.get(toiletPositionNumber).getSession());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

        // ここでは単純に2秒後にインジケータ非表示
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    public void showDialog(String title, String message){
        CounterDialog newFragment = CounterDialog.newInstance(title, message);
        // リスナーセット
        newFragment.setDialogListener(this);
        newFragment.setCancelable(false);
        newFragment.show(getFragmentManager(), "dialog");
    }

    /**
     * OKボタンをおした時
     */
    @Override
    public void doPositiveClick() {
        Intent intent = new Intent(getActivity(), MyCounter.class);
        startActivity(intent);
    }

    /**
     * NGボタンをおした時
     */
    @Override
    public void doNegativeClick() {

    }

    public String convertStandardJSONString(String data_json){
        data_json = data_json.replace("\\", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }

    private void sendPostRequest(final String url ,final String toire_toire_id, final String user_session) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String paramToireId = params[0];
                String paramUserSession = params[1];


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                BasicNameValuePair toire_idValuePair = new BasicNameValuePair("toire_toire_id", paramToireId);
                BasicNameValuePair user_sessionValuePAir = new BasicNameValuePair("user_session", paramUserSession);

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(toire_idValuePair);
                nameValuePairList.add(user_sessionValuePAir);

                try {

                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder stringBuilder = new StringBuilder();

                        String bufferedStrChunk = null;

                        while((bufferedStrChunk = bufferedReader.readLine()) != null){
                            stringBuilder.append(bufferedStrChunk);
                        }

                        return stringBuilder.toString();

                    } catch (ClientProtocolException cpe) {
                        System.out.println("First Exception caz of HttpResponese :" + cpe);
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        System.out.println("Second Exception caz of HttpResponse :" + ioe);
                        ioe.printStackTrace();
                    }

                } catch (UnsupportedEncodingException uee) {
                    System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                    uee.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("working")){
                    //Toast.makeText(getActivity(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
                }else{
                   // Toast.makeText(getActivity(), "Invalid POST req...", Toast.LENGTH_LONG).show();
                }
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(toire_toire_id, user_session);
    }

    @Override
    public void onPause() {
        super.onPause();
        queue.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        queue.start();
    }

}
