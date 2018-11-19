package com.example.zhouhaibin.ices;

import com.jiangdg.usbcamera.UVCCameraHelper;

public class ConstString {
    public static final String pic_save_path = UVCCameraHelper.ROOT_PATH + "refrigerator/";
    public static final String dateTimeFormatStr = "yyyy-MM-dd HH:mm:ss";
    public static final String arduinoDevice = "/dev/ttyACM0";
    public static final String post_url = "http://10.249.177.52:8000/test/api";
}
