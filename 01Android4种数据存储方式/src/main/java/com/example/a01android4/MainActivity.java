package com.example.a01android4;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.io.FileWriter;
        import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtContent;
    private String mContent;
    private static final String FILENAME = "config.txt";
    private static final String SDDownload = "/Download";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtContent = ((EditText) findViewById(R.id.et_content));
        Button spSave = (Button) findViewById(R.id.sp_save);
        Button spGetconfig = (Button) findViewById(R.id.sp_getConfig);
        Button phoneSave = (Button) findViewById(R.id.phoneSave);
        Button phoneGetConfig = (Button) findViewById(R.id.phone_getConfig);
        Button sdSave = (Button) findViewById(R.id.SD_save);
        Button sdGetConfig = (Button) findViewById(R.id.SD_getConfig);
        spSave.setOnClickListener(this);
        spGetconfig.setOnClickListener(this);
        phoneSave.setOnClickListener(this);
        phoneGetConfig.setOnClickListener(this);
        sdSave.setOnClickListener(this);
        sdGetConfig.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_save:
                mContent = mEtContent.getText().toString().trim();
                if (!(TextUtils.isEmpty(mContent))) {
                    SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
                    boolean commit = config.edit().putString("Hsia", mContent).commit();
                    if (commit) {
                        Toast.makeText(getApplicationContext(), "SP数据保存成功！", Toast.LENGTH_SHORT).show();
                        mEtContent.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "SP数据保存失败！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "需要保存的数据不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sp_getConfig:
                String getConfig = getSharedPreferences("config", MODE_PRIVATE).getString("Hsia", "");
                if (!("".equals(getConfig))) {
                    Toast.makeText(getApplicationContext(), "SP数据取回成功！", Toast.LENGTH_SHORT).show();
                    mEtContent.setText(getConfig);
                }else{
                    Toast.makeText(getApplicationContext(), "SP数据取回失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.phoneSave:
                mContent = mEtContent.getText().toString().trim();
                if (!(TextUtils.isEmpty(mContent))) {
                    boolean saveConfig = SaveConfig(this,mContent);
                    if (saveConfig) {
                        Toast.makeText(getApplicationContext(), "getFilesDir保存数据成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "getFilesDir保存数据失败！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "需要保存的数据不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.phone_getConfig:
                String getFileData = getConfigFile(this);
                if (!(TextUtils.isEmpty(getFileData))) {
                    mEtContent.setText(getFileData);
                    Toast.makeText(getApplicationContext(), "getFilesDir恢复数据成功！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "getFilesDir恢复数据失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SD_save:
                mContent = mEtContent.getText().toString().trim();
                if (!(TextUtils.isEmpty(mContent))) {
                    boolean sDSaveConfig = SDSaveConfig(mContent);
                    if (sDSaveConfig) {
                        Toast.makeText(getApplicationContext(), "SD卡保存数据成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "SD卡保存数据失败！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "需要保存的数据不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SD_getConfig:
                String sdGetConfig = SDGetConfig();
                if (!(TextUtils.isEmpty(sdGetConfig))) {
                    mEtContent.setText(sdGetConfig);
                    Toast.makeText(getApplicationContext(), "SD卡恢复数据成功！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "SD卡恢复数据失败！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

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
}
