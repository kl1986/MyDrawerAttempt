package com.example.a2287517l.mydrawerattempt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;

/**
 * Created by steph on 27/02/17.
 */

public class AddItemPage extends Fragment {
    ArrayAdapter<String> m_adapter;
    ArrayList<String> m_listItems = new ArrayList<String>();

    View myView;
    EditText item;
    EditText quantity;
    TextView itemView;
    TextView quantityView;
    ListView viewItems;
    Button save;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_item_page, container, false);
        return myView;
    }
}
