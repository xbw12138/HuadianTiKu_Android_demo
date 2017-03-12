package com.xbw.huadian.model;

/**
 * Created by xubowen on 2017/3/3.
 */
public class Problem {
    private String pro;
    private String daan;
    private String type;
    private String selectedAnswer;
    public int selected;
    public void setPro(String str){
        this.pro=str;
    }
    public String getPro(){
        return pro;
    }
    public void setDan(String str){
        this.daan=str;
    }
    public String getDan(){
        return daan;
    }
    public void setType(String str){
        this.type=str;
    }
    public String getType(){
        return type;
    }
    public void setselectedAnswer(String str){
        this.selectedAnswer=str;
    }
    public String getselectedAnswer(){
        return selectedAnswer;
    }

}
