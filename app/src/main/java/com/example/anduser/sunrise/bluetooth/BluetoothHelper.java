package com.example.anduser.sunrise.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class BluetoothHelper {

    private static final BluetoothHelper instance = new BluetoothHelper();

    BluetoothAdapter bluetoothAdapter;
    OutputStream outputStream;
    BluetoothDevice device;

    private BluetoothHelper() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothHelper getInstance() {
        return instance;
    }


    public void initBluetoothDevice() {
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if (!bondedDevices.isEmpty()) {
            Object[] devices = bondedDevices.toArray();
            BluetoothDevice device = (BluetoothDevice) devices[0];
            ParcelUuid[] uuids = device.getUuids();
            BluetoothSocket socket;
            try {
                socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                socket.connect();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBluetoothEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public OutputStream getOutputStream() {
        if (outputStream == null) {
            initBluetoothDevice();
        }
        return outputStream;
    }

    public void switchLight(boolean state) {
        if (device == null) {
            initBluetoothDevice();
        }
        try {
            if (state) {
                outputStream.write("light set on".getBytes());
            } else {
                outputStream.write("light set off".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
