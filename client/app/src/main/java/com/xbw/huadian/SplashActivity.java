package com.xbw.huadian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xbw.huadian.model.Start;
import com.xbw.huadian.util.Constant;
import com.xbw.huadian.util.HttpUtils;
import com.xbw.huadian.util.SharedPreference;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SplashActivity extends Activity {

    private ImageView iv_start;
    private Start content;
    private SharedPreference sharedPreference;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        iv_start = (ImageView) findViewById(R.id.iv_start);
        textView=(TextView)findViewById(R.id.textView10);
        textView.setText("版本："+getVersionName()+" v");
        sharedPreference=new SharedPreference(this);
        initImage();
    }

    private void initImage() {
        File dir = getFilesDir();
        final File imgFile = new File(dir, "start.png");
        if (imgFile.exists()) {
            iv_start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            iv_start.setImageResource(R.drawable.start);
        }
        //设置开屏页无动画
        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (HttpUtils.isNetworkConnected(SplashActivity.this)) {
                    HttpUtils.gets(Constant.SPLASH, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            startActivity();
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            //Toast.makeText(SplashActivity.this,responseString,Toast.LENGTH_LONG).show();
                            try {
                                Gson gson = new Gson();
                                content = gson.fromJson(responseString, Start.class);
                            }catch (JsonSyntaxException e){
                                startActivity();
                                return;
                            }
                            //第一次启动本地无名字存储
                            if(sharedPreference.isSplash(this.getClass().getName())){
                                //开屏页的名字跟本地名字一样就不下载图片
                                if(content.getname().equals(sharedPreference.getSplash())){
                                    startActivity();
                                }else{
                                    //Toast.makeText(SplashActivity.this,content.getimg(),Toast.LENGTH_SHORT).show();
                                    HttpUtils.getImage(content.getimg(), new BinaryHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                            saveImage(imgFile, bytes);
                                            //保存新的图片名字到本地
                                            sharedPreference.KeepSplash(content.getname());
                                            //Toast.makeText(SplashActivity.this,"下载图片成功",Toast.LENGTH_SHORT).show();
                                            startActivity();
                                        }
                                        @Override
                                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                            startActivity();
                                            //Toast.makeText(SplashActivity.this,"下载图片失败",Toast.LENGTH_SHORT).show();
                                        }
                                        @Override
                                        public String[] getAllowedContentTypes() {
                                            return super.getAllowedContentTypes();
                                        }
                                    });
                                }
                            }else{
                                //Toast.makeText(SplashActivity.this,content.getimg(),Toast.LENGTH_SHORT).show();
                                HttpUtils.getImage(content.getimg(), new BinaryHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                        saveImage(imgFile, bytes);
                                        //保存新的图片名字到本地
                                        sharedPreference.KeepSplash(content.getname());
                                        //Toast.makeText(SplashActivity.this,"下载图片成功",Toast.LENGTH_SHORT).show();
                                        startActivity();
                                    }
                                    @Override
                                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                        startActivity();
                                        //Toast.makeText(SplashActivity.this,"下载图片失败",Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public String[] getAllowedContentTypes() {
                                        return super.getAllowedContentTypes();
                                    }
                                });
                            }

                        }
                    });
                } else {
                    Toast.makeText(SplashActivity.this, "没有网络连接!", Toast.LENGTH_LONG).show();
                    startActivity();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_start.startAnimation(scaleAnim);

    }

    private void startActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        //overridePendingTransition(android.R.anim.fade_in,
        //        android.R.anim.fade_out);
        //finish();
    }

    public void saveImage(File file, byte[] bytes) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            //Toast.makeText(SplashActivity.this,file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    String getVersionName() {
        PackageManager packageManager = getPackageManager();
        if (packageManager == null) {
            return null;
        }

        try {
            return packageManager.getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
} 


