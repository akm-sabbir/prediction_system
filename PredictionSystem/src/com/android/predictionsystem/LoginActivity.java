package com.android.predictionsystem;

import java.io.Serializable;

import com.android.predictionsystem.broadcastrecievers.serviceBroadcstreciever;
import com.android.predictionsystem.fragments.Fragment_exam;
import com.android.predictionsystem.services.sessionService;
import com.android.predictionsystems.data.universalContentProvider;
import com.android.predictionsystems.data.userInfo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
    private SharedPreferences prefrw;
    private boolean emailChecked = false; 
    private serviceBroadcstreciever serviceReciever = null;
	private sessionService myService;
	private ServiceConnection mConnection = new ServiceConnection() {
		
	
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			myService = ((sessionService.MyBinder)service).getService();
			myService.mainProcessing();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			myService = null;
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 serviceReciever = new  serviceBroadcstreciever(LoginActivity.this);
	     getApplicationContext().registerReceiver(serviceReciever, new IntentFilter( serviceBroadcstreciever.serviceRecieved));
		 Toast.makeText(getApplication(), "We are here to start our Ops", Toast.LENGTH_LONG).show();
       // myService.mainProcessing();
		setContentView(R.layout.activity_login);
      //  serviceReciever = new  serviceBroadcstreciever(this);
       // registerReceiver(serviceReciever, new IntentFilter( serviceBroadcstreciever.serviceRecieved));
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		prefrw = getSharedPreferences("ID", Context.MODE_PRIVATE);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		findViewById(R.id.Signup).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplication(), SignUp.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			Editor  prefrwEdit = prefrw.edit();
			String[] projection = {userInfo._ID,userInfo.FIRST_NAME,userInfo.LAST_NAME,userInfo.EMAIL,userInfo.PASSWORD};
		     Cursor c = getContentResolver().query(universalContentProvider.CONTENT_URI_USER, null, userInfo.EMAIL + "=" + "\'"+mEmailView.getText().toString()+"\'" ,null, null);
	                if (c.moveToFirst()) {
	                	emailChecked = true;
	                	boolean pos = false;
	                    do{                    	
	                    try{                   	
	                    	/*Toast.makeText(LoginActivity.this,
	                                c.getString(c.getColumnIndex(
	                                    userInfo._ID)).toString() + ", " +                     
	                                c.getString(c.getColumnIndex(
	                                    userInfo.FIRST_NAME)).toString() + ", " +                     
	                                c.getString(c.getColumnIndex(
	                                    userInfo.EMAIL)).toString(),Toast.LENGTH_LONG).show();*/
	                    	Log.v("Track the Password", c.getString(c.getColumnIndex(userInfo.PASSWORD)));
	                    	 if(c.getString(c.getColumnIndex(userInfo.PASSWORD)).toString().equals( mPasswordView.getText().toString() )){
	                    		 
	                    		prefrwEdit.putString("USER_ID",c.getString(c.getColumnIndex(userInfo._ID)).toString());
	                    		prefrwEdit.commit();
	                    		pos = true;
	                    		break;
	                    	 }
	                    }catch(Exception ex){
	                    	System.err.println("Exception is " + ex.getMessage());
	                    }	 
	                    	 
	                    } while (c.moveToNext());
	                    return pos;
	                }

			// TODO: register the new account here.
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
            AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
			if (success) {
				// i have to do work here.
			//	finish();
				emailChecked = false;
				dialog.setMessage("Login is successful press ok to continue");
				dialog.setTitle("Login");
				dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.d("hello service", "Service is working");
						Intent bindInent = new Intent(getApplicationContext(),sessionService.class);//getApplicationContext
						//bindInent.putExtra("detailActivity", LoginActivity.this);
						bindService(bindInent, mConnection, Context.BIND_AUTO_CREATE);
						Intent intent = new Intent();
					    intent.setClass(getApplicationContext(), Spinner_Tech.class);
						startActivity(intent);
					}
				});
				dialog.show();
			} else {		
				if(!emailChecked){
					mEmailView.setError(getString(R.string.error_invalid_email));
					mEmailView.requestFocus();
				}else{
					mPasswordView
						.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
				}
				emailChecked = false;
			}
			
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	super.onBackPressed();
	}
	
}
