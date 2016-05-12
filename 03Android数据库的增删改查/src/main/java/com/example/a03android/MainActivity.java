package com.example.a03android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @butterknife.Bind(R.id.btn_add)
    Button btnAdd;
    @butterknife.Bind(R.id.btn_del)
    Button btnDel;
    @butterknife.Bind(R.id.btn_update)
    Button btnUpdate;
    @butterknife.Bind(R.id.btn_find)
    Button btnFind;
    private PersonDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        dao = new PersonDao(this);
    }

    @butterknife.OnClick({R.id.btn_add, R.id.btn_del, R.id.btn_update, R.id.btn_find})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                boolean zhansan = dao.add("zhansan", "13888888");
                if (zhansan){
                    Toast.makeText(this, "数据添加成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "数据插入失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_del:
                int del = dao.del("zhansan");
                if (del >1) {
                    Toast.makeText(this, "删除了" + del + "行", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "数据库已经没有数据了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update:
                int update = dao.update("zhansan", "1377");
                Toast.makeText(this, "更改了" + update + "行数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_find:
                String find = dao.find();
                if (find != null) {
                    String[] split = find.split(",");
                    String name = split[0];
                    String phone = split[1];
                    Toast.makeText(this, "查询结果:" + name + "," + phone, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "数据库中，没有数据了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
