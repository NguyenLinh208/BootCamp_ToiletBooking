package com.example.android.toiletbooking.fragment;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.activity.MyCounter;
import com.example.android.toiletbooking.model.Toilet;

import java.util.ArrayList;

/**
 * Created by usr0200475 on 15/06/29.
 */
public class ToiletBooking extends ListFragment implements DialogListener{

    ArrayList<Toilet> listToilets = new ArrayList<>();
    TextView textView;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_list_toilet_not_use, container, false);
//        textView = (TextView)view.findViewById(R.id.status_text);
//        return view;
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 3; j++) {
                    Toilet toilet = new Toilet();
                    toilet.setName("Toilet" + j);
                    toilet.setNumber(Integer.toString(j));
                    toilet.setFloor(i + "階");
                    toilet.setStatus(false);
//                    if (!toilet.isStatus()) textView.setText("使用可能");
                    listToilets.add(toilet);
                }
            }

            ArrayAdapter<Toilet> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.fragment_list_toilet_not_use, R.id.text1, listToilets);
            setListAdapter(adapter1);

    }

    public void showDialog(String title, String message, int type){
        BookingDialog newFragment = BookingDialog.newInstance(title, message, type);
        // リスナーセット
        newFragment.setDialogListener(this);
        newFragment.setCancelable(false);
        newFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDialog("確認画面","予約でよろしいですか？",1);
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
