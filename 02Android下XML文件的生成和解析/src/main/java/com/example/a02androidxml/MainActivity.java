package com.example.a02androidxml;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mMakeXML;
    private Button mResolveXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMakeXML = (Button) findViewById(R.id.makeXML);
        mResolveXML = (Button) findViewById(R.id.resolveXML);
        mMakeXML.setOnClickListener(this);
        mResolveXML.setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMakeXML.setVisibility(View.VISIBLE);
                mResolveXML.setVisibility(View.VISIBLE);
                Snackbar.make(view, "请选择按钮进行操作。", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mMakeXML.setVisibility(View.INVISIBLE);
            mResolveXML.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"按钮选项已关闭！",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.makeXML:
                boolean makeXML = MakeXML();
                if (makeXML) {
                    Toast.makeText(getApplicationContext(),"XML文件已生成。",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"XML文件生成失败。",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.resolveXML:
                //指定XML文件位置
                File file = new File(getFilesDir(), "makeXML.xml");
                List<Weather> pullXMLs = GetResolveXML(file);
                if (pullXMLs != null) {
                    Toast.makeText(getApplicationContext(),"XML文件解析完成:"+pullXMLs.toString(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"XML文件解析失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 解析XML的方法
     * @param file
     * @return
     */
    private List<Weather> GetResolveXML(File file) {
        XmlPullParser parser = Xml.newPullParser();
        FileInputStream fis = null;
        List<Weather> list = null;
        Weather weather = null;
        try {
            fis = new FileInputStream(file);
            parser.setInput(fis,"utf-8");
            int type = parser.next();
            while(type!=XmlPullParser.END_DOCUMENT){
                if (type==XmlPullParser.START_TAG) {
                    list = new ArrayList<>();
                    //如果开始标签是weather标签则实例化weather
                    if ("weather".equals(parser.getName())) {
                        weather = new Weather();
                    }else if ("city".equals(parser.getName())) {
                        //获取节点下的属性值
                        String id = parser.nextText();
                        weather.setId(id);
                    }else if ("name".equals(parser.getName())) {
                        String name = parser.nextText();
                        weather.setName(name);
                    }else if ("pm".equals(parser.getName())) {
                        String pm = parser.nextText();
                        weather.setPm(pm);
                    }else if ("wind".equals(parser.getName())) {
                        String wind = parser.nextText();
                        weather.setWind(wind);
                    }else if ("temp".equals(parser.getName())) {
                        String temp = parser.nextText();
                        weather.setTemp(temp);
                    }
                }
                type = parser.next();
                list.add(weather);
            }
            fis.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 生成XML文件的方法
     * @return
     */
    private boolean MakeXML() {
        //Xml.newSerializer开始拼接数据生成xml
        XmlSerializer xmlSerializer = Xml.newSerializer();
        File file = new File(getFilesDir(), "makeXML.xml");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            xmlSerializer.setOutput(fos,"utf-8");
            //开始写xml
            xmlSerializer.startDocument("utf-8",true);
            //开始写xml的根节点
            xmlSerializer.startTag(null,"weather");
//            for (int i = 0; i < 5; i++) {
            xmlSerializer.startTag(null,"city");
            xmlSerializer.text("1");
            xmlSerializer.endTag(null,"city");
            xmlSerializer.startTag(null,"name");
            xmlSerializer.text("北京");
            xmlSerializer.endTag(null,"name");
            xmlSerializer.startTag(null,"wind");
            xmlSerializer.text("4");
            xmlSerializer.endTag(null,"wind");
            xmlSerializer.startTag(null,"temp");
            xmlSerializer.text("21-30");
            xmlSerializer.endTag(null,"temp");
//            }
            xmlSerializer.endTag(null,"weather");
            xmlSerializer.endDocument();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
