package com.example.erhuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileReader;

import io.rong.imkit.RongIM;

/**
 * Created by yf on 2016/5/25.
 */
public class FriendFragment extends Fragment{

    public static FriendFragment instance =  null;
    public static FriendFragment getInstance(){
        if(instance == null){
            instance = new FriendFragment();
        }
        return instance;
    }

    private View mView;
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStage){
        mView = inflater.inflate(R.layout.friend_fragment,null);
        mButton = (Button) mView.findViewById(R.id.friend);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(getActivity(),"yf_test2","chat");
                }
            }
        });

        return mView;
    }

}
