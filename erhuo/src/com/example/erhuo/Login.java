package com.example.erhuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class Login extends Activity implements RongIM.UserInfoProvider{
	EditText etName,etPass;
	Button bnLogin, bnCancel;
	private int userId;
	private String token;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.login);
		etName = (EditText)findViewById(R.id.userEditText);
		etPass = (EditText)findViewById(R.id.pwdEditText);
		bnLogin = (Button)findViewById(R.id.bnLogin);
		bnCancel = (Button)findViewById(R.id.bnCancel);
		bnCancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(Login.this, Register.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});//(new HomeListener(this));
		
		bnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(validate())
				{
					if(loginPro())
					{
						Bundle data = new Bundle();
						data.putInt("userId", userId);
						App appErhuo = ((App) getApplicationContext());
						appErhuo.setUserId(userId);
						data.putString("userName", etName.getText().toString());
						Intent intent = new Intent(Login.this,
							MainPage.class);
						intent.putExtras(data);
						startActivity(intent);
						finish();
					}
					else
					{
						DialogUtil.showDialog(Login.this,
							"sth happened",false);
					}
				}
			}


		});
	}
	private boolean validate() 
	{

		String username = etName.getText().toString().trim();
		if(username.equals(""))
		{
			DialogUtil.showDialog(this, "username!",false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if(pwd.equals(""))
		{
			DialogUtil.showDialog(this, "password!",false);
			return false;
		}
		return true;
	}
	private boolean loginPro()
	{
		String username = etName.getText().toString().trim();
		String pwd = etPass.getText().toString().trim();
		JSONObject jsonObj;
		JSONObject  tokenOBJ;
		try
		{
			jsonObj = query(username,pwd);
			//userId > 0
			userId = jsonObj.getInt("userId"); 
			if(userId > 0){
				
				tokenOBJ = jsonObj.getJSONObject("token");
				
				token=tokenOBJ.getString("token");
				
				RongIM.connect(token, new RongIMClient.ConnectCallback() {
		            @Override
		            public void onTokenIncorrect() {
		            	Toast.makeText(Login.this,"token error",
		                        Toast.LENGTH_SHORT).show();
		            }
		            @Override
		            public void onSuccess(String s) {
		                Toast.makeText(Login.this,"Login Success",
		                        Toast.LENGTH_SHORT).show();
		            }
		            @Override
		            public void onError(RongIMClient.ErrorCode errorCode) {
		            	Log.d("LoginActivity", "--onError" + errorCode);
		            }
		        });
				
				Log.i("token", token);
				return true;
			}
		}
		catch(Exception e)
		{
			DialogUtil.showDialog(this, "Error",false);
			e.printStackTrace();
		}
		return false;
	}

	private JSONObject query(String username, String pwd) 
			throws Exception
	{

		Map<String,String> map = new HashMap<String,String>();
		map.put("user", username);
		map.put("pass", pwd);
		String url = HttpUtil.BASE_URL + "checkLogin";

		String res = HttpUtil.AnotherpostRequest(url, map);
		Log.i("exp",res);
		return new JSONObject(res);
		//return new JSONObject("{\"userId\":1}");
	}
	
	
	private List<Friend> userIDList;
	private void initUserInfo(){
        userIDList = new ArrayList<Friend>();
        RongIM.setUserInfoProvider(this,true);
    }

    @Override
    public UserInfo getUserInfo(String s){
        for(Friend i : userIDList){
            return new UserInfo(i.getUserId(),i.getUserName(), Uri.parse(i.getPortraitUri()));
        }
        return null;
    }
}
