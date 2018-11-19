package com.example.zhouhaibin.ices.SerialportHelper;


public interface RecvSerialCallback {
    void onRecvObjectIn();

    void onRecvObjectOut();

    void onRecvData(String recvData);

}