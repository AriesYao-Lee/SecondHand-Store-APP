package com.example.erhuo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.erhuo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderArrayAdapter extends BaseAdapter
{
    
    private JSONArray orderArray;
    private Context ctx;
    private int user;
    public OrderArrayAdapter(JSONArray orderArray
            ,Context ctx,int user)
    {
        this.orderArray = orderArray;
        this.ctx = ctx;
        this.user = user;
    }
    @Override
    public int getCount()
    {
        
        return orderArray.length();
    }

    @Override
    public Object getItem(int position)
    {
        
        return orderArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position)
    {
        try
        {
            return ((JSONObject) getItem(position)).getInt("id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent)
    {
        
        LinearLayout container = new LinearLayout(ctx);
        
        container.setOrientation(1);
       
        LinearLayout linear = new LinearLayout(ctx);
        
        linear.setOrientation(0);
        
        ImageView iv = new ImageView(ctx);
        iv.setPadding(10, 0, 20, 0);
        iv.setImageResource(R.drawable.item);
        
        linear.addView(iv);

        
        TextView tv = new TextView(ctx);
        try
        {
            
            String ItemName = ((JSONObject)getItem(position))
                    .getString("itemName");
            int buyer =  ((JSONObject)getItem(position))
                    .getInt("buyerId");
            int seller = ((JSONObject)getItem(position))
                    .getInt("ownerId");
            
            if(seller==user)
            	ItemName = ItemName + "(卖出)";
            else
            	ItemName = ItemName + "(买入)";
            
            tv.setText(ItemName);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        tv.setTextSize(20);
        
        linear.addView(tv);
        container.addView(linear);
        
        TextView timeView = new TextView(ctx);
        timeView.setPadding(30, 0, 0, 0);
        try
        {
            
            String ordertime = ((JSONObject)getItem(position))
                    .getString("time");
            timeView.setText(ordertime);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        timeView.setTextSize(16);
        container.addView(timeView);
        
        return container;
    }
}
