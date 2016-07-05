package com.example.erhuo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import io.rong.imkit.RongIM;
import  android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
//import org.json.JSONArray;
import org.json.JSONObject;

public class ItemPage extends Activity {

	Button bnFavorItem,bnPurchase,bnContactSeller;
	ImageView itempic;
	int itemid,sellerid,status,userId,hasFavored;//kindId
	String itemDesc,itemname,imgSrc;//kindName
	Double itemprice;
	TextView showname,showprice;
	Bitmap itembitmap;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_page);

		String url;
		try
		{
			Intent data = getIntent();
			//userId = data.getIntExtra("userId", 0);
			itemid = data.getIntExtra("itemId",0);
			App appErhuo = ((App) getApplicationContext());
			userId = appErhuo.getUserId();
			Log.i("itemId", itemid+"");
			//kindId = (int)data.getLongExtra("kindId", 0);
			//kindName = data.getStringExtra("kindName");
			//��ָ��URL�������󣬲�����Ӧ��װ��JSON����
			url = HttpUtil.BASE_URL + "getItem?itemId="+itemid+"&userId="+userId;
			final JSONObject json = new JSONObject(
					HttpUtil.getRequest(url));
			Log.i("itemRes",json.toString());
			//�����ݿ��ȡ��Ʒ����Ϣ
			//itemid = json.getInt("itemid");
			itemprice = json.getDouble("price");
			hasFavored = json.getInt("hasFavored");
			itemname = json.getString("itemName");
			sellerid = json.getInt("sellerId");
			itemDesc = json.getString("itemDesc");
			status = json.getInt("status");
			imgSrc = json.getString("imgSrc");
			String itemStatus;
			if(status==1) itemStatus = getString(R.string.status1);
			else if(status==2)itemStatus = getString(R.string.status2);
			else itemStatus = getString(R.string.status3);
			showname = (TextView) findViewById(R.id.itemname);
			showprice = (TextView) findViewById(R.id.itemprice);
			showname.setText(itemname+"("+itemDesc+")");
			showprice.setText(itemprice+"("+itemStatus+")");
			itempic = (ImageView)findViewById(R.id.itempic);
			final String urlpic = HttpUtil.BASE_URL + imgSrc;
			//if(hasFavored>0) bnFavorItem.setText("���ղ�");
			//if(userId==sellerid){
				//bnPurchase.setText(getString(R.string.no_buy));
				//bnContactSeller.setText(getString(R.string.no_contact));
			//}
			new Thread(){
        		public void run()
        		{
        			try{
        				itembitmap = getHttpBitmap(urlpic);
        				//Toast.makeText(DeliverItem.this,"result"+String.valueOf(request),Toast.LENGTH_LONG).show();
        			}
        			catch(Exception e){
        				e.printStackTrace();
        			}
        		}
        	}.start();
			while(itembitmap==null){}
			itempic.setImageBitmap(itembitmap);
		}
		catch (Exception e)
		{
			Toast.makeText(ItemPage.this,"Server is down!",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		bnFavorItem = (Button) findViewById(R.id.favoritem);

		bnFavorItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("userid", String.valueOf(userId));
				map.put("itemid", String.valueOf(itemid));
				Log.i("favorUser", userId+"");
				Log.i("favorItem", itemid+"");
				String url = HttpUtil.BASE_URL + "favorItem";
				try {
					JSONObject json;
					String res = HttpUtil.AnotherpostRequest(url, map);
					json=new JSONObject(res);
					int favorSuccess = json.getInt("favorSuccess");
					if(favorSuccess > 0){
						Toast.makeText(ItemPage.this,"Favor successfully",Toast.LENGTH_LONG).show();
						bnFavorItem.setText(R.string.favored);
					}
					else
						Toast.makeText(ItemPage.this,"Favor failure",Toast.LENGTH_LONG).show();
				}
				catch (Exception e)
				{
					Toast.makeText(ItemPage.this,"Server is down!",Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});

		bnPurchase = (Button) findViewById(R.id.purchaseitem);

		bnPurchase.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(userId==sellerid)
					Toast.makeText(ItemPage.this,"You are the owner",Toast.LENGTH_LONG).show();
				else {
					Bundle data = new Bundle();
					data.putInt("buyerid", userId);
					data.putInt("itemid", itemid);
					data.putInt("sellerid", sellerid);
					data.putDouble("itemprice", itemprice);
					Intent intent = new Intent(ItemPage.this
							, OrderPage.class);
					intent.putExtras(data);
					startActivity(intent);
				}
			}
		});

		bnContactSeller = (Button) findViewById(R.id.contactseller);
	
	    bnContactSeller.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
					if(userId==sellerid)
						Toast.makeText(ItemPage.this,"You are the owner",Toast.LENGTH_LONG).show();
	                else if(RongIM.getInstance()!=null){
	                    RongIM.getInstance().startPrivateChat(ItemPage.this, sellerid+"", "");
	                }
	            }
	     });


	}
	
	public static Bitmap getHttpBitmap(String url){
    	URL myFileURL;
    	Bitmap bitmap=null;
    	try{
    		myFileURL = new URL(url);
    		//�������
    		HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
    		//���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������
    		conn.setConnectTimeout(6000);
    		//�������û��������
    		conn.setDoInput(true);
    		//��ʹ�û���
    		conn.setUseCaches(false);
    		//�����п��ޣ�û��Ӱ��
    		//conn.connect();
    		//�õ�������
    		InputStream is = conn.getInputStream();
    		//�����õ�ͼƬ
    		bitmap = BitmapFactory.decodeStream(is);
    		//�ر�������
    		is.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
		return bitmap;
    	
    }

}
