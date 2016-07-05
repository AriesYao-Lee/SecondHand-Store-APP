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

public class FavoredArrayAdapter extends BaseAdapter
{
    // 需要包装的JSONArray
    private JSONArray favoredArray;
    private Context ctx;
    public FavoredArrayAdapter(JSONArray favoredArray
            ,Context ctx)
    {
        this.favoredArray = favoredArray;
        this.ctx = ctx;
    }
    @Override
    public int getCount()
    {
        // 返回ListView包含列表项数量
        return favoredArray.length();
    }

    @Override
    public Object getItem(int position)
    {
        // 获取指定列表项所包装的JSONObject
        return favoredArray.optJSONObject(position);
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
        // 定义一个线性布局管理器
        LinearLayout container = new LinearLayout(ctx);
        // 设置为水平的线性布局管理器
        container.setOrientation(1);
        // 定义一个线性布局管理器
        LinearLayout linear = new LinearLayout(ctx);
        // 设置为水平的线性布局管理器
        linear.setOrientation(0);
        // 创建一个ImageView
        ImageView iv = new ImageView(ctx);
        iv.setPadding(10, 0, 20, 0);
        iv.setImageResource(R.drawable.item);
        // 将图片添加到LinearLayout中
        linear.addView(iv);

        // 创建一个TextView
        TextView tv = new TextView(ctx);
        try
        {
            // 获取JSONArray数组元素的kindName属性
            String kindName = ((JSONObject)getItem(position))
                    .getString("kindName");
            // 设置TextView所显示的内容
            tv.setText(kindName);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        tv.setTextSize(20);
        // 将TextView添加到LinearLayout中
        linear.addView(tv);
        container.addView(linear);
        // 定义一个文本框来显示种类描述
        TextView descView = new TextView(ctx);
        descView.setPadding(30, 0, 0, 0);
        try
        {
            // 获取JSONArray数组元素的kindDesc属性
            String kindDesc = ((JSONObject)getItem(position))
                    .getString("kindDesc");
            descView.setText(kindDesc);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        descView.setTextSize(16);
        container.addView(descView);
        return container;
    }
}
