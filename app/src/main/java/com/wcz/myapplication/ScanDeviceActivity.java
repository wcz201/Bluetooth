package com.wcz.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScanDeviceActivity extends AppCompatActivity {
    private static final String TAG = "ScanDeviceActivitywcz";
    RecyclerView recyclerView;
    List<BluetoothDevice> list;
    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;
    ScanDeviceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_device);
        init();
        turnOnBluetooth();

        scanDevice();
    }

    private void scanDevice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                list.clear();
                BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
                bluetoothLeScanner.startScan(scanCallback);


            }
        }).start();
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, final ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            if (list.contains(device)) {
                Log.e(TAG, "onScanResult: 重复的" );
                return;
            }
            list.add(device);
            Log.e(TAG, "Device name: " + device.getName());
            Log.e(TAG, "Device address: " + device.getAddress());
            Log.e(TAG, "Device service UUIDs: " + device.getUuids());
            ScanRecord record = result.getScanRecord();
            Log.e(TAG, "Record advertise flags: 0x" + Integer.toHexString(record.getAdvertiseFlags()));
            Log.e(TAG, "Record Tx power level: " + record.getTxPowerLevel());
            Log.e(TAG, "Record device name: " + record.getDeviceName());
            Log.e(TAG, "Record service UUIDs: " + record.getServiceUuids());
            Log.e(TAG, "Record service data: " + record.getServiceData());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    private void init() {
        recyclerView = findViewById(R.id.scan_recycler);
        list = new ArrayList<>();
        adapter = new ScanDeviceAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        bluetoothAdapter = bluetoothManager.getAdapter();


    }

    private void turnOnBluetooth() {
        Intent turn_on = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turn_on, 0);
        Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
