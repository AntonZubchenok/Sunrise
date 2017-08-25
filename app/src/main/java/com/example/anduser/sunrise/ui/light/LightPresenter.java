package com.example.anduser.sunrise.ui.light;

import com.example.anduser.sunrise.bluetooth.BluetoothHelper;

public class LightPresenter implements LightContract.Presenter {

    private LightContract.View view;
    private BluetoothHelper bluetoothHelper;

    public LightPresenter(LightContract.View view) {
        this.view = view;
        this.bluetoothHelper = BluetoothHelper.getInstance();
    }

    @Override
    public void onButtonClicked(boolean state) {
        bluetoothHelper.switchLight(state);
    }
}
