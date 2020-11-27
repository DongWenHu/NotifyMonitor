package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private Intent intent;
    private Button button;
    private Button button2;
    private Button button3;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button =findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        editText=findViewById(R.id.editText);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//打开监听引用消息Notification access
                intent =new Intent(MainActivity.this, MyNotifyService.class);//启动服务
                intent.putExtra("data", editText.getText().toString());
                startService(intent);//启动服务

            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public  void onClick(View v){
                Intent intent_s=new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent_s);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //创建通知渠道实例
                //并为它设置属性
                //通知渠道的ID,随便写
                String id = "channel_01";
                //用户可以看到的通知渠道的名字，R.string.app_name就是strings.xml文件的参数，自定义一个就好了
                CharSequence name = getString(R.string.app_name);
                //用户可看到的通知描述
                String description = getString(R.string.app_name);
                //构建NotificationChannel实例
                NotificationChannel notificationChannel= new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                //配置通知渠道的属性
                notificationChannel.setDescription(description);
                //设置通知出现时的闪光灯
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                //设置通知出现时的震动
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
                //在notificationManager中创建通知渠道
                manager.createNotificationChannel(notificationChannel);
                Notification notification = new NotificationCompat.Builder(MainActivity.this, id)
                        .setContentTitle("标题")
                        .setContentText("内容  哈哈哈 试试看")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_launcher_foreground) //定义图标
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))//定义自己的图标
                        .build();
                manager.notify(1, notification);
            }
        });
    }
}