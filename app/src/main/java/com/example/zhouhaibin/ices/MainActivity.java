package com.example.zhouhaibin.ices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android_serialport_api.SerialPort;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.zhouhaibin.ices.Activity.CameraActivity;
import com.example.zhouhaibin.ices.Activity.test_camActivity;
import com.example.zhouhaibin.ices.SerialportHelper.RecvSerialCallback;
import com.example.zhouhaibin.ices.SerialportHelper.SerialPortHandle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    /*
    protected SerialPort serialPort;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    */

    private SerialPortHandle serialPortHandle;
    private Button button_start;
    private Button button_cam;

    private RecvSerialCallback recvSerialCallback = new RecvSerialCallback() {
        @Override
        public void onRecvObjectIn() {
            Log.d("serial", "onRecvData: in");
        }

        @Override
        public void onRecvObjectOut() {
            Log.d("serial", "onRecvData: out");
        }

        @Override
        public void onRecvData(String recvData) {
            //Log.d("serial", "onRecvData: "+recvData);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        serialPortHandle = new SerialPortHandle("/dev/ttyACM0",9600,0);
        serialPortHandle.setRecvSerialCallback(recvSerialCallback);
        button_start = (Button) findViewById(R.id.button_start);
        button_cam = (Button) findViewById(R.id.button_capture);
        init_button();

    }
    private void init_button(){
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortHandle.startRecv();
                /*
                Log.d("开始启动", "onCreate: start thread");
                try{
                    serialPort = new SerialPort(new File("/dev/ttyUSB0"),9600,0);
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    //readThread = new ReadThread();
                    //readThread.start();
                }catch (SecurityException e){
                    Log.d("debug","启动失败");
                    e.printStackTrace();
                }catch (IOException e){
                    Log.d("debug", "onCreate: 启动失败");
                    e.printStackTrace();
                }
                */
            }

        });
        button_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, test_camActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
