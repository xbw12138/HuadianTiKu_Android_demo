package com.xbw.huadian;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xbw.huadian.model.Problem;
import com.xbw.huadian.util.Constant;
import com.xbw.huadian.util.DataFactory;
import com.xbw.huadian.util.HttpUtils;
import com.xbw.huadian.util.Progress_Dialog;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private DataFactory dataf;
    private List<Problem> QT;
    private List<Problem> danxuan;
    private List<Problem> duoxuan;
    private List<Problem> panduan;
    //总的题目数据
    private int count;
    //当前显示的题目
    private int corrent;
    //问题
    private TextView tv_title;
    //选项
    RadioButton[] mRadioButton = new RadioButton[4];
    RadioButton[] mRadioButtonX = new RadioButton[4];
    //下一题
    private Button btn_down;
    //详情
    private TextView tv_result;
    //容器
    private RadioGroup mRadioGroup;
    private LinearLayout mLinearLayout;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private int Wrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData(Constant.QT);
    }
    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        mRadioButton[0] = (RadioButton) findViewById(R.id.RadioA);
        mRadioButton[1] = (RadioButton) findViewById(R.id.RadioB);
        mRadioButton[2] = (RadioButton) findViewById(R.id.RadioC);
        mRadioButton[3] = (RadioButton) findViewById(R.id.RadioD);
        mRadioButtonX[0] = (RadioButton) findViewById(R.id.RadioXA);
        mRadioButtonX[1] = (RadioButton) findViewById(R.id.RadioXB);
        mRadioButtonX[2] = (RadioButton) findViewById(R.id.RadioXC);
        mRadioButtonX[3] = (RadioButton) findViewById(R.id.RadioXD);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        btn_down = (Button) findViewById(R.id.btn_down);
        tv_result = (TextView) findViewById(R.id.tv_result);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        mLinearLayout = (LinearLayout) findViewById(R.id.mRadioGroupX);
    }
    private void initData(String url){
        dialog = Progress_Dialog.CreateProgressDialog(this);
        dialog.show();
        if (HttpUtils.isNetworkConnected(this)) {
            HttpUtils.get(url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        //QT = dataf.jsonToArrayList(responseString, Problem.class);
                        dialog.dismiss();
                        initDB(dataf.jsonToArrayList(responseString, Problem.class));
                    } catch (JsonSyntaxException e) {
                        Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                }
            });
        }
    }
    private String TypeTK(String str){
        String mm=null;
        if(str.equals("N")){
            mm="单选题";
        }else if(str.equals("O")){
            mm="多选题";
        }else if(str.equals("P")){
            mm="判断题";
        }
        return mm;
    }
    /**
     * 初始化数据库服务
     */
    private void initDB( List<Problem> alist) {
        final List<Problem> list=alist;//initData(url);
        count = list.size();
        tv2.setText("|共"+count+"题");
        tv1.setText("第1题");
        tv4.setText("答错0题");
        corrent = 0;
        Wrong=0;
        tv_title.setText(list.get(corrent).getPro());
        tv_result.setText(list.get(corrent).getDan());
        tv3.setText(TypeTK(list.get(corrent).getType()));
        //下一题
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                String DA="";
                for(int i=0;i<4;i++){
                    if(list.get(corrent).getType().equals("O")){
                        if(mRadioButtonX[i].isChecked() == true) {
                            DA += mRadioButtonX[i].getText().toString();
                        }
                    }else if(mRadioButton[i].isChecked() == true) {
                        DA += mRadioButton[i].getText().toString();
                    }
                }
                if(DA.equals("")){
                    new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("此题还未作答，请做出您的选择后进行下一题")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(DA.equals(list.get(corrent).getDan())){
                    if (corrent < count - 1) {
                        corrent++;
                        tv_title.setText(list.get(corrent).getPro());
                        tv_result.setText(list.get(corrent).getDan());
                        tv1.setText("第"+(corrent+1)+"题");
                        tv3.setText(TypeTK(list.get(corrent).getType()));
                        if(list.get(corrent).getType().equals("N")){
                            mLinearLayout.setVisibility(View.GONE);
                            mRadioGroup.setVisibility(View.VISIBLE);
                            mRadioButton[2].setVisibility(View.VISIBLE);
                            mRadioButton[3].setVisibility(View.VISIBLE);
                        }
                        if(list.get(corrent).getType().equals("P")){
                            mLinearLayout.setVisibility(View.GONE);
                            mRadioGroup.setVisibility(View.VISIBLE);
                            mRadioButton[2].setVisibility(View.GONE);
                            mRadioButton[3].setVisibility(View.GONE);
                        }
                        if(list.get(corrent).getType().equals("O")){
                            mLinearLayout.setVisibility(View.VISIBLE);
                            mRadioGroup.setVisibility(View.GONE);
                            mRadioButtonX[0].setChecked(false);
                            mRadioButtonX[1].setChecked(false);
                            mRadioButtonX[2].setChecked(false);
                            mRadioButtonX[3].setChecked(false);
                        }
                        mRadioGroup.clearCheck();
                    }else{
                        new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否退出？得分:"+(count-Wrong+1))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setNegativeButton("取消",null).show();
                    }
                }else{
                    Wrong++;
                    tv4.setText("答错"+Wrong+"题");
                    new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("答案错误，正确答案为"+list.get(corrent).getDan())
                            .setPositiveButton("下一题", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (corrent < count - 1) {
                                        corrent++;
                                        tv_title.setText(list.get(corrent).getPro());
                                        tv_result.setText(list.get(corrent).getDan());
                                        tv1.setText("第"+(corrent+1)+"题");
                                        tv3.setText(TypeTK(list.get(corrent).getType()));
                                        if(list.get(corrent).getType().equals("N")){
                                            mLinearLayout.setVisibility(View.GONE);
                                            mRadioGroup.setVisibility(View.VISIBLE);
                                            mRadioButton[2].setVisibility(View.VISIBLE);
                                            mRadioButton[3].setVisibility(View.VISIBLE);
                                        }
                                        if(list.get(corrent).getType().equals("P")){
                                            mLinearLayout.setVisibility(View.GONE);
                                            mRadioGroup.setVisibility(View.VISIBLE);
                                            mRadioButton[2].setVisibility(View.GONE);
                                            mRadioButton[3].setVisibility(View.GONE);
                                        }
                                        if(list.get(corrent).getType().equals("O")){
                                            mLinearLayout.setVisibility(View.VISIBLE);
                                            mRadioGroup.setVisibility(View.GONE);
                                            mRadioButtonX[0].setChecked(false);
                                            mRadioButtonX[1].setChecked(false);
                                            mRadioButtonX[2].setChecked(false);
                                            mRadioButtonX[3].setChecked(false);
                                        }
                                        mRadioGroup.clearCheck();
                                    }else{
                                        new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否退出？")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                }).setNegativeButton("取消",null).show();
                                    }
                                }
                            }).show();
                }
            }
        });
    }
}
