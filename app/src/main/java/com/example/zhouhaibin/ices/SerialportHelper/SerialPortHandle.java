package com.example.zhouhaibin.ices.SerialportHelper;


import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

public class SerialPortHandle {
    /*
    利用谷歌Android_serialport_api 库

    实现 接收到   激光传感器信号后 拍照上传功能
     */

    protected static final String TAG  = "serialPortHandler";
    protected String recvData;

    protected SerialPort serialPort;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    protected ReadThread readThread;
    protected RecvSerialCallback recvSerialCallback;


    public SerialPortHandle(String serialPortStr,int baudrate,int flag){
        try{
            serialPort = new SerialPort(new File(serialPortStr),baudrate,flag);
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setRecvSerialCallback(RecvSerialCallback callback){
        this.recvSerialCallback = callback;
    }

    public void startRecv(){
        this.readThread = new ReadThread();
        this.readThread.run();
    }
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (inputStream == null) {
                        return;
                    }
                    size = inputStream.read(buffer);
                    if (size > 0) {
                        recvData = new String(buffer,0,size);
                        recvSerialCallback.onRecvData(recvData);
                        if(recvData.contains("3")){
                            recvSerialCallback.onRecvObjectIn();
                        }
                        if(recvData.contains("4")){
                            recvSerialCallback.onRecvObjectOut();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}


