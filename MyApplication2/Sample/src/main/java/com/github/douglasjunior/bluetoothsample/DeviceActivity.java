/*
 * MIT License
 *
 * Copyright (c) 2015 Douglas Nassif Roma Junior
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.douglasjunior.bluetoothsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;

import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.sleep;

/**
 * Created by douglas on 10/04/2017.
 */

public class DeviceActivity extends AppCompatActivity implements BluetoothService.OnBluetoothEventCallback {
    private static final String TAG = "DeviceActivity";

    private BluetoothService mService;
    private BluetoothWriter mWriter;
    private Button mButton;
    private Button setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        mService = BluetoothService.getDefaultInstance();
        mWriter = new BluetoothWriter(mService);

        ButtonListener b = new ButtonListener();
        mButton = (Button) findViewById(R.id.button2);
        mButton.setOnTouchListener(b);
        mButton = (Button) findViewById(R.id.button3);
        mButton.setOnTouchListener(b);

        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceActivity.this, settingActivity.class));
            }
        });
    }


    class ButtonListener implements View.OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.button3) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mWriter.writeln("a");
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mWriter.writeln("c");
                }
            } else if (v.getId() == R.id.button2) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mWriter.writeln("a");
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mWriter.writeln("b");
                }
            }

            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mService.setOnEventCallback(this);
    }

    @Override
    public void onDataRead(byte[] buffer, int length) {
        Log.d(TAG, "onDataRead: " + new String(buffer, 0, length));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.disconnect();
    }

    @Override
    public void onStatusChange(BluetoothStatus status) {
        Log.d(TAG, "onStatusChange: " + status);
    }

    @Override
    public void onDeviceName(String deviceName) {
        Log.d(TAG, "onDeviceName: " + deviceName);
    }

    @Override
    public void onToast(String message) {
        Log.d(TAG, "onToast");
    }

    @Override
    public void onDataWrite(byte[] buffer) {
        Log.d(TAG, "onDataWrite");
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mService.disconnect();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mService.setOnEventCallback(this);
//    }
}
