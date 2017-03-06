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
 * Created by Kelvin on 28/02/17.
 */

public class AddItemPage extends Fragment {

    View myView;

    EditText itemInput;
    TextView listText;
    ListDBHandler listDBHandler;
    Button mAddButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_layout, container, false);
        listDBHandler = new ListDBHandler(getActivity(), null, null, 1);
        itemInput = (EditText) myView.findViewById(R.id.itemInput);
        listText = (TextView) myView.findViewById(R.id.listText);
        printDatabase();


        mAddButton = (Button) myView.findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItem item = new ListItem(itemInput.getText().toString());
                listDBHandler.addItem(item);
                printDatabase();
            }
        });
        return myView;
    }

    //Add a product to the database
    public void addButtonClicked(View view) {
        ListItem item = new ListItem(itemInput.getText().toString());
        listDBHandler.addItem(item);
        printDatabase();
    }



    //Print the database
    public void printDatabase() {
        String dbString = listDBHandler.databaseToString();
        listText.setText(dbString);
        itemInput.setText("");
    }
}
