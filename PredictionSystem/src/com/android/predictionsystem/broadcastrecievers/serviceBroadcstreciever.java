package com.android.predictionsystem.broadcastrecievers;

import com.android.predictionsystem.LoginActivity;
import com.android.predictionsystem.Spinner_Tech;
import com.android.predictionsystem.services.sessionService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.Toast;

public class serviceBroadcstreciever extends BroadcastReceiver {

	public static final String serviceRecieved = "sessionServices";
	private  Context mContext;
	private LoginActivity logActivity;
	public serviceBroadcstreciever(LoginActivity baseContext){
		   logActivity = baseContext;
		  
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mContext = context;
	
		
		if(intent.getAction().equals(serviceRecieved)){
			 Toast.makeText(logActivity, "BroadCasting Recieved", Toast.LENGTH_LONG).show();
			dialogBoxgenerator();
		}
		
	}
	public void dialogBoxgenerator(){
		Toast.makeText(logActivity, "High i have recieved a broadcast", Toast.LENGTH_LONG).show();
		try{
	      AlertDialog dialog = new AlertDialog.Builder(logActivity).create();
				dialog.setMessage("Your Session is Over");
				dialog.setTitle("TimeOut");
				dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(mContext, LoginActivity.class);
						logActivity.startActivity(intent);
						
						//intent.setClass( context.getApplicationContext()., LoginActivity.class);
						
					}
				});
				dialog.show();
		}catch(RuntimeException rt){
			Log.i("runtime",rt.getMessage());
			System.err.print(rt.getMessage());
		}
	}

}
