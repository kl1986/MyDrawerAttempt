package com.example.a2287517l.mydrawerattempt;

/**
 * Created by kelvi on 28/02/2017.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.view.View;

import java.util.Date;

public class ListDBHandler extends SQLiteOpenHelper {

    private int _id;
    private String _list_name, _cat;
    private boolean _bought;
    private Date _date_added, _date_bought, _date_deleted, _date_reminder;
    ListItem[] items;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "listDB.db";
    public static final String TABLE_LIST = "list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "_item";
    public static final String COLUMN_CAT = "_cat";
    public static final String COLUMN_BOUGHT = "_bought";
    public static final String COLUMN_DATE_ADDED = "_date_added";
    public static final String COLUMN_DATE_BOUGHT = "_date_bought";
    public static final String COLUMN_DATA_REMINDER = "_date_reminder";

    //We need to pass database information along to superclass
    public ListDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, ListItem[] i) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        items = i;
        databaseToString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_LIST + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM + " TEXT, " +
                COLUMN_CAT + " TEXT, " +
                COLUMN_BOUGHT + " BOOLEAN, " +
                COLUMN_DATE_ADDED + " DATE, " +
                COLUMN_DATE_BOUGHT + " DATE, " +
                COLUMN_DATA_REMINDER + " DATE " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);
    }


    //Add a new row to the database
    public void addItem(ListItem item){
        ContentValues values = new ContentValues();
        String name = item.get_item_name();
        values.put(COLUMN_ITEM, name);
        if (name.equals("Milk") || name.equals("Butter") || name.equals("Cheese") || name.equals("Yoghurt")){
            values.put(COLUMN_CAT, "Dairy");
        }
        else if (name.equals("Banana") || name.equals("Tomato") || name.equals("Avocado")){
            values.put(COLUMN_CAT, "Fruit and Veg");
        }
        else if (name.equals("Chips") || name.equals("Ice") || name.equals("Peas")){
            values.put(COLUMN_CAT, "Frozen");
        }
        else {
            values.put(COLUMN_CAT, "none");
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProduct(String itemName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LIST + " WHERE " + COLUMN_ITEM + "=\"" + itemName + "\";");
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LIST + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        int count = 0;
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("_item")) != null) {
                dbString += c.getString(c.getColumnIndex("_item"));
                dbString += ":";
                dbString += c.getString(c.getColumnIndex("_cat"));
                dbString += "\n";
                items[count] = new ListItem(c.getString(c.getColumnIndex("_item")));
                items[count].set_id(Integer.parseInt(c.getString(c.getColumnIndex("_id"))));
                items[count].set_cat(c.getString(c.getColumnIndex("_cat")));
                items[count].set_bought(Boolean.parseBoolean(c.getString(c.getColumnIndex("_bought"))));
                count++;
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}