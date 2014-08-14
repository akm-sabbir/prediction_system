package com.android.predictionsystem.fragments;


import com.android.predictionsystem.LoginActivity;
import com.android.predictionsystem.R;
import com.android.predictionsystem.SignUp;
import com.android.predictionsystem.Spinner_Tech;
import com.android.predictionsystem.broadcastrecievers.serviceBroadcstreciever;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
public class Fragment_exam extends Activity {

	private Button startBut = null;
	private TextView testView = null;
	private serviceBroadcstreciever service = null;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_layout_fragment);
		//service = new serviceBroadcstreciever();
		//registerReceiver(service, new IntentFilter( serviceBroadcstreciever.serviceRecieved));
	    startBut = (Button) findViewById(R.id.button1);
	    testView = (TextView)findViewById(R.id.startExam);
		startBut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment newFragement = new mainExamfragments();
				startBut.setVisibility(View.GONE);
				testView.setVisibility(View.GONE);
				android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.add(R.id.FragmentContainer,  newFragement);
			    transaction.commit();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		 MenuInflater inflater =  getMenuInflater();	
		    inflater.inflate(R.menu.main_menu, menu);
		    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_settings:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}
	

}
