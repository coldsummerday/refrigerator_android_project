package com.example.zhouhaibin.ices.Http;

import com.jiangdg.usbcamera.UVCCameraHelper;
import com.serenegiant.usb.widget.CameraViewInterface;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;

public class HttpClient {
    private  OkHttpClient okHttpClient = new OkHttpClient();
    private static HttpClient httpClient;
    private HttpClient(){

    }
    public static HttpClient getInstance(){
        if(httpClient == null){
            httpClient = new HttpClient();
        }

        return httpClient;
    }

    public void postImage(String filePath, String url,String key ,Callback callback){
        File postFile = new File(filePath);
        String filename = postFile.getName();

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), postFile);
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart(key, filename, fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
