package com.example.erhuo;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.rong.imkit.RongIM;

public class OrderDetails extends Activity{
    TextView tvOrderid, tvPrice, tvTime, tvPlace, tvNameTitle, tvName, tvTelTitle, tvTel;
    Button bnConfirm, bnContact,bnCancel;
    Integer orderId, userId;
    Double  price;
    String time, place, nameTitle, name, telTitle, tel;
    int isBuyer,Seller,Buyer;//1锟斤拷示user锟斤拷buyer,-1锟斤拷示锟斤拷seller
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        tvOrderid = (TextView) findViewById(R.id.orderid);
        tvPrice = (TextView) findViewById(R.id.price);
        tvTime = (TextView) findViewById(R.id.time);
        tvPlace = (TextView) findViewById(R.id.place);
        tvNameTitle = (TextView) findViewById(R.id.name_title);
        tvName = (TextView) findViewById(R.id.name);
        tvTelTitle = (TextView) findViewById(R.id.tel_title);
        tvTel = (TextView) findViewById(R.id.tel);
        bnConfirm = (Button) findViewById(R.id.bnConfirm);
        bnContact = (Button) findViewById(R.id.bnContactInOrder);
        bnCancel = (Button) findViewById(R.id.bnOrderCancel);

        Intent data = getIntent();
        orderId = data.getIntExtra("orderId", 0);
        userId = data.getIntExtra("userId", 0);
        tvOrderid.setText(""+orderId);

        //通锟斤拷orderId锟接凤拷锟斤拷锟斤拷取锟截讹拷应锟斤拷锟斤拷源
        String url = HttpUtil.BASE_URL + "getOrder?orderId="+orderId+"&userId="+userId;
        try{
            JSONObject result,item,order,seller,buyer;

            String res = HttpUtil.getRequest(url);
            result=new JSONObject(res);
            isBuyer = result.getInt("isBuyer");
            item = result.getJSONObject("item");
            order = result.getJSONObject("order");
            seller = result.getJSONObject("seller");
            buyer = result.getJSONObject("buyer");
            time = order.getString("time");
            place = order.getString("place");
            price = item.getDouble("price");
            Seller = item.getInt("sellerId");
            Buyer = buyer.getInt("userId");

            if (isBuyer > 0){
                name = seller.getString("username");
                tel = seller.getString("phone");
            }
            else{
                name = buyer.getString("username");
                tel = order.getString("buyer_phone");
            }
        }
        catch (Exception e)
        {
            Toast.makeText(OrderDetails.this,"Server is down!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        tvPrice.setText(price.toString());
        tvTime.setText(time);
        tvPlace.setText(place);
        if (isBuyer > 0){
            tvNameTitle.setText("卖家用户名");
            tvTelTitle.setText("卖家联系方式");
        }
        else{
            tvNameTitle.setText("买家用户名");
            tvTelTitle.setText("买家联系方式");
        }
        tvName.setText(name);
        tvTel.setText(tel);

        bnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = HttpUtil.BASE_URL + "confirmOrder?orderId="+orderId+"&isBuyer="+isBuyer;
                try{
                    String res = HttpUtil.getRequest(url);
                    JSONObject result = new JSONObject(res);
                    int CS =result.getInt("confirmSuccess");
                    if(CS==1)
                        Toast.makeText(OrderDetails.this,"Confirm succeeded",Toast.LENGTH_LONG).show();
                    else if(CS==2)
                        Toast.makeText(OrderDetails.this,"Has confirmed",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(OrderDetails.this,"Confirm failed",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(OrderDetails.this,"Server is down!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = HttpUtil.BASE_URL + "cancelOrder?orderId="+orderId+"&isBuyer="+isBuyer;
                try{
                    String res = HttpUtil.getRequest(url);
                    JSONObject result = new JSONObject(res);
                    int CS =result.getInt("cancelSuccess");
                    if(CS==3)
                        Toast.makeText(OrderDetails.this,"Already Finished",Toast.LENGTH_LONG).show();
                    else if(CS==2)
                        Toast.makeText(OrderDetails.this,"Buyer Has confirmed",Toast.LENGTH_LONG).show();
                    else if(CS==0)
                        Toast.makeText(OrderDetails.this,"Cancel Success",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(OrderDetails.this,"Cancel Failed",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(OrderDetails.this,"Server is down!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        bnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RongIM.getInstance()!=null) {
                    if (userId == Seller)
                    {
                        RongIM.getInstance().startPrivateChat(OrderDetails.this, Buyer + "", "");
                    }
                    else{
                        RongIM.getInstance().startPrivateChat(OrderDetails.this, Seller + "", "");
                    }
                }
            }
        });
    }
}
