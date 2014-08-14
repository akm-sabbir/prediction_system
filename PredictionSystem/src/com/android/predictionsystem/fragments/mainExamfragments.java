package com.android.predictionsystem.fragments;

import com.android.predictionsystem.R;
import com.android.predictionsystem.SignUp;
import com.android.predictionsystem.Spinner_Tech;
import com.android.predictionsystem.broadcastrecievers.serviceBroadcstreciever;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(11)
public class mainExamfragments extends Fragment{

	private View mContainerview = null;
	//private View mView = null;
	private RadioGroup rdg = null;
	private Button submit = null;
	private TextView timerView = null;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	     super.onCreateView(inflater, container, savedInstanceState);	     
         timerCount();
	     mContainerview = inflater.inflate(R.layout.fragment_item,container,false);
	     //mView = inflater.inflate(R.layout.exam_layout_fragment, container,false);
	     //Button buts = (Button)mView.findViewById(R.id.button1);
	     //buts.setVisibility(View.GONE);
	     rdg = (RadioGroup) mContainerview.findViewById(R.id.radioGroup1);
	     submit  = (Button) mContainerview.findViewById(R.id.submission);
	     submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
			    intent.setClass(getActivity(), Spinner_Tech.class);
				startActivity(intent);
			}
		});
	     rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				if(checkedId == R.id.radio0)
					Toast.makeText(getActivity().getApplicationContext(),"Choice one", Toast.LENGTH_LONG).show();
				else if(checkedId == R.id.radio1)
					Toast.makeText(getActivity().getApplicationContext(), "Choice two", Toast.LENGTH_LONG).show();
				else if(checkedId == R.id.radio2)
					Toast.makeText(getActivity().getApplicationContext(), "Choice three", Toast.LENGTH_LONG).show();
				else if(checkedId == R.id.radio3)
					Toast.makeText(getActivity().getApplicationContext(), "Choice four", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getActivity().getApplicationContext(), "Choice five", Toast.LENGTH_LONG).show();
					
			}
		});
	     ScrollView scV = new ScrollView(getActivity());
	     scV.addView(mContainerview);
	     return scV;
	}
    public void timerCount(){
    	 timerView = (TextView)mContainerview.findViewById(R.id.Timer);
			super.onStart();
			new CountDownTimer(30000, 1000) {

			     public void onTick(long millisUntilFinished) {
			    	 long sec = millisUntilFinished / 1000;
			    	 long min = sec/60;
			    	 long remSec = sec % 60;
			         timerView.setText("remaining time: " + min +":"+ remSec);
			     }

			     public void onFinish() {
			         timerView.setText("done!");
			     }
			  }.start();
    	return;
    }
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
	    
	}
	

}
