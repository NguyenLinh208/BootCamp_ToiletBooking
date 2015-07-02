package com.example.android.toiletbooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.activity.BookForm;
import com.example.android.toiletbooking.model.Toilet;

import java.util.ArrayList;

/**
 * Created by usr0200475 on 15/06/29.
 */
public class InUsingToilets extends ListFragment {

    ArrayList<Toilet> listToilets = new ArrayList<>();
    String statusFromIputActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            for ( int i = 0; i<10; i++) {
                Toilet toilet = new Toilet();
                toilet.setName("Toilet" + i);
                toilet.setNumber(Integer.toString(i));
                toilet.setStatus(true);
                listToilets.add(toilet);
            }

        ArrayAdapter<Toilet> adapter = new ArrayAdapter<>( getActivity(), R.layout.fragment_list_toilet_using,R.id.text1, listToilets);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(getActivity(), BookForm.class);
        String status;
        if (listToilets.get(position).isStatus()){
            status = "使用中";
        }
        else status =  "使用できます";

        String sendData = listToilets.get(position).getName() + " " + status + "　？人待ち中";
        intent.putExtra("send",sendData);
        startActivity(intent);
        Log.d(getTag(), "onListItemClick position => " + position + " : id => " + id);

    }


}
