package com.example.a2287517l.mydrawerattempt;

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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ViewList extends Fragment {
    View myView;

    EditText itemInput;
    TextView listText;
    ListDBHandler listDBHandler;
    ImageButton mAddButton;
    ListItem[] items = new ListItem[10];
    Boolean found = true;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BluetoothAdapter bleDev = null;
    private BluetoothLeScanner scanner = null;
    private ScanResultArrayAdapter scanAdapter = null;
    private static final int REQUEST_ENABLE_BT = 1000;
    private boolean isScanning = false;
    private int scanMode = ScanSettings.SCAN_MODE_BALANCED;
    //private BeaconInfo selectedBeacon = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.list_layout, container, false);
        super.onCreate(savedInstanceState);

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

        scanAdapter = new ScanResultArrayAdapter(getActivity());

        // retrieve the BluetoothManager instance and check if Bluetooth is enabled. If not the
        // user will be prompted to enable it and the response will be checked in onActivityResult
        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bleDev = bluetoothManager.getAdapter();
        if (bleDev == null || !bleDev.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        startScan();
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // begin scheduling graph updates
        //graphHandler.postDelayed(updateUI, GRAPH_UPDATE_TIME_MS);
    }

    @Override
    public void onPause() {
        super.onPause();

        // stop any in-progress scan and stop updating the graph if activity is paused
        stopScan();
        //graphHandler.removeCallbacks(updateUI);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode != getActivity().RESULT_OK) {
                Toast.makeText(getActivity(), "Bluetooth not enabled!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Bluetooth enabled successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    Runnable updateUI = new Runnable() {
        @Override
        public void run() {

        }
    };

    private void makeT() {
        Toast.makeText(getActivity(), "Update UI called", Toast.LENGTH_SHORT).show();
    }

    private void stopScan() {
        if(scanner != null && isScanning) {
            Toast.makeText(getActivity(), "Stopping BLE scan...", Toast.LENGTH_SHORT).show();
            isScanning = false;
            Log.i(TAG, "Scan stopped");
            scanner.stopScan(bleScanCallback);
        }

        //selectedBeacon = null;
        //toggleScan.setText("Start scanning");
        scanAdapter.clear();
    }

    private void toggleScan() {
        if(!isScanning)
            startScan();
        else
            stopScan();
    }

    private void startScan() {
        if(scanner == null) {
            scanner = bleDev.getBluetoothLeScanner();
            if(scanner == null) {
                // probably tried to start a scan without granting Bluetooth permission
                Toast.makeText(getActivity(), "Failed to start scan (BT permission granted?)", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Failed to get BLE scanner instance");
                return;
            }
        }

        Toast.makeText(getActivity(), "Starting BLE scan...", Toast.LENGTH_SHORT).show();

        // clear old scan results
        scanAdapter.clear();

        List<ScanFilter> filters = new ArrayList<>();
        ScanSettings settings = new ScanSettings.Builder().setScanMode(scanMode).build();
        scanner.startScan(filters, settings, bleScanCallback);
        isScanning = true;
        //toggleScan.setText("Stop scanning");
    }

    // class implementing BleScanner callbacks
    private ScanCallback bleScanCallback = new ScanCallback() {

        ArrayList<String> bought_list;

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            final BluetoothDevice dev = result.getDevice();
            final int rssi = result.getRssi();



            if(dev != null && isScanning) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // retrieve device info and add to or update existing set of beacon data
                        String name = dev.getName();
                        String address = dev.getAddress();
                        scanAdapter.update(dev, address, name == null ? "Unnamed device" : name, rssi);

                        // once its found the right beacon
                        if (dev.getAddress().equals("E2:87:2B:54:1F:7B") && !found) {

                            // create a sublist of items on shopping list belonging to the category
                            ListItem[] subList = new ListItem[10];
                            int subListCount = 0;
                            for (int i = 0; i < items.length -1; i++){
                                if (items[i].get_cat().equals("Dairy")) {
                                    subList[subListCount] = items[i];
                                    subListCount++;
                                }
                            }

                            // create the pop up
                            ListPopUp nearMe = new ListPopUp();
                            nearMe.setList(subList);
                            nearMe.show(getFragmentManager(), "nearMe");

                            // get the items that the user selected and set them to bought in the list
                            bought_list = nearMe.getList();
                            for (String bought_item : bought_list) {
                                for (int i = 0; i < items.length -1; i++){
                                    if (items[i].get_item_name().equals(bought_item)) {
                                        items[i].set_bought(true);
                                    }
                                }
                            }
                            //Toast.makeText(getActivity(), "WOOHOO", Toast.LENGTH_SHORT).show();
                            found = true;
                        }
                    }

                });
            }
        }


        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d(TAG, "BatchScanResult(" + results.size() + " results)");
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.w(TAG, "ScanFailed(" + errorCode + ")");
        }
    };

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
            for(int i=0;i<WINDOW_SIZE;i++)
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
            for(int i=0;i<WINDOW_SIZE;i++) {
                mean += window[i];
            }
            mean /= WINDOW_SIZE;
            return mean;
        }

        @Override
        public boolean equals(Object o) {
            // test if beacon objects are equal using their addresses
            if(o != null && o instanceof BeaconInfo) {
                BeaconInfo other = (BeaconInfo) o;
                if(other.address.equals(address))
                    return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            // as with equals() use addresses to test equality
            return address.hashCode();
        }
    }

    // basic adapter class to act as a data source for the list widget
    private class ScanResultArrayAdapter extends BaseAdapter {

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
                row = inflater.inflate(R.layout.layout_scanresults, parent, false);
            } else {
                row = convertView;
            }
            return row;
        }
    }


    //Add a product to the database
    public void addButtonClicked(View view) {
        ListItem item = new ListItem(itemInput.getText().toString());
        listDBHandler.addItem(item);
        printDatabase();
    }

    //Print the database
    public void printDatabase() {

        String dbString = "";
        for (int i = 0; i < items.length - 1; i++) {
            if (items[i] == null) {
                break;
            }
            //Toast.makeText(getActivity(), i, Toast.LENGTH_SHORT).show();
            if (!items[i].is_bought()) {
                dbString += items[i].get_item_name() + "\n";
            }
        }
        listText.setText(dbString);
        itemInput.setText("");
    }


}