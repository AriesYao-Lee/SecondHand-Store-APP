package com.example.erhuo;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfo extends Activity{
	TextView tvEmail,tvName, tvPhone;
	Button bnEdit, bnBack;
	Integer userId;
	String name,phone,email;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		tvEmail= (TextView) findViewById(R.id.showemail);
		tvName = (TextView) findViewById(R.id.showname);
		tvPhone = (TextView) findViewById(R.id.showphone);
		bnEdit = (Button) findViewById(R.id.bnManageInfo);
		bnBack = (Button) findViewById(R.id.bnBackInInfo);

		Intent data = getIntent();
		userId = data.getIntExtra("userId", 0);

		String url = HttpUtil.BASE_URL + "getUserInfo?userId="+userId;
		try{
			JSONObject result;

			String res = HttpUtil.getRequest(url);
			result=new JSONObject(res);
			name= result.getString("username");
			email = result.getString("email");
			phone = result.getString("phone");



		}
		catch (Exception e)
		{
			Toast.makeText(PersonalInfo.this,"Server is down!",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		tvName.setText(name);
		tvPhone.setText(phone);
		tvEmail.setText(email);

		bnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putInt("userId", userId);
				Intent intent = new Intent(PersonalInfo.this
						,ManageInfo.class);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});


		bnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putInt("userId", userId);
				Intent intent = new Intent(PersonalInfo.this
						,MainPage.class);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
	}
}
