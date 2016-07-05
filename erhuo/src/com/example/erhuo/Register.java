package com.example.erhuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class Register extends Activity{
	EditText etR_Name,etPwd,etRPwd,etPNum,etEmail;
	Button bnRegister,bnBack;
	@Override


	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		etR_Name = (EditText)findViewById(R.id.reg_name);
		etPwd = (EditText)findViewById(R.id.reg_psw);
		etRPwd = (EditText)findViewById(R.id.rereg_psw);
		etPNum = (EditText)findViewById(R.id.reg_phonenum);
		etEmail = (EditText)findViewById(R.id.reg_email);
		bnRegister = (Button)findViewById(R.id.bnRegister);
		bnBack = (Button)findViewById(R.id.bnBack);

		etR_Name.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				//失去焦点时判断
				if (!hasFocus) {
					if (etR_Name.getText().toString().trim().length() < 4) {
						Toast.makeText(Register.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		etPwd.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(etPwd.getText().toString().trim().length()<6){
						Toast.makeText(Register.this, "密码不能小于8个字符", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		etRPwd.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(!etRPwd.getText().toString().trim().equals(etPwd.getText().toString().trim())){
						Toast.makeText(Register.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});


		etEmail.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(!etEmail.getText().toString().trim().contains("@")){
						Toast.makeText(Register.this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		etPNum.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(!isPhone(etPNum.getText().toString().trim())){
						Toast.makeText(Register.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		bnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if((etR_Name.getText().toString()).equals("")||(etPwd.getText().toString()).equals("")){
					Toast.makeText(getApplicationContext(),"请填完信息",Toast.LENGTH_LONG).show();
				}
				else{
					if(registerPro()){
						Intent intent = new Intent(Register.this,Login.class);
						startActivity(intent);
						finish();
					}
				}
			}
		});

		bnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Register.this,Login.class);
				Log.i("jump", i.toString());
				startActivity(i);
				finish();
			}
		});
	}
	private boolean registerPro()
	{
		String username = etR_Name.getText().toString().trim();
		String pwd = etPwd.getText().toString().trim();
		String phoneNum = etPNum.getText().toString().trim();
		String email = etEmail.getText().toString().trim();
		JSONObject jsonObj;
		try
		{
			jsonObj = query(username,pwd,phoneNum,email);
			//userId > 0
			if(jsonObj.getInt("regSuccess") > 0)
				return true;
		}
		catch(Exception e)
		{
			DialogUtil.showDialog(this, "regster failed",false);
			e.printStackTrace();
		}
		return false;
	}

	private JSONObject query(String username, String pwd,String phone,String email)
			throws Exception
	{
		// 定义发送请求的方法
		//使用Map封装请求参数
		Map<String,String> map = new HashMap<String,String>();
		map.put("user", username);
		map.put("pass", pwd);
		map.put("phone", phone);
		map.put("email", email);
		//定义发送请求的url
		//String url = HttpUtil.BASE_URL + "login.jsp";
		String url = HttpUtil.BASE_URL + "Register";
		//String post = setPost(map);
		//发送请求
		//String res = HttpUtil.postRequest(url, map);
		String res = HttpUtil.AnotherpostRequest(url, map);
		Log.i("exp",res);
		return new JSONObject(res);
		//return new JSONObject("{\"userId\":1}");
	}
	private boolean isPhone(String inputText) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(inputText);
		return m.matches();
	}
}
