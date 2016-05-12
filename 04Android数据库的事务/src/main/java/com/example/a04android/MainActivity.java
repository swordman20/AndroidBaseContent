package com.example.a04android;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyOpenHelper myOpenHelper = new MyOpenHelper(this);
        db = myOpenHelper.getWritableDatabase();
    }
    public void click(View view){
        db.beginTransaction();
        try {
            // 做银行转账的逻辑
            db.execSQL("update account set money = money - 100 where name = ?",
                    new Object[] { "张三" });

            // 模拟一个异常
            int i = 1 / 0;

            db.execSQL("update account set money = money + 100 where name = ?",
                    new Object[] { "李四" });
            // 设置一个成功标记 如果该句话执行了 说明我们事务成功 如有有异常 就会回滚
            db.setTransactionSuccessful();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "数据库操作失败，数据已回滚到上个版本", Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();
        }
    }
}
