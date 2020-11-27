package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("OverrideAbstract")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyNotifyService extends NotificationListenerService {

    private BufferedWriter bw;
    private SimpleDateFormat sdf;
    private MyHandler handler = new MyHandler();
    private String nMessage;
    private String data;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AAA", "Service is started"+"-----");
        data = intent.getStringExtra("data ");

        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        if (notification == null) {
            return;
        }
        Bundle extras = notification.extras;
        if (extras != null) {
            //包名
            String pkg = sbn.getPackageName();
            // 获取通知标题
            String title = extras.getString(Notification.EXTRA_TITLE, "");
            // 获取通知内容
            String content = extras.getString(Notification.EXTRA_TEXT, "");
            String str = String.format("收到通知\n包名：%s\n标题：%s\n内容：%s", pkg, title, content);
            Log.i("AAA", str);
            Toast.makeText(MyNotifyService.this, str, Toast.LENGTH_LONG).show();
        }
    }
    private void writeData(String str){
        try {
//            bw.newLine();
//            bw.write("NOTE");
            bw.newLine();
            bw.write(str);
            bw.newLine();
//            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void init(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            FileOutputStream fos = new FileOutputStream(newFile(),true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
        }catch(IOException e){
            Log.d("KEVIN","BufferedWriter Initialization error");
        }
        Log.d("KEVIN","Initialization Successful");
    }

    private File newFile() {
//        try {
        File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator  + "ANotification");
        fileDir.mkdir();
        String basePath = Environment.getExternalStorageDirectory() + File.separator + "ANotification" + File.separator + "record.txt";
        return new File(basePath);

    }


    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1 :
//                    Toast.makeText(MyService.this,"Bingo",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
