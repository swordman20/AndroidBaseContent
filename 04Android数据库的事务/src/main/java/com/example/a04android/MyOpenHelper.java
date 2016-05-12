package com.example.a04android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

	public MyOpenHelper(Context context) {

		super(context, "person.db", null, 1);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table account (_id integer primary key autoincrement,name varchar(20),money varchar(20))");
		db.execSQL("insert into account ('name','money') values ('zhangsan','2000')");
		db.execSQL("insert into account ('name','money') values ('lisi','5000')");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		System.out.println("onUpgrade");
	}
	

}
