package com.example.a2287517l.mydrawerattempt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by kelvi on 08/03/2017.
 */

public class AddItemPage extends Fragment {

    View myView;
    EditText itemInput;
    TextView listText;
    ListDBHandler listDBHandler;
    ImageButton mAddButton;
    ListItem[] items = new ListItem[100];



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_layout, container, false);
        listDBHandler = new ListDBHandler(getActivity(), null, null, 1, items);
        itemInput = (EditText) myView.findViewById(R.id.addToList);
        listText = (TextView) myView.findViewById(R.id.listText);
        printDatabase();

        mAddButton = (ImageButton) myView.findViewById(R.id.addListBtn);
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
