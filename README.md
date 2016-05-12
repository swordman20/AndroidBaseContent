#Android基础知识点总结
**在学习了很长一段时间的Android基础内容，如果不做及时的复习，那么后面的项目学习起来可能就没那么顺利了，并且复习也是一件很愉快的事。古人有云：“学而时习之不亦说乎”，应该就是这个道理。**
* * *
~本文大纲~

[TOC]


* * *


##Day01
*第一天主要学习的内容相对零散一点，但还是很重要的。*
###01、Android系统中我们常用的4种数据存储方式★★★★★
```
 1. SharedPreferences存储数据
 2. getFilesDir数据存储在手机上
 3. Environment数据存储在SD卡上
 4. SQLite数据库存储
```
**1、SharedPreferences存储数据**
保存数据
```
SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
                    boolean commit = config.edit().putString("Hsia", mContent).commit();
                    if (commit) {
                        Toast.makeText(getApplicationContext(), "数据保存成功！", Toast.LENGTH_SHORT).show();
                        mEtContent.setText("");
                    }
```
取回数据
```
String getConfig = getSharedPreferences("config", MODE_PRIVATE).getString("Hsia", "");
                if (!("".equals(getConfig))) {
                    Toast.makeText(getApplicationContext(), "数据取回成功！", Toast.LENGTH_SHORT).show();
                    mEtContent.setText(getConfig);
                }
```
**2、getFilesDir数据存储在手机上**
```
 /**
     * 获取已保存的用户信息
     * @param context
     * @return
     */
    private String getConfigFile(Context context) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(context.getFilesDir(), FILENAME)));
            String data = br.readLine();
            br.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息到手机上
     * @param context
     * @param mContent
     * @return
     */
    private boolean SaveConfig(Context context, String mContent) {
        if (mContent != null) {
            File file = new File(context.getFilesDir(),FILENAME);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(mContent);
                bw.newLine();
                bw.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
```
**3、Environment数据存储在SD卡上**
```
/**
     * 从sd卡中取出数据
     * @return
     */
    private String SDGetConfig() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                FileReader fr = new FileReader(new File(Environment.getExternalStorageDirectory() + SDDownload, FILENAME));
                int len = 0;
                char[] chs = new char[1024];
                String data = "";
                while((len = fr.read(chs))!=-1){
                    data = new String(chs,0,len);
                }
                fr.close();
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存数据到sd卡中
     * @param mContent
     * @return
     */
    private boolean SDSaveConfig(String mContent) {
        //判断sd卡是否存在
        String state = Environment.getExternalStorageState();
        //如果sd卡不存在直接返回false
        if (!(Environment.MEDIA_MOUNTED.equals(state))) {
            return false;
        }else{
            try {
            FileWriter fw = new FileWriter(new File(Environment.getExternalStorageDirectory() + SDDownload, FILENAME));
                fw.write(mContent);
                fw.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
```

![1.png](/Volumes/软件/GreenSoft/我的blog/blog配图/1.png)
![1.png](/Volumes/软件/GreenSoft/我的blog/blog配图/2.png)

***
- SharedPreferences存储数据，会在当前包名下，生成一个“shared_prefs”文件夹，配置文件放在该目录下。
- getFilesDir存储数据，会在当前包名下，生成一个“files”文件夹，配置文件放在该目录下。
- Environment存储数据，获取到SD卡根目录，文件默认会存放在SD卡根目录中。

**4、SQLite数据库存储（相对知识点较多暂时不做演示）**

###02、获取手机内存空间大小★★★
*由于获取手机空间大小实际开发中使用较少，并且相对简单，就不做代码测试了，主要就用到Environment（获取SD卡空间）和getFilesDir（获取手机空间）。*

关键代码

- 获取SD卡空间大小

```
File storageDirectory = Environment.getExternalStorageDirectory();
long totalSpace = storageDirectory.getTotalSpace();
long usableSpace = storageDirectory.getUsableSpace();

```
- 获取手机空间大小

```
File filesDir = getFilesDir();
long totalSpace = filesDir.getTotalSpace();
long usableSpace = filesDir.getUsableSpace();
String totalSpaceStr = Formatter.formatFileSize(this, totalSpace);
String usableSpaceStr = Formatter.formatFileSize(this, usableSpace);
```

###03、XML文件的生成和解析★★★
- 生成XML文件


![3.png](/Volumes/软件/GreenSoft/我的blog/blog配图/3.png)
***
```
/**
     * 生成XML文件的方法
     * @return
     */
    private boolean MakeXML() {
        //Xml.newSerializer开始拼接数据生成xml
        XmlSerializer xmlSerializer = Xml.newSerializer();
        //设置xml文件的存放路径
        File file = new File(getFilesDir(), "makeXML.xml");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            xmlSerializer.setOutput(fos,"utf-8");
            //开始写xml
            xmlSerializer.startDocument("utf-8",true);
            //开始写xml的根节点
            xmlSerializer.startTag(null,"Message");
            for (int i = 0; i < 5; i++) {
                xmlSerializer.startTag(null,"sms");
                xmlSerializer.startTag(null,"address");
                xmlSerializer.text("15855225588");
                xmlSerializer.endTag(null,"address");
                xmlSerializer.startTag(null,"name");
                xmlSerializer.text("Hsia");
                xmlSerializer.endTag(null,"name");
                xmlSerializer.startTag(null,"body");
                xmlSerializer.text("Hello!!!!");
                xmlSerializer.endTag(null,"body");
                xmlSerializer.endTag(null,"sms");
            }
            xmlSerializer.endTag(null,"Message");
            xmlSerializer.endDocument();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
```

- 解析XML文件

```
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

```

###GET新技能
- 1、将LogCat信息显示在控制台中

	在控制台中输入adb logcat 然后按回车键即可看到LogCat信息，如果需要终止按Ctrl+C键即可。

- 2、将LogCat信息保存在文件中

	执行adb logcat >D:/a.txt 则将日志输出到D:/a.txt文件中。按Ctrl+C键终止日志的输出。



##Day02
*主要学习Android-SQLite和ListView控件的使用*
###SQLiteOpenHelper的基本用法

![image](/Users/xwf/Desktop/d1.gif)

1、SQLiteOpenHelper一般就是写一个类，继承SQLiteOpenHelper来创建一个数据库。

```
public class MyOpenHelp extends SQLiteOpenHelper {
    public MyOpenHelp(Context context) {
        super(context, "person.db", null, 1);
    }
    /**
     * Called when the database is created for the first time
     * 当 数据第一次创建的时候调用
     * 该方法适合做 表结构的初始化
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("oncreate");
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))");
    }
    /**
     * 当数据库需要升级的时候调用
     * 该方法适合做 表结构修改
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade");
    }
}

```

2、新建一个针对数据库的操作类

```
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
```

3、对数据库进行增删改查

```
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
```

###数据库事务
> 即同一组操作要么同时成功，要么同时失败.

```
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
```
###Listview的简单优化

```
 if (convertView == null) {
 	//历史缓存对象为空  我创建一个新的View 对象 
			tv = new  TextView(getApplicationContext());
			System.out.println("创建新的view对象--"+position);
		}else {
			//历史缓存对象不为空
			tv = (TextView) convertView;
			System.out.println("复用历史缓存对象--"+position);
			}
```


![image](/Users/xwf/Desktop/d2.gif)
> 复用历史缓存对象

```
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
```

##day03
主要学习Android下访问网络的操作，涉及到Handle消息机制的处理
###消息机制的写法

```
	(1)在主线程创建一个Handler 对象 
	(2)重写Handler的HandleMessage方法
	(3)拿着handler 去子线程发送一条消息
	(4)发送完消息后 handleMessage方法就会执行
```
###网络图片查看器

![image](/Users/xwf/Desktop/n1.gif)

```
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
```

> 备注：关于访问网络的内容暂时就写这么多了，后面什么Httpclient，由于谷歌在Android5.0的时候已经废弃了，所以，不做赘述。

##day04
##day05
##day06
**关于作者**
	- 个人网站：[北京互联科技](http://shop.zbj.com/14622657/)
	- Email：[xiaweifeng@live.cn](https://login.live.com)
	- 项目地址:[https://github.com/swordman20/AndroidBaseContent.git](https://github.com/swordman20/AndroidBaseContent.git)