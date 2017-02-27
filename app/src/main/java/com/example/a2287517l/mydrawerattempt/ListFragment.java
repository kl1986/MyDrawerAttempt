package com.example.a2287517l.mydrawerattempt;

import android.app.Fragment;
import android.bluetooth.le.ScanSettings;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 2287517l on 27/02/2017.
 */
public class ListFragment extends Fragment {

    View myView;
    TextView rssi;

    // request ID for enabling Bluetooth
    private static final int REQUEST_ENABLE_BT = 1000;
    private boolean isScanning = false;
    private ScanResultArrayAdapter scanAdapter = null;
    private int scanMode = ScanSettings.SCAN_MODE_BALANCED;

    private int lastx = 0;
    // currently selected beacon, if any
    private BeaconInfo selectedBeacon = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_layout, container, false);
        super.onCreate(savedInstanceState);


        return myView;


    }

    // simple class to hold data about a beacon
    private class BeaconInfo {
        public BluetoothDevice device;
        public String address;
        public String name;
        public int rssi;

        private static final int WINDOW_SIZE = 9;
        private int[] window = new int[WINDOW_SIZE];
        private int windowptr = 0;

        public BeaconInfo(BluetoothDevice device, String address, String name, int rssi) {
            this.device = device;
            this.address = address;
            this.name = name;
            this.rssi = rssi;
            for (int i = 0; i < WINDOW_SIZE; i++)
                this.window[i] = rssi;
        }

        // called when a new scan result for this beacon is parsed
        public void updateRssi(int newRssi) {
            this.rssi = newRssi;
            window[windowptr] = newRssi;
            windowptr = (windowptr + 1) % WINDOW_SIZE;
        }

        // returns the latest raw RSSI reading for this beacon
        public double getRssi() {
            return this.rssi;
        }

        // returns a very simple moving average of the last WINDOW_SIZE
        // RSSI values received for this beacon
        public double getFilteredRssi() {
            double mean = 0.0;
            for (int i = 0; i < WINDOW_SIZE; i++) {
                mean += window[i];
            }
            mean /= WINDOW_SIZE;

            //View row = inflater.inflate(R.layout.list_layout, parent, false);
            rssi = (TextView) findViewById(R.id.rssi_field);
            rssi.setText(Double.toString(mean));

            return mean;
        }
    }

        public class ScanResultArrayAdapter extends BaseAdapter {

            private final Context context;
            private final HashMap<String, BeaconInfo> data;
            private final List<String> keys;

            public ScanResultArrayAdapter(Context context) {
                super();
                this.context = context;
                this.keys = new ArrayList<>();
                this.data = new HashMap<>();
            }

            public void clear() {
                data.clear();
                keys.clear();
                notifyDataSetChanged();
            }

            @Override
            public BeaconInfo getItem(int position) {
                return data.get(keys.get(position));
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public int getCount() {
                return data.size();
            }

            // updates the dataset with a new scan result. may create a new BeaconInfo object or update
            // an existing one.
            public void update(BluetoothDevice beaconDevice, String beaconAddress, String beaconName, int beaconRssi) {
                if(data.containsKey(beaconAddress)) {
                    data.get(beaconAddress).updateRssi(beaconRssi);
                } else {
                    BeaconInfo info = new BeaconInfo(beaconDevice, beaconAddress, beaconName, beaconRssi);
                    data.put(beaconAddress, info);
                    keys.add(beaconAddress);
                }
                notifyDataSetChanged();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row;
                if(convertView == null) {
                    //row = inflater.inflate(R.layout.layout_scanresult, parent, false);
                } else {
                    row = convertView;
                }

                // manually set the contents of each of the labels
                //TextView field1 = (TextView) row.findViewById(R.id.resultField1);
                //TextView field2 = (TextView) row.findViewById(R.id.resultField2);
                BeaconInfo info = data.get(keys.get(position));
                //field1.setText(info.name + " [" + info.rssi + " dBm]");
                //field2.setText(info.address);

                // if this happens to be the selected beacon, change the background colour to highlight it
                //if(selectedBeacon != null && info.equals(selectedBeacon))
                    //row.setBackgroundColor(Color.argb(64, 0, 255, 0));

                //else
                    //row.setBackgroundColor(Color.argb(255, 255, 255, 255));

                return convertView;
            }
        }
}
