package com.example.erhuo;
import android.text.TextUtils;
import java.io.Serializable;

/**
 * Created by yf on 2016/5/25.
 */
public class Friend implements Serializable{
    private String userId;
    private String nickname;
    private String portrait;
    private boolean isSeleted = false;
    private boolean isAdd = false;

    public Friend(){

    }

    public Friend(String userId,String nickname, String portrait){
        this.userId=userId;
        this.nickname=nickname;
        this.portrait=portrait;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPortraitUri() {
        return nickname;
    }



}
