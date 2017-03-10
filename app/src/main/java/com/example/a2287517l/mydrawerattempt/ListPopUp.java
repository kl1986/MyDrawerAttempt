package com.example.a2287517l.mydrawerattempt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kelvi on 08/03/2017.
 */

public class ListPopUp extends DialogFragment {

    public ListItem[] itemList;
    String[] listItemNames; // = new String[10];
    ListItem[] items = new ListItem[10];
    ListDBHandler listDBHandler;

    ArrayList<String> list = new ArrayList<String>();
    @Override
    @NonNull
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        listDBHandler = new ListDBHandler(getActivity(), null, null, 1, items);
        builder.setTitle("Near You").setMultiChoiceItems(listItemNames, null, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    list.add(listItemNames[which]);
                }
                else if (list.contains(listItemNames[which])){
                    list.remove(listItemNames[which]);
                }
            }

        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selections = "";
                for (String ms: list) {

                    for (int i = 0; i< items.length; i++ ) {
                        if (items[i] != null) {
                            //Toast.makeText(getActivity(), "data" + items[i].get_item_name(), Toast.LENGTH_SHORT).show();
                            if (items[i].get_item_name().equals(ms)) {
                                //Toast.makeText(getActivity(), "I get here", Toast.LENGTH_SHORT).show();
                                listDBHandler.deleteProduct(items[i].get_item_name());
                            }
                        }
                    }
                    selections = selections + ", " + ms;
                }
                Toast.makeText(getActivity(), "Deleted from List:" + selections, Toast.LENGTH_SHORT).show();
            }
        });


        return builder.create();
    }

    public void setList(ListItem[] t) {
        items = t;
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                count++;
            }
        }

        listItemNames = new String[count];

        for (int i =0; i < listItemNames.length; i++){
            listItemNames[i] = "";
        }

        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                listItemNames[i] = items[i].get_item_name();
            }
        }
    }

    public ArrayList<String> getList() {
        return list;
    }

    public ListPopUp() {
        super();
    }
}
