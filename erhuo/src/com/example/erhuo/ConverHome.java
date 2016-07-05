package com.example.erhuo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by yf on 2016/5/25.
 */
public class ConverHome extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    private List<Fragment> mFragment = new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceStage){
        super.onCreate(savedInstanceStage);
        setContentView(R.layout.converactivity);
        mConversationList = initConversationList();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //mFragment.add(HomeFragment.getInstance());
        mFragment.add(mConversationList);
        //mFragment.add(FriendFragment.getInstance());

        mFragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);

    }

    private Fragment initConversationList(){
        if(mConversationFragment==null){
            ConversationListFragment listFragment = ConversationListFragment.getInstance();
            Uri uri = Uri.parse("rong://"+getApplicationInfo().packageName).buildUpon()
                    .appendPath("coversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),"false")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(),"false")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(),"false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(),"false")
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        }
        else {
            return mConversationFragment;
        }
    }
}