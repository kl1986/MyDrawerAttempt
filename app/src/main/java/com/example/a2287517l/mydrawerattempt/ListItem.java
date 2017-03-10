package com.example.a2287517l.mydrawerattempt;

import java.util.Date;

/**
 * Created by kelvi on 28/02/2017.
 */

//Each row in the database can be represented by an object
//Columns will represent the objects properties


public class ListItem {

    private int _id;
    private String _item_name, _cat;
    private boolean _bought;
    private Date _date_added, _date_bought, _date_deleted, _date_reminder;

//    public  ListItem(){
//    }

    public  ListItem(String _item_name){
        this._item_name = _item_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_item_name() {
        return _item_name;
    }

    public void set_item_name(String _item_name) {
        this._item_name = _item_name;
    }

    public String get_cat() {
        return _cat;
    }

    public void set_cat(String _cat) {
        this._cat = _cat;
    }

    public boolean is_bought() {
        return _bought;
    }

    public void set_bought(boolean _bought) {
        this._bought = _bought;
    }

    public Date get_date_added() {
        return _date_added;
    }

    public void set_date_added(Date _date_added) {
        this._date_added = _date_added;
    }

    public Date get_date_bought() {
        return _date_bought;
    }

    public void set_date_bought(Date _date_bought) {
        this._date_bought = _date_bought;
    }

    public Date get_date_deleted() {
        return _date_deleted;
    }

    public void set_date_deleted(Date _date_deleted) {
        this._date_deleted = _date_deleted;
    }

    public Date get_date_reminder() {
        return _date_reminder;
    }

    public void set_date_reminder(Date _date_reminder) {
        this._date_reminder = _date_reminder;
    }



}