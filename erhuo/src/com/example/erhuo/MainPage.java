package com.example.erhuo;

import org.json.JSONArray;

import com.example.erhuo.adapter.KindArrayAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainPage extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	ListView TypeList;
	int userid;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		Intent pre = getIntent();
        userid = pre.getIntExtra("userId",0);
        Log.i("userId in mainPage", userid+"");
		

		TypeList = (ListView) findViewById(R.id.typeList);
		// ���巢�������URL
		String url = HttpUtil.BASE_URL + "viewKind";
		try
		{
			//��ָ��URL�������󣬲�����Ӧ��װ��JSONArray����
			final JSONArray jsonArray = new JSONArray(
						HttpUtil.getRequest(url));
			// ��JSONArray�����װ��Adapter
			TypeList.setAdapter(new KindArrayAdapter(jsonArray, MainPage.this));
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(MainPage.this
						, "��������Ӧ�쳣�����Ժ����ԣ�" ,false);
			e.printStackTrace();
		}
		TypeList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent,View view,
					int position,long id)
			{
				Bundle data = new Bundle();
				data.putLong("kindId", id);
				TextView kindName = null;
				try{
					ViewGroup firstLayout =(ViewGroup)((ViewGroup)view).getChildAt(0); 
					kindName= (TextView)firstLayout.getChildAt(1);
					Log.i("kindName", kindName.getText()+"");
				}
				catch (Exception e)
				{
					DialogUtil.showDialog(MainPage.this
								, "getChildAt(0)����" ,false);
					e.printStackTrace();
				}
				data.putString("kindName", kindName.getText()+"");
				data.putInt("userId", userid);
				Intent i = new Intent(MainPage.this, TypePage.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtras(data);
				startActivity(i);
			}
		});
	}
	@Override 
	public boolean onSearchRequested()
	{
		Bundle appData= new Bundle();  
	    appData.putInt("userId",userid);  
	    startSearch(null,false, appData,false);
		return true;
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			onSearchRequested();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_mydeliver) {
			Bundle data = new Bundle();
			data.putInt("userId",userid);
			Intent i = new Intent(MainPage.this, PersonalDelivered.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(data);
			startActivity(i);
		} else if (id == R.id.nav_myfavor) {
			Bundle data = new Bundle();
			data.putInt("userId",userid);
			Intent i = new Intent(MainPage.this, PersonalFavored.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(data);
			startActivity(i);

		} else if (id == R.id.nav_myorder) {
			Bundle data = new Bundle();
			data.putInt("userId",userid);
			Intent i = new Intent(MainPage.this, PersonalOrder.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(data);
			startActivity(i);

		} else if (id == R.id.nav_mymsg) {
			startActivity(new Intent(MainPage.this,ConverHome.class));

		} else if (id == R.id.nav_deliver) {
			Bundle data = new Bundle();
			data.putInt("userId",userid);
			Intent i = new Intent(MainPage.this, DeliverItem.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(data);
			startActivity(i);

		} else if (id == R.id.nav_quit) {
			Intent i = new Intent(MainPage.this, Login.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		else if (id == R.id.nav_manage) {
			Bundle data = new Bundle();
			data.putInt("userId",userid);
			Intent i = new Intent(MainPage.this, PersonalInfo.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtras(data);
			startActivity(i);
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

}
