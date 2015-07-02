package com.example.android.effectivenavigation.fragment;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.activity.MyCounter;
import com.example.android.effectivenavigation.model.Toilet;

import java.util.ArrayList;

/**
 * Created by usr0200475 on 15/06/29.
 */
public class ToiletBooking extends ListFragment implements DialogListener{

    ArrayList<Toilet> listToilets = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            for ( int i = 0; i<10; i++) {
                Toilet toilet = new Toilet();
                toilet.setName("Toilet" + i);
                toilet.setNumber(Integer.toString(i));
                toilet.setStatus(false);
                listToilets.add(toilet);
            }

        ArrayAdapter<Toilet> adapter = new ArrayAdapter<>( getActivity(), R.layout.fragment_list_toilet_not_use,R.id.text1, listToilets);
        setListAdapter(adapter);
    }

    public void showDialog(String title, String message, int type){
        BookingDialog newFragment = BookingDialog.newInstance(title, message, type);
        // リスナーセット
        newFragment.setDialogListener(this);
        // ここでCancelable(false)をしないと効果が無い
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
