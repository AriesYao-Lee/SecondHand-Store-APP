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

public class ManageInfo extends Activity{
    EditText etPNum,etEmail;
    Button bnEdit,bnBack;
    Integer userId;
    @Override


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageinfo);
        etPNum = (EditText)findViewById(R.id.editphone);
        etEmail = (EditText)findViewById(R.id.editemail);
        bnEdit = (Button)findViewById(R.id.bnEdit);
        bnBack = (Button)findViewById(R.id.bnCancelEdit);

        Intent data = getIntent();
        userId = data.getIntExtra("userId", 0);


        etEmail.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){
                    if(!etEmail.getText().toString().trim().contains("@")){
                        Toast.makeText(ManageInfo.this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ManageInfo.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email",etEmail.getText().toString().trim());
                map.put("phone",etPNum.getText().toString().trim());
                map.put("userId",String.valueOf(userId));

                try {
                    String url = HttpUtil.BASE_URL + "editUserInfo";
                    JSONObject editinfo = new JSONObject(HttpUtil.AnotherpostRequest(url, map));
                    if(editinfo.getInt("editSuccess")>0)
                        Toast.makeText(ManageInfo.this, "Edit Success!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ManageInfo.this, "Edit failed!", Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(ManageInfo.this,"Server is down!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        bnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putInt("userId", userId);
                Intent intent = new Intent(ManageInfo.this,PersonalInfo.class);
                intent.putExtras(data);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isPhone(String inputText) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }
}
