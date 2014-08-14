package com.android.predictionsystem;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.predictionsystems.data.universalContentProvider;
import com.android.predictionsystems.data.userInfo;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends Activity {

	private Button signup = null;
	private EditText first_name = null;
	private EditText last_name = null;
	private EditText email = null;
	private EditText phone = null;
	private EditText password = null;
    public static final Pattern MatchEmailPattern =Pattern.compile( "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
	     + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
	     + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
	     + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
    public static final Pattern pattern = Pattern.compile("[[a-zA-Z]+\\s*[a-zA-Z]*]+");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		signup = (Button)findViewById(R.id.register);
		first_name = (EditText) findViewById(R.id.first_name_edit);
		last_name =(EditText) findViewById(R.id.last_name_edit);
		email = (EditText) findViewById(R.id.email_edit_text);
		phone = (EditText)findViewById(R.id.phone_edit);
		password  =  ( EditText)findViewById(R.id.password_edit_text);
		signup.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@SuppressLint("UseValueOf")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean succesful = true;
				ArrayList<Integer> vec = new ArrayList<Integer>();
				ContentValues values = new ContentValues();
				
				if(isValidName(first_name.getText().toString())){
					values.put(userInfo.FIRST_NAME, first_name.getText().toString());
				}else{
					vec.add(new Integer(1));
					succesful = false;
				}
				if(isValidName(last_name.getText().toString())){
					values.put(userInfo.LAST_NAME, last_name.getText().toString());
				}else{
					vec.add(new Integer(2));
					succesful = false;
				}
				if(isValidPhone(phone.getText().toString()))
					values.put(userInfo.PHONE, phone.getText().toString());
				else{
					Toast.makeText(getBaseContext(), "Success is checking phone ", Toast.LENGTH_LONG).show();
					try{
						vec.add(new Integer(3));
						succesful = false;
					}catch(NullPointerException mes){
						System.err.println(mes.getMessage());
					}
				}
				
				if(isValidEmailAddress(email.getText().toString()))
					values.put(userInfo.EMAIL, email.getText().toString());
				else{
					try{
						Toast.makeText(getBaseContext(), "Success is checking Email", Toast.LENGTH_LONG).show();
						vec.add(new Integer(5));
						succesful = false;
					}catch(NullPointerException mes){
						System.err.println(mes.getMessage());
					}
				}
				if(isValidPassword(password.getText().toString())){
					values.put(userInfo.PASSWORD, password.getText().toString());
				}else{
					vec.add(new Integer(4));
					succesful = false;
				}
				AlertDialog dialog = new AlertDialog.Builder(SignUp.this).create();
				if(!succesful){
					//AlertDialog dialog = new AlertDialog.Builder(SignUp.this).create();
					Toast.makeText(getBaseContext(), "Success is checking ", Toast.LENGTH_LONG).show();
				  String mess = "";	
				  dialog.setTitle("Warning");
				  for( int i = 0; i < vec.size(); i++){
					  Integer val = (Integer)vec.get(i);
					  if(val.intValue() == 1)
						  mess += "Invalid first name\n";
					  else if(val.intValue() == 2)
						  mess += "Invalid last name\n";
					  else if(val.intValue() == 3)
						  mess += "Invalid Phone Number\n";
					  else if(val.intValue() == 4)
					  	  mess += "Invalid password\n";
					  else
						  mess += "Invalid Email Address";
				  }
				  dialog.setMessage(mess);
				  dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				  });
				  dialog.show();
				  return ;
				}else{
					
					//AlertDialog dialogBox = new AlertDialog.Builder(SignUp.this).create();
					try{
						Uri uri = getContentResolver().insert(universalContentProvider.CONTENT_URI_USER, values);
						dialog.setTitle("Success");
						dialog.setMessage("Account has been Succesfully Created Press Ok To Continue");
						dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(SignUp.this,LoginActivity.class);
								startActivity(intent);
							}
						});
						
						Toast.makeText(getBaseContext(),uri.getPathSegments().get(0).toString(), Toast.LENGTH_LONG).show();
						dialog.show();
					}catch(ArrayIndexOutOfBoundsException excep){
						System.err.print(excep.getMessage());
					}
					
					
			   }
			}
		});
		
	}
	public boolean isValidPassword(String password){
		Pattern pat = Pattern.compile("[[0-9]*[a-zA-Z]*]+");
		Matcher match = pat.matcher(password);
		if(match.find())
			return true;
		return false;
	}
	public boolean isValidPhone(String number){
		Pattern pat = Pattern.compile("[0-9]+");
		Matcher match = pat.matcher(number);
		if(match.find())
			return true;
		return false;
	}
	public boolean isValidName(String name){
		Matcher match = pattern.matcher(name);
		if(match.find())
			return true;
		return false;
	}
	public static boolean isValidEmailAddress(String email) {
		    Matcher match = MatchEmailPattern.matcher(email);
		   if(match.find()) return true;
		   else return false;
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sign_up, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	

}
