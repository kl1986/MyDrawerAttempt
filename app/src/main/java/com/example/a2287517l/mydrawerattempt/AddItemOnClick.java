package com.example.a2287517l.mydrawerattempt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddItemOnClick extends AppCompatActivity {

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private EditText txtInput;
//added this
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        ListView listView = (ListView)findViewById(R.id.listContainer);
        String[] items = {"Eggs","Bread","Butter"};
        arrayList = new ArrayList<String>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtItem,arrayList);
        listView.setAdapter(adapter);

        txtInput = (EditText)findViewById(R.id.addTextList);
        ImageButton imgBtnAdd = (ImageButton)findViewById(R.id.addTaskBtn);
        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = txtInput.getText().toString();
                arrayList.add(newItem);
                adapter.notifyDataSetChanged();
            }
        });
    }

}