package com.example.android.toiletbooking.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.activity.WaitingFormActivity;
import com.example.android.toiletbooking.activity.MyCounter;
import com.example.android.toiletbooking.model.GridViewAdapter;
import com.example.android.toiletbooking.model.GridViewItem;
import com.example.android.toiletbooking.model.Toilet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by usr0200475 on 15/06/29.
 */
public class ListToilets extends Fragment implements DialogListener,AdapterView.OnItemClickListener{

    ArrayList<Toilet> listToilets = new ArrayList<>();
    TextView textView;
    private List<GridViewItem> mItems;
    private GridViewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the items list
        mItems = new ArrayList<GridViewItem>();
        Resources resources = getResources();
        for (int i = 1; i <= 10; i++) {
            mItems.add(new GridViewItem(resources.getDrawable(R.drawable.ic_floor), i+"階"));
            for (int j = 1; j <= 3; j++) {
                Toilet toilet = new Toilet();
                toilet.setName("WC" + j);
                toilet.setNumber(Integer.toString(j));
                toilet.setFloor(i);
                toilet.setStatus(getRandomBoolean());
                if (toilet.isStatus()) {
                    toilet.setWaiting(2);
                } else toilet.setWaiting(0);
                listToilets.add(toilet);
                String title = toilet.getName() + "-" + toilet.getWaiting() + "人待ち";
                mItems.add(new GridViewItem(resources.getDrawable(R.drawable.ic_toilet), title));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the root view of the fragment
        View fragmentView = inflater.inflate(R.layout.fragment_list_toilet, container, false);

        // initialize the adapter
        mAdapter = new GridViewAdapter(getActivity(), mItems,listToilets);

        // initialize the GridView
        GridView gridView = (GridView) fragmentView.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);

        return fragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GridViewItem item = mItems.get(position);
      //  int pos = position;
        int toiletPositionNumber = position - position/4  -1;
        int check = position%4;
        Resources resources = getResources();
        //Floorの位置の場合
        switch (check) {
            case (0): {
                break;
            }
            default: {
                switch (listToilets.get(toiletPositionNumber).getWaiting()) {
                    case (0):{
                        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
                       // listToilets.get(toiletPositionNumber).setStatus(true);
                       // mItems.set(position, (new GridViewItem(resources.getDrawable(R.drawable.ic_toilet_using), listToilets.get(toiletPositionNumber).toString())));
                        listToilets.get(toiletPositionNumber).setStatus(true);
                        listToilets.get(toiletPositionNumber).setWaiting(listToilets.get(toiletPositionNumber).getWaiting() + 1);
                        Intent intent = new Intent(getActivity(), MyCounter.class);
                        startActivity(intent);
                        // showDialog("確認画面", "予約でよろしいですか？", 1);
                        break;
                     }
                    default:{
                        Intent intent = new Intent(getActivity(), WaitingFormActivity.class);
                        Toilet sendData = listToilets.get(toiletPositionNumber);
                        intent.putExtra("send", sendData);
                        startActivity(intent);
                        Log.d(getTag(), "onListItemClick position => " + position + " : id => " + id);
                    }
                 }
            }
        }
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

}
