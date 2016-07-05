package com.example.erhuo;

import android.app.Application;
import io.rong.imkit.RongIM;

public class App extends Application{
	private int loginUserId;
	
	public int getUserId(){
		return loginUserId;
	}
	public void setUserId(int id){
		loginUserId=id;
	}
	
	@Override
    public void onCreate(){
        super.onCreate();
        RongIM.init(this);
    }
}
