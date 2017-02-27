package com.example.a2287517l.mydrawerattempt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by steph on 27/02/17.
 */

public class AddItemPage extends Fragment {
    View myView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_item_page, container, false);
        return myView;
    }

}
