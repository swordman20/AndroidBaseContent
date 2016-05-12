package com.example.a03android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * Created by Hsia on 16/5/12.
 * E-mail: xiaweifeng@live.cn
 * //TODO:对数据操作的类
 */
public class PersonDao {

    private final MyOpenHelp help;

    public PersonDao(Context context) {
        help = new MyOpenHelp(context);
    }

    /**
     * 插入数据到数据库的方法
     * @param name
     * @param phone
     * @return
     */
    public boolean add(String name,String phone){
        SQLiteDatabase db = help.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        long l = db.insert("info", null, values);
        //关闭下数据库
        db.close();
        if (l == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 数据库的删除方法
     * @param name
     * @return
     */
    public int del(String name){
        SQLiteDatabase db = help.getWritableDatabase();
        int info = db.delete("info", "name = ?", new String[]{name});
        return info;
    }

    /**
     * 更改数据库，通过名字更改号码
     * @param name
     * @param phone
     * @return
     */
    public int update(String name,String phone){
        SQLiteDatabase db = help.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",phone);
        int info = db.update("info", values, "name = ?", new String[]{name});
        return info;
    }

    public String find(){
        SQLiteDatabase db = help.getWritableDatabase();
        //查询到结果集
        /**
         * table 表名
         * columns  查询的列  具体查询的是哪一列
         * selection 根据什么条件去
         * selectionArgs
         */
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        String name = "";
        String phone = "";
        while (cursor.moveToNext()){
            //获取数据库中String数据
            name =  cursor.getString(0);
            phone = cursor.getString(1);
        }
        db.close();
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)) {
            return null;
        }else {
            return name +","+ phone;
        }
    }
}
