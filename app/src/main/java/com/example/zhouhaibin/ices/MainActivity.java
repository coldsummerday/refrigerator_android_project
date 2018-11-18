package com.example.zhouhaibin.ices;

import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android_serialport_api.SerialPort;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {


    protected SerialPort serialPort;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    protected ReadThread readThread;

    private Button button_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        button_start = (Button) findViewById(R.id.button_start);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("开始启动", "onCreate: start thread");
                try{
                    serialPort = new SerialPort(new File("/dev/ttyUSB0"),9600,0);
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    readThread = new ReadThread();
                    readThread.start();
                }catch (SecurityException e){
                    Log.d("debug","启动失败");
                    e.printStackTrace();
                }catch (IOException e){
                    Log.d("debug", "onCreate: 启动失败");
                    e.printStackTrace();
                }
            }
        });
    }

    private class ReadThread extends Thread{

        @Override
        public void run(){
            super.run();
            while (!isInterrupted()){
                int size;
                Log.d("debug", "run:接收线程已经开启");
                try{
                    byte[] buffer = new byte[64];
                    if (inputStream==null){
                        return;
                    }
                    size = inputStream.read(buffer);
                    if(size>0){
                        onDataReceived(buffer, size);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                    return;
                }
            }
        }

    }

    protected void onDataReceived(final byte[] buffer,final int size){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String recinfo = new String(buffer,0,size);
                Log.d("debug", "run: ====>"+recinfo);
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
