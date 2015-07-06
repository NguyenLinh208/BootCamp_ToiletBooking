package com.example.android.toiletbooking.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.activity.BookForm;
import com.example.android.toiletbooking.activity.MyCounter;
import com.example.android.toiletbooking.model.GridViewAdapter;
import com.example.android.toiletbooking.model.GridViewItem;
import com.example.android.toiletbooking.model.Toilet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by usr0200475 on 15/06/29.
 */
public class WomenToilets extends Fragment implements DialogListener,AdapterView.OnItemClickListener{

    ArrayList<Toilet> listToilets = new ArrayList<>();
    TextView textView;
    private List<GridViewItem> mItems;    // GridView items list
    private GridViewAdapter mAdapter;    // GridView adapter



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
                toilet.setName("Toilet" + j);
                toilet.setNumber(Integer.toString(j));
                toilet.setFloor(i);
                toilet.setStatus(false);
                toilet.setWaiting(0);

//                toilet.setStatus(true);
//                toilet.setWaiting(5);
                listToilets.add(toilet);
                if (toilet.getWaiting() == 0) {
                    mItems.add(new GridViewItem(resources.getDrawable(R.drawable.ic_toilet), toilet.toString()));
                } else {
                    mItems.add(new GridViewItem(resources.getDrawable(R.drawable.ic_toilet), toilet.toString()));
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the root view of the fragment
        View fragmentView = inflater.inflate(R.layout.fragment_list_toilet_not_use, container, false);

        // initialize the adapter
        mAdapter = new GridViewAdapter(getActivity(), mItems);

        // initialize the GridView
        GridView gridView = (GridView) fragmentView.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);

        return fragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // retrieve the GridView item
        GridViewItem item = mItems.get(position);
        int pos = position;
        int toiletPositionNumber = pos - pos/4  -1;
        int check = pos%4;
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
                        listToilets.get(toiletPositionNumber).setStatus(true);
                        mItems.set(position,(new GridViewItem(resources.getDrawable(R.drawable.ic_toilet), listToilets.get(toiletPositionNumber).toString())));
                        view.setBackgroundColor(Color.GRAY);
                        //Counterを起動する
                        Intent intent = new Intent(getActivity(), MyCounter.class);
                        startActivity(intent);
                        // showDialog("確認画面", "予約でよろしいですか？", 1);
                        break;
                     }
                    default:{
                        Intent intent = new Intent(getActivity(), BookForm.class);
                        String status;
                        if (listToilets.get(toiletPositionNumber).isStatus()) {
                            status = "使用中";
                        } else status = "使用できます";

                        String sendData = listToilets.get(toiletPositionNumber).getName() + " " + status + listToilets.get(toiletPositionNumber).getWaiting() + "人待ち中";
                        intent.putExtra("send", sendData);
                        startActivity(intent);
                        Log.d(getTag(), "onListItemClick position => " + position + " : id => " + id);
                    }
                 }
            }
        }
    }

    public void showDialog(String title, String message, int type){
        BookingDialog newFragment = BookingDialog.newInstance(title, message, type);
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


}
