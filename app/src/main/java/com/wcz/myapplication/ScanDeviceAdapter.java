package com.wcz.myapplication;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScanDeviceAdapter extends RecyclerView.Adapter<ScanDeviceAdapter.ViewHolder> {

    public ScanDeviceAdapter(List<BluetoothDevice> list) {
        this.list = list;
    }

    List<BluetoothDevice> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scan_devices_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BluetoothDevice bluetoothDevice = list.get(position);
        viewHolder.name.setText(bluetoothDevice.getName());
        viewHolder.address.setText(bluetoothDevice.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;
        LinearLayout scan_device_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.device_name);
            address = itemView.findViewById(R.id.device_address);
            scan_device_item = itemView.findViewById(R.id.scan_device_item);
        }
    }
}
