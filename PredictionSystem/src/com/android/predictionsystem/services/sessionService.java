package com.android.predictionsystem.services;



import com.android.predictionsystem.LoginActivity;
import com.android.predictionsystem.broadcastrecievers.serviceBroadcstreciever;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;

public class sessionService extends Service {

	public static final String sessionService = "sessionServices";
	public static final String sessionName = "Services";
	private serviceBroadcstreciever serviceReciever = null;
	private final IBinder binder = new MyBinder();
	private MyAsyncTask2 asyncTask ;	
	private int done = 1;
	private LoginActivity logs = null;
    public class MyBinder extends Binder{
		 public sessionService getService(){
			 return sessionService.this;
		 }
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		asyncTask = new MyAsyncTask2();
		
		}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		 super.onStartCommand(intent, flags, startId);
		// mainProcessing();
		 //done = 1;
		// Toast.makeText(getApplication(), "We are here to start our Ops", Toast.LENGTH_LONG).show();
		 asyncTask.execute(0);
		 return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		 done = 1;
		 //logs = (LoginActivity)intent.getSerializableExtra("detailActivity");
	//	 serviceReciever = new  serviceBroadcstreciever(getApplicationContext());
	    // getApplicationContext().registerReceiver(serviceReciever, new IntentFilter( serviceBroadcstreciever.serviceRecieved));
		 Toast.makeText(getApplication(), "We are here to start our Ops", Toast.LENGTH_LONG).show();
		 asyncTask.execute(0);
		return binder;
	}
	public void mainProcessing(){
		
		Thread  thread = new Thread(null,doBackgroundThread,"BACKGROUND");
		thread.start();
	}
	private Runnable doBackgroundThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*new CountDownTimer(10000, 1000) {

			     public void onTick(long millisUntilFinished) {
			    	 long sec = millisUntilFinished / 1000;
			    	 long min = sec/60;
			    	 long remSec = sec % 60;
			         //timerView.setText("remaining time: " + min +":"+ remSec);
			     }

			     public void onFinish() {
			         //timerView.setText("done!");
			    	 Intent intentB = new Intent(serviceBroadcstreciever.serviceRecieved);
					 sendBroadcast(intentB);
			     }
			  }.start();*/
		}
	};
	
	class MyAsyncTask2 extends AsyncTask<Integer, Integer, Long> {

		public MyAsyncTask2(Activity context) {
				
		}

		public MyAsyncTask2() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Long doInBackground(Integer... params) {
		
			
			long start = System.currentTimeMillis();
			
            
			 long endTime = System.currentTimeMillis() + 10*1000;
	          while (System.currentTimeMillis() < endTime) {
	              synchronized (this) {
	                  try {
	                      wait(endTime - System.currentTimeMillis());
	                  } catch (Exception e) {
	                  }
	              }
	          }
			long end = System.currentTimeMillis();
			long elapse = end - start;
			return elapse;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
		}

		@Override
		protected void onPostExecute(Long time) {
			 Intent intentB = new Intent(serviceBroadcstreciever.serviceRecieved);
			 Toast.makeText(getApplication(), "Starting BroadCasting", Toast.LENGTH_LONG).show();
	    	 sendBroadcast(intentB);
		}

		@Override
		protected void onPreExecute() {

			

		}

	}
	public void dialogBoxgenerator(){
		//Toast.makeText(getApplicationContext(), "High i have recieved a broadcast", Toast.LENGTH_LONG).show();
	      AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).create();
				dialog.setMessage("Your Session is Over");
				dialog.setTitle("TimeOut");
				dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(intent);
						//intent.setClass( context.getApplicationContext()., LoginActivity.class);
						
					}
				});
				dialog.show();
	}

}
