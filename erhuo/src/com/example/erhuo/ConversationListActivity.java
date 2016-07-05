package com.example.erhuo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import org.apache.http.util.ByteArrayBuffer;
/**
 * Created by yf on 2016/5/26.
 */
public class ConversationListActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversationlist);
        mConversationList = initConversationList();

        mFragment.add(mConversationList);
        mFragment.add(FriendFragment.getInstance());

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
    }


    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    private List<Fragment> mFragment = new ArrayList<Fragment>();
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
