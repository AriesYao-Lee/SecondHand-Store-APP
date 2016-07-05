package com.example.erhuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalDelivered extends Activity{
    Button bnFavored, bnOrder, bnBack;
    ListView ItemList;
    int userId;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_delivered);
        // 鑾峰彇鐣岄潰甯冨眬涓婄殑鎸夐挳
        bnFavored = (Button) findViewById(R.id.bn_D_to_Favored);
        bnOrder = (Button) findViewById(R.id.bn_D_to_Order);
        bnBack = (Button) findViewById(R.id.bn_D_Back);
        ItemList = (ListView) findViewById(R.id.deliveredList);
        Intent pre = getIntent();
        userId = pre.getIntExtra("userId",0);
        
        bnFavored.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	Bundle data = new Bundle();
                data.putInt("userId",userId);
            	Intent i = new Intent(PersonalDelivered.this, PersonalFavored.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	i.putExtras(data);
                startActivity(i);
            }
        });

        bnOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	Bundle data = new Bundle();
                data.putInt("userId",userId);
            	Intent i = new Intent(PersonalDelivered.this, PersonalOrder.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	i.putExtras(data);
            	startActivity(i);
            }
        });

        bnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	Bundle data = new Bundle();
                data.putInt("userId",userId);
            	Intent i = new Intent(PersonalDelivered.this, MainPage.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	i.putExtras(data);
                startActivity(i);
            }
        });

        //Intent i = getIntent();
        //final long kindId = (Long)i.getLongExtra("kindId",0);
        //final String TypeName = (String)i.getSerializableExtra("kindName");
        //final Integer userId = (Integer)i.getIntExtra("userId", 0);

        // 瀹氫箟鍙戦�佽姹傜殑URL
        String url = HttpUtil.BASE_URL + "viewDeliverItem?userId="+userId;
        Log.i("personalDeliver", url);
        try
        {
            //鍚戞寚瀹歎RL鍙戦�佽姹傦紝骞舵妸鍝嶅簲鍖呰鎴怞SONArray瀵硅薄
            String res = HttpUtil.getRequest(url);
            Log.i("deliverRes", res);
            JSONArray jsonArray = new JSONArray(res);
            JSONArrayAdapter adapter  = new JSONArrayAdapter(
                    PersonalDelivered.this,jsonArray,"itemName",true);
            // 鎶奐SONArray瀵硅薄鍖呰鎴怉dapter
            ItemList.setAdapter(adapter);
        }
        catch (Exception e)
        {
            DialogUtil.showDialog(PersonalDelivered.this
                    , "服务器响应异常，请稍后再试！" ,false);
            e.printStackTrace();
        }

        ItemList.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
                Intent i = new Intent(PersonalDelivered.this, ItemPage.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(data);
                startActivity(i);
            }
        });
    }
}
