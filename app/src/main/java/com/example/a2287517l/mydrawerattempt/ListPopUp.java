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
    String[] listItemNames = new String[10];

    ArrayList<String> list = new ArrayList<String>();
    @Override
    @NonNull
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        final String[] items = getResources().getStringArray(R.array.near_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Near You").setMultiChoiceItems(listItemNames, null, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    list.add(items[which]);
                }
                else if (list.contains(items[which])){
                    list.remove(items[which]);
                }
            }

        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selections = "";
                for (String ms: list) {
                    selections = selections + ", " + ms;
                }
                Toast.makeText(getActivity(), "near list:" + selections, Toast.LENGTH_SHORT).show();
            }
        });


        return builder.create();
    }

    public void setList(ListItem[] i) {
        for (int x = 0; x < i.length -1; x++){
            if (i[x] == null) {
                break;
            }
            listItemNames[x] = i[x].get_item_name();
        }
    }

    public ArrayList<String> getList() {
        return list;
    }

    public ListPopUp() {
        super();
    }
}
