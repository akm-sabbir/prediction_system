package com.android.predictionsystem;


import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class InteractiveArrayAdapter extends ArrayAdapter<Model> {

  private final List<Model> list;
  private final Activity context;
  private ListView listv = null;
  private ViewHolder holder = null;
  public InteractiveArrayAdapter(Activity context, List<Model> list, ListView listV) {
    super(context, R.layout.listcomponents, list);
    this.context = context;
    this.list = list;
    listv = listV;
    setNotifyOnChange(true);
  }

  static class ViewHolder {
    protected TextView text;
    protected CheckBox checkbox;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
     final View view;
    if (convertView == null) {
      LayoutInflater inflator = context.getLayoutInflater();
      view = inflator.inflate(R.layout.listcomponents, null);
      final ViewHolder viewHolder = new ViewHolder();
      viewHolder.text = (TextView) view.findViewById(R.id.label);
      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
      viewHolder.checkbox
          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
              CheckBox element = (CheckBox) viewHolder.checkbox;
              if(!element.isChecked())
            	  view.setBackgroundColor(Color.WHITE);
            //  element.setSelected(buttonView.isChecked());

            }
          });
      view.setTag(viewHolder);
      viewHolder.checkbox.setTag(list.get(position));
      view.setOnClickListener(new OnClickListener() {	
		@Override
		public void onClick(View v) {
			if(((Spinner_Tech)context).getCheckbox01()){
				if(viewHolder.checkbox.isChecked()){
				
					view.setBackgroundColor(Color.BLUE);
				}
				else
					view.setBackgroundColor(Color.WHITE);
			}
		}
	});
      
    } else {
      view = convertView;
      ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
    }
  
    holder = (ViewHolder) view.getTag();
    holder.text.setText(list.get(position).getName());
    holder.checkbox.setChecked(list.get(position).isSelected());
   
   
    return view;
  }
} 