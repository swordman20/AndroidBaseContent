package com.example.a05listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.lv);
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        //listview优化
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            //如果为空就创建view对象
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item, null);
                System.out.println("Hsia:创建listview对象"+position);
                //不为空就复用 view对象
            }else {
                view = convertView;
                System.out.println("Hsia:复用listview历史对象"+position);
            }
            return view;
        }
    }

}
