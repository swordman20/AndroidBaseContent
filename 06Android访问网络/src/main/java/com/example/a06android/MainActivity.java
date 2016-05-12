package com.example.a06android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case 1:
                    Toast.makeText(MainActivity.this, "访问网络失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
            final Bitmap bitmap = (Bitmap) msg.obj;
            Toast.makeText(MainActivity.this, "3秒后执行", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv.setImageBitmap(bitmap);
                }
            },3000);
        }
    };
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
    }
    public void look(View view){
        lookPng();
    }

    private void lookPng() {
        new Thread(){
            @Override
            public void run() {
                String path = "https://img.alicdn.com/tps/i1/TB1h3IXGFXXXXXsXFXXDANtWXXX-175-175.png_.webp";
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) new URL(path).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        //将获取的流数据转为bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //通过handle更新UI
                        //obtain 实现了new Message ,里面是一个线程池
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    }else{
                        handler.sendEmptyMessage(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
