package com.example.erhuo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
//import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ResultPage extends Activity {
	String query;
	Button bnMain;
	ListView ItemList;
	Integer userId;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_page);
		Intent intent =getIntent();
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			query = intent.getStringExtra(SearchManager.QUERY) ; //获取用户输入的关键字
		}
		bnMain = (Button) findViewById(R.id.bn_home);
		ItemList = (ListView) findViewById(R.id.resultList);
		//Bundle data = intent.getExtras();
		//userId = (Integer)data.getInt("userId");
		App appErhuo = ((App) getApplicationContext());
		userId = appErhuo.getUserId();
		Log.i("result userId", userId+"");
		bnMain.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Bundle data = new Bundle();
				data.putInt("userId", userId);
				Intent i = new Intent(ResultPage.this, MainPage.class);
				i.putExtras(data);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "searchItem?query="+query+"&userId="+userId;
		//Log.i("urlViewItem", url);
		try
		{
			//向指定URL发送请求，并把响应包装成JSONArray对象
			String res = HttpUtil.getRequest(url);
			//Log.i("res", res);
			JSONArray jsonArray = new JSONArray(res);
			JSONArrayAdapter adapter  = new JSONArrayAdapter(
					ResultPage.this,jsonArray,"itemName",true);
			// 把JSONArray对象包装成Adapter
			ItemList.setAdapter(adapter);
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(ResultPage.this
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
					//data.putLong("kindId", kindId);
					//data.putString("kindName", TypeName);
					data.putInt("userId", userId);
				}
				catch(JSONException e){
					e.printStackTrace();
				}
				Intent i = new Intent(ResultPage.this, ItemPage.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtras(data);
				startActivity(i);
			}
		});
	}
}
