package com.example.android.toiletbooking.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.activity.WaitingActivity;
import com.example.android.toiletbooking.activity.WaitingFormActivity;
import com.example.android.toiletbooking.activity.MyCounter;
import com.example.android.toiletbooking.model.GridViewAdapter;
import com.example.android.toiletbooking.model.GridViewItem;
import com.example.android.toiletbooking.model.Toilet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

    String url = "http://192.168.1.2/return_toilet_json.php";
    RequestQueue queue = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<GridViewItem>();

        queue = Volley.newRequestQueue(getActivity());

        //非同期 json request
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, (JSONArray)null,
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

                                Log.v("test", toiletObject.toString());

                                String toilet_id = toiletObject.getString("toire_id");

                                String toilet_name = toiletObject.getString("toire_name");

                                String toilet_status = toiletObject.getString("toire_status");

                                String toilet_human = toiletObject.getString("toire_human");

                                Toilet toilet = new Toilet();

                                toilet.setName(toilet_name);

                                toilet.setNumber(toilet_id);

                                if (Integer.parseInt(toilet_status) == 0) {
                                    toilet.setStatus(false);
                                } else toilet.setStatus(true);

                                toilet.setWaiting(Integer.parseInt(toilet_human));

                                listToilets.add(toilet);
                                    // Log.v("test", listToilets.toString());
                                String waiting = Integer.toString(toilet.getWaiting());
                                String title = toilet.getName();

                                if (toilet.isStatus()) {
                                    mItems.add(new GridViewItem(resources.getDrawable(R.drawable.ic_toilet_active), title, waiting));
                                } else {
                                    mItems.add(new GridViewItem(resources.getDrawable(R.drawable.ic_toilet_passive), title, waiting));
                                }
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
        // inflate the root view of the fragment
        View fragmentView = inflater.inflate(R.layout.fragment_list_toilet, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipelayout);
        // initialize the adapter
        mAdapter = new GridViewAdapter(getActivity(), mItems,listToilets);
        // initialize the GridView
        GridView gridView = (GridView) fragmentView.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);

        // Listenerをセット
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return fragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GridViewItem item = mItems.get(position);
      //  int pos = position;
        int toiletPositionNumber = position;
        int check = position%4;
        int thisWaiting = listToilets.get(toiletPositionNumber).getWaiting();
        String title = listToilets.get(toiletPositionNumber).toString();
        Resources resources = getResources();
        //Floorの位置の場合

        switch (listToilets.get(toiletPositionNumber).getWaiting()) {
            case (0):{
                Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
                String waiting = Integer.toString(thisWaiting + 1);
                mItems.set(position, (new GridViewItem(resources.getDrawable(R.drawable.ic_toilet_active), title, waiting)));
                listToilets.get(toiletPositionNumber).setStatus(true);
                Intent intent = new Intent(getActivity(), MyCounter.class);
                startActivity(intent);
                break;
            }
            default:{
                listToilets.get(toiletPositionNumber).setWaiting(thisWaiting + 1);
                String waiting = Integer.toString(thisWaiting + 1);
                Intent intent = new Intent(getActivity(), WaitingActivity.class);
                Toilet sendData = listToilets.get(toiletPositionNumber);
                mItems.set(position, (new GridViewItem(resources.getDrawable(R.drawable.ic_toilet_active), title, waiting)));
                intent.putExtra("send", sendData);
                startActivity(intent);
                Log.d(getTag(), "onListItemClick position => " + position + " : id => " + id);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        // 更新処理を実装する
        // ここでは単純に2秒後にインジケータ非表示
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public void showDialog(String title, String message, int type){
        ConfirmDialog newFragment = ConfirmDialog.newInstance(title, message, type);
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

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public String convertStandardJSONString(String data_json){
        data_json = data_json.replace("\\", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }
}
