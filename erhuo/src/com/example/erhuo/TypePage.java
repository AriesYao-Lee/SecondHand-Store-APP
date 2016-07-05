package com.example.erhuo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TypePage extends Activity {
	Button bnMain;
	ListView ItemList;
	TextView TypeTitle;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type_page);
		// 获取界面布局上的两个按钮
		bnMain = (Button) findViewById(R.id.bn_home);
		ItemList = (ListView) findViewById(R.id.kindList);
		TypeTitle = (TextView)findViewById(R.id.type_title);
		Intent i = getIntent();
		final long kindId = (Long)i.getLongExtra("kindId",0);
		final String TypeName = (String)i.getSerializableExtra("kindName");
		final Integer userId = (Integer)i.getIntExtra("userId", 0);
		TypeTitle.setText(TypeName);

		bnMain.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Bundle data = new Bundle();
				data.putInt("userId", userId);
				Intent i = new Intent(TypePage.this, MainPage.class);
				i.putExtras(data);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "viewItem?kindId="+kindId+"&userId="+userId;
		Log.i("urlViewItem", url);
		try
		{
			//向指定URL发送请求，并把响应包装成JSONArray对象
			String res = HttpUtil.getRequest(url);
			Log.i("res", res);
			JSONArray jsonArray = new JSONArray(res);
			JSONArrayAdapter adapter  = new JSONArrayAdapter(
					TypePage.this,jsonArray,"itemName",true);
			// 把JSONArray对象包装成Adapter
			ItemList.setAdapter(adapter);
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(TypePage.this
					, "服务器响应异常，请稍后再试！" ,false);
			e.printStackTrace();
		}

		ItemList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent,View view,
									int position,long id)
			{
				JSONObject jsonObj = (JSONObject)ItemList.getAdapter()
						.getItem(position);
				Bundle data = new Bundle();
				try{
					int itemId = jsonObj.getInt("id");
					Log.i("itemId in TypePage", itemId+"");
					data.putInt("itemId", itemId);
					data.putLong("kindId", kindId);
					data.putString("kindName", TypeName);
					data.putInt("userId", userId);
				}
				catch(JSONException e){
					e.printStackTrace();
				}
				Intent i = new Intent(TypePage.this, ItemPage.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtras(data);
				startActivity(i);
			}
		});
	}
}