package com.example.zhouhaibin.ices.Activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getNowTimeStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd~HH:mm:ss");// 设置日期格式
        String nowdate = df.format(new Date());// new Date()为获取当前系统时间
        return nowdate;
    }
    public  static void createDir(String dirPath){
        File dir = new File(dirPath);
        //文件夹是否已经存在
        if (dir.exists()) {
            return ;
        }
        if (!dirPath.endsWith(File.separator)) {//不是以 路径分隔符 "/" 结束，则添加路径分隔符 "/"
            dirPath = dirPath + File.separator;
        }
        //创建文件夹
        if (dir.mkdirs()) {
            return ;
        }
    }
}
