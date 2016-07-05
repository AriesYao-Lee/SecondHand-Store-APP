package com.example.erhuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.erhuo.adapter.OrderArrayAdapter;

public class PersonalOrder extends Activity{
    Button bnFavored, bnDelivered, bnBack;
    ListView ItemList;
    int userid;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_order);
        
        bnFavored = (Button) findViewById(R.id.bn_O_to_Favored);
        bnDelivered = (Button) findViewById(R.id.bn_O_to_Delivered);
        bnBack = (Button) findViewById(R.id.bn_O_Back);
        ItemList = (ListView) findViewById(R.id.orderList);
        Intent pre = getIntent();
        userid = pre.getIntExtra("userId",0);
        
        bnFavored.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	Bundle data = new Bundle();
                data.putInt("userId",userid);
            	Intent i = new Intent(PersonalOrder.this, PersonalFavored.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	i.putExtras(data);
                startActivity(i);
            }
        });

        bnDelivered.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	Bundle data = new Bundle();
                data.putInt("userId",userid);
            	Intent i = new Intent(PersonalOrder.this, PersonalDelivered.class);
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
                data.putInt("userId",userid);
            	Intent i = new Intent(PersonalOrder.this, MainPage.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	i.putExtras(data);
                startActivity(i);
            }
        });

        //Intent i = getIntent();
        //final long kindId = (Long)i.getLongExtra("kindId",0);
        //final String TypeName = (String)i.getSerializableExtra("kindName");
        //final Integer userId = (Integer)i.getIntExtra("userId", 0);

        
        String url = HttpUtil.BASE_URL + "viewOrder?userId="+userid;
        Log.i("urlViewItem", url);
        try
        {
            
            String res = HttpUtil.getRequest(url);
            Log.i("res", res);
            JSONArray jsonArray = new JSONArray(res);
            OrderArrayAdapter adapter  = new OrderArrayAdapter(
                    jsonArray,PersonalOrder.this,userid);
            
            ItemList.setAdapter(adapter);
        }
        catch (Exception e)
        {
            DialogUtil.showDialog(PersonalOrder.this
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
                    int orderid = jsonObj.getInt("id");
                    //Log.i("orderId", orderid+"");
                    data.putInt("orderId", orderid);
                    //data.putLong("kindId", kindId);
                    //data.putString("kindName", TypeName);
                    data.putInt("userId", userid);
                    Log.i("userId in order", userid+"");
                    //Toast.makeText(PersonalOrder.this, "vi111efail", Toast.LENGTH_LONG).show();
                }
                catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(PersonalOrder.this, "fail", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(PersonalOrder.this, OrderDetails.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(data);
                startActivity(i);
            }
        });
    }
}
