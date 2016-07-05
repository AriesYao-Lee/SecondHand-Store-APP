package com.example.erhuo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import android.widget.AdapterView;


public class  DeliverItem extends Activity {

    Button bnConfirm,bnCancel,bnPicture;
    EditText itemname,itemdescription,itemprice;
    private static final String TAG = "deliveritem";
    Spinner itemtype;
    ImageView imageview;
    Item item;
    byte [] tmp={0,0};
    int userid;
    int type=0;
    File f;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static String requestURL = HttpUtil.BASE_URL+"ImageServlet";
    int request=-1;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_item);
        
        itemtype = (Spinner) findViewById(R.id.deliveritemtype);
        itemtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                type=pos+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        bnConfirm = (Button) findViewById(R.id.bnDeliverItem);

        bnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemname = (EditText) findViewById(R.id.deliveritemname);
                itemdescription = (EditText) findViewById(R.id.deliveritemdescription);
                itemprice = (EditText) findViewById(R.id.deliveritemprice);
                

                
                if (f != null) {
                	new Thread(){
                		public void run()
                		{
                			try{
                				request = UploadUtil.uploadFile(f, requestURL,DeliverItem.this);
                				Log.i("imgSrc First", request+"");
                				//Toast.makeText(DeliverItem.this,"result"+String.valueOf(request),Toast.LENGTH_LONG).show();
                			}
                			catch(Exception e){
                				e.printStackTrace();
                			}
                		}
                	}.start();
                    
                }
                while(request==-1){}
                Log.i("imgSrc", request+"");
                request--;
                String imgSrc;
                if(request>0){
                	imgSrc = "img_"+request+".jpg";
                }
                else{
                	imgSrc = "img_0.jpg";
                }
                item = new Item(0, itemname.getText().toString(),
                        itemdescription.getText().toString(),
                        Double.parseDouble(itemprice.getText().toString()),
                        String.valueOf(type),tmp);


                Intent pre = getIntent();
                userid = pre.getIntExtra("userId",0);

                Map<String,String> map = new HashMap<String,String>();
                map.put("itemname",itemname.getText().toString().trim());
                map.put("itemdesc",itemdescription.getText().toString().trim());
                map.put("itemprice",itemprice.getText().toString().trim());
                map.put("typeId",String.valueOf(type));
                map.put("sellerid",String.valueOf(userid));
                //Log.i("userID", userid+"");
                map.put("status",String.valueOf(1));
                map.put("imgSrc",imgSrc);
                
                try {
                    String url = HttpUtil.BASE_URL + "deliver";
                    JSONObject jsonObj = new JSONObject(HttpUtil.AnotherpostRequest(url, map));
                    if(jsonObj.getInt("addItemSuccess") > 0)
                    	Toast.makeText(DeliverItem.this, "Item Delivered!", Toast.LENGTH_LONG).show();
                    else
                    	Toast.makeText(DeliverItem.this, "Deliver failed", Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(DeliverItem.this,"Server is down!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                
                
            }
        });

        bnCancel = (Button) findViewById(R.id.bnCancelDeliver);

        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent pre = getIntent();
                //int userid = pre.getIntExtra("id",0);
                Bundle data = new Bundle();
                data.putInt("userId",userid);
                Intent intent = new Intent(DeliverItem.this
                  ,MainPage.class);
                intent.putExtras(data);
                startActivity(intent);
                finish();
            }
        });

        bnPicture = (Button) findViewById(R.id.choosepicture);

        bnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        switch (requestCode) {

            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 150);
                break;

            case PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");


        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);


        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    @SuppressWarnings("deprecation")
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            saveBitmap(photo);
            Drawable drawable = new BitmapDrawable(photo);
            imageview = (ImageView) findViewById(R.id.imageView);
            imageview.setBackgroundDrawable(drawable);
        }
    }
    
    public void saveBitmap(Bitmap bm) {

        try {
            f = new File(Environment.getExternalStorageDirectory(),"up.jpg");
        }
        catch(Exception e)
         { Toast.makeText(DeliverItem.this, e.toString(), Toast.LENGTH_LONG).show();}
         if (f.exists()) {
             f.delete();
         }
         try {
             FileOutputStream out = new FileOutputStream(f);
             bm.compress(Bitmap.CompressFormat.PNG, 90, out);
             out.flush();
             out.close();
         } catch (FileNotFoundException e) {
             // TODO Auto-generated catch block
             Log.e(TAG,e.toString());
             e.printStackTrace();

         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }

     }

}

