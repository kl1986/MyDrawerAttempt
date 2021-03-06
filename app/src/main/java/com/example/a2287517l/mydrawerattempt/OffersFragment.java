package com.example.a2287517l.mydrawerattempt;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 2287517l on 27/02/2017.
 */
public class OffersFragment extends Fragment {

    View myView;
    ListDBHandler listDBHandler;
    ListItem[] items = new ListItem[10];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.offers_layout, container, false);

        TextView offer1 = (TextView) myView.findViewById(R.id.Offer1);
        TextView offer2 = (TextView) myView.findViewById(R.id.Offer2);
        listDBHandler = new ListDBHandler(getActivity(), null, null, 1, items);

        if (listDBHandler.checkItemExists("Steak")) {
            offer1.setText("OFFER ON STEAK AT TESCO - 30% OFF!");
        };
        if (listDBHandler.checkItemExists("Chips")) {
            offer2.setText("OFFER ON CHIPS AT SAINSBURY'S - BUY 1 GET 1 FREE!");
        };

        return myView;
    }
}
