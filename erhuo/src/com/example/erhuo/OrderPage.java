package com.example.erhuo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class OrderPage extends Activity {

    Button bnConfirm,bnCancel;
    EditText orderpalce,ordertime,phonenumber;
    Order order;
    int itemid;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderpage);

        bnConfirm = (Button) findViewById(R.id.bnConfirmOrder);

        bnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                int buyerid = intent.getIntExtra("buyerid",0);
                itemid = intent.getIntExtra("itemid",0);
                int sellerid = intent.getIntExtra("sellerid",0);
                Double itemprice = intent.getDoubleExtra("itemprice",0);
                orderpalce = (EditText) findViewById(R.id.orderplace);
                ordertime = (EditText) findViewById(R.id.ordertime);
                phonenumber = (EditText) findViewById(R.id.phonenumber);

                //注意，getText只是获取静态的值，因此要在检测到button按下之后才能获取editText的内容
                order = new Order(0, ordertime.getText().toString()
                        ,orderpalce.getText().toString(),itemid ,buyerid, sellerid, itemprice,0,phonenumber.getText().toString());
                //以上还有一些信息需要从数据库获取，暂时以0代替

                //...把订单加入数据库
                Map<String,String> map = new HashMap<String,String>();
                String time = ordertime.getText().toString().trim();
                time = time.replace(" ", "_");
                Log.i("timeInOrder", time);
                map.put("ordertime",time);
                map.put("orderplace",orderpalce.getText().toString());
                map.put("itemid",String.valueOf(itemid));
                map.put("buyerid",String.valueOf(buyerid));
                //map.put("sellerid",String.valueOf(sellerid));
                //map.put("itemprice",String.valueOf(itemprice));
                //map.put("status",String.valueOf(0));
                map.put("buyerphone",phonenumber.getText().toString());

                try {
                    String url = HttpUtil.BASE_URL + "sendOrder";
                    JSONObject orderResult = new JSONObject(HttpUtil.AnotherpostRequest(url, map));
                    Log.i("sendOrder", orderResult.toString());
                    if(orderResult.getInt("sendOrderSuccess")>0)
                        Toast.makeText(OrderPage.this, "Order Created!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(OrderPage.this, "Order failed!", Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(OrderPage.this,"Server is down!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        bnCancel = (Button) findViewById(R.id.bnCancelOrder);

        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putInt("itemId", itemid);
                Intent intent = new Intent(OrderPage.this
                        ,ItemPage.class);
                intent.putExtras(data);
                startActivity(intent);
                finish();
            }
        });


    }

}
