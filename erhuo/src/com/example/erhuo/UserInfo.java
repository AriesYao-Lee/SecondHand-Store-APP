package com.example.erhuo;

//import android.provider.ContactsContract;

/**
 * Created by cxdn on 2016/5/23.
 */
public class UserInfo {
    String name;
    String password;
    String phonenumber;
    String email;
    int id;

    UserInfo(){};

    UserInfo(String name,String password,String phonenumber,String email,int id)
    {
        this.name=name;
        this.password=password;
        this.phonenumber=phonenumber;
        this.email=email;
        this.id=id;
    }

}
