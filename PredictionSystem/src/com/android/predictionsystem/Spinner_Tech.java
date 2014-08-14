package com.android.predictionsystem;

import java.text.ChoiceFormat;

import java.util.ArrayList;
import java.util.List;

import com.android.predictionsystem.InteractiveArrayAdapter.ViewHolder;
import com.android.predictionsystem.fragments.Fragment_exam;
import com.android.predictionsystems.data.technicalInfo;
import com.android.predictionsystems.data.universalContentProvider;
import com.android.predictionsystems.data.userInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView;

//import com.samsung.indoornavigation.R;

@SuppressLint("NewApi")
public class Spinner_Tech extends Activity implements OnItemSelectedListener{

	
	private Spinner spinner_item = null;
	private Button next = null;
	private CheckBox checkbox = null;
	private  CheckBox checkbox01 = null;
	private CheckBox checkboxcomponent = null;
	private EditText data = null;
	private Button subOrnext = null;
	private String[] presidents={"Select","Java","C_plusplus","C","Python","C#","J#","PhP","Android","IOS"};
	private List<String> list = new ArrayList<String>();
	private String selectedItem = null;
	private static ListView listview = null;
	private List<Model> listList = new ArrayList<Model>();
	private int set = 0;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner_layout);
		spinner_item = (Spinner)findViewById(R.id.spinnerItem);
		checkbox = (CheckBox) findViewById(R.id.CheckBox01);
		
		data = (EditText) findViewById(R.id.editText);
		next = (Button) findViewById(R.id.next);
		listview = (ListView) findViewById(R.id.list);
		checkbox01 = (CheckBox) findViewById(R.id.checkBox2);
		try{
		checkboxcomponent = (CheckBox) findViewById(R.id.check);
		checkboxcomponent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
					Toast.makeText(Spinner_Tech.this, "I have been Checked",Toast.LENGTH_LONG).show();
			}
		});
		}catch(NullPointerException nulex){
			System.err.print(nulex.getMessage());
		}
		listview.setClickable(true);
		checkbox.setChecked(false);
		checkbox01.setChecked(false);
		if(!checkbox01.isChecked()){
			listview.setEnabled(false);
		}
		if(!checkbox.isChecked()){
			data.setEnabled(false);
			spinner_item.setEnabled(false);
		}
		checkbox01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(checkbox01.isChecked()){
					listview.setEnabled(true);
					listview.setClickable(true);
					listview.setChoiceMode(2);
				}else{
					listview.setEnabled(false);
					listview.setClickable(false);
					
				}
			}
		});
		checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(checkbox.isChecked()){
					data.setEnabled(true);
					spinner_item.setEnabled(true);
					next.setText("Add");
					
				}else{
					data.setEnabled(false);
					spinner_item.setEnabled(false);
					next.setText("Next");
				}
				
			}
		});
		spinner_item.setOnItemSelectedListener(this);
		listview.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
			public void onDestroyActionMode(ActionMode arg0) {
				
			}
			
			public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// TODO Auto-generated method stub
				
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, final View view1, int position,long id) {
				// TODO Auto-generated method stub
				view1.animate().setDuration(2000).alpha(0);
				View model = (View)adapter.getItemAtPosition(position);
				if(model.isSelected())
					view1.setBackgroundColor(Color.BLUE);
				else
					view1.setBackgroundColor(Color.WHITE);
				ViewHolder view = (ViewHolder)model.getTag();
			//	if(view.checkbox.isChecked())
						Toast.makeText(Spinner_Tech.this, "is it selected ?"/*view.text.getText().toString()*/, Toast.LENGTH_LONG).show();
			}
		});
//		listview.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> adapter, View arg1,
//					int position, long arg3) {
//				// TODO Auto-generated method stub
//				View model = (View)adapter.getItemAtPosition(position);
//				ViewHolder view = (ViewHolder)model.getTag();
//			//	if(view.checkbox.isChecked())
//						Toast.makeText(Spinner_Tech.this,  "on item selection "+view.text.getText().toString(), Toast.LENGTH_LONG).show();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		for(String str : presidents){
			list.add(str);
		}
		//next  = (Button)findViewById(R.id.next);
		try{
			next.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder dialog = new AlertDialog.Builder(Spinner_Tech.this);
					dialog.setMessage("Adding " + data.getText().toString() + " years of experience in " + selectedItem);
					dialog.setTitle("TechInfo");
					dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						
							SharedPreferences prefrw = getSharedPreferences("ID", Context.MODE_PRIVATE);
							
							int val;
							ContentValues values = new ContentValues();
							String str = prefrw.getString("USER_ID", "Empty");
							if(str.equals("Empty")){
								Toast.makeText(getBaseContext(), "Invalid User Id", Toast.LENGTH_LONG).show();
								return;
							}
							else
								val = Integer.parseInt(str);
							Cursor c = getContentResolver().query(universalContentProvider.CONTENT_URI_TECHNICALINFO, null, technicalInfo.TECHNOLOGY + "=" + "\'"+ selectedItem +"\'" ,null, null);
							if(c.moveToFirst())
								return;
							values.put(technicalInfo.TECHNOLOGY, selectedItem);
							values.put(technicalInfo.EXPERIENCE, data.getText().toString());
							values.put(technicalInfo.USER_ID, val);
							Uri uri = getContentResolver().insert(universalContentProvider.CONTENT_URI_TECHNICALINFO, values);
							try{
								Toast.makeText(getBaseContext(), uri.getPathSegments().get(0).toString(), Toast.LENGTH_LONG).show();
							}catch(ArrayIndexOutOfBoundsException excep){
								System.err.print(excep.getMessage());
							}
						}
					});
															
					if(!checkbox.isChecked()){
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(), Fragment_exam.class);
						startActivity(intent);
					}else{
						dialog.show();
						
					}
				}
			});
			
		}catch(NullPointerException nulexcep){
			System.err.println(nulexcep.getMessage());
		}
		try{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		ArrayAdapter<Model> listAdapter = new InteractiveArrayAdapter(this, getModel(),listview);
		listview.setAdapter(listAdapter);
		//ActionListAdapter adapter = new ActionListAdapter(getApplicationContext(),R.layout.spinner_items,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner_item.setAdapter(adapter);
		}catch(NullPointerException pointerEx){
			
			System.err.println(pointerEx.getMessage()+"\n");
		}
	
	}

	class ActionListAdapter extends ArrayAdapter<String>{

		private List<String> items;
		String[] presidents={"Select","Java","C_plusplus","C","Python","C#","J#","PhP","Android","IOS","Jscript"};
		//int []image_id={R.drawable.content_picture,R.drawable.device_access_camera,R.drawable.ic_action_video};
		public ActionListAdapter(Context context, 
				int textViewResourceId, List<String> objects) {
			super(context,  textViewResourceId, objects);
			items=objects;
			for(String str : presidents){
				items.add(str);
			}
			// TODO Auto-generated constructor stub
		}
		
    
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.spinner_items, null);
			}
			String device = items.get(position);
			if (device != null) {
		        TextView title = (TextView)v.findViewById(R.id.tech_title); // title
		       // ImageView imageView = (ImageView)v.findViewById(R.id.list_image); // title
		        //imageView.setImageResource(image_id[position]);
		        // Setting all values in listview
		        title.setText(device);
		    //    title.settextco
		        
			}
			return v;
		}

	}
    /**
     * Initiate a connection with the peer.
     */

	@Override
	public void onItemSelected(AdapterView<?> main, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		 selectedItem = main.getItemAtPosition(position).toString();
		 Toast.makeText(main.getContext(),"The selected Items are " + selectedItem, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
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
		case R.id.action_Next:
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), Fragment_exam.class);
			startActivity(intent1);
			break;
			default:
			
		}
		return true;
	}
	  private List<Model> getModel() {
		    List<Model> list = new ArrayList<Model>();
		    list.add(get("Ransac"));
		    list.add(get("Linear Regression"));
		    list.add(get("Map Reduce"));
		    list.add(get("Polynomial Regression"));
		    list.add(get("Temporal Pattern Mining"));
		    list.add(get("Spanning Trees"));
		    list.add(get("Tries"));
		    list.add(get("Max Flow"));
		    list.add(get("Min Cost Flow"));
		    list.add(get("Kruskal"));
		    list.add(get("wireless Sensor Network"));
		    list.add(get("Adhoc Network"));
		    list.add(get("SVM"));
		    list.add(get("Neural Net"));
		    // Initially select one of the items
		    list.get(1).setSelected(true);
		    return list;
		  }

		  private Model get(String s) {
		    return new Model(s);
		  }

	   public boolean getCheckbox01(){
		   return checkbox01.isChecked();
	   }
}
