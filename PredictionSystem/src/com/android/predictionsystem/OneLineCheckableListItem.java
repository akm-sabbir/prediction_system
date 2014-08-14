package com.android.predictionsystem;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OneLineCheckableListItem extends RelativeLayout implements Checkable {

    public OneLineCheckableListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean checked;


    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked; 

        CheckBox iv = (CheckBox) findViewById(R.id.check);
        TextView text = (TextView) findViewById(R.id.label);
      //  iv.setChecked(true);
        Log.i("Toggle in Set", "Check the check");
        //iv.setImageResource(checked ? R.drawable.button_up : R.drawable.button_down);
    }

    @Override
    public void toggle() {
    	Log.i("Toggle in toggle", "Check the check");
       // this.checked = !this.checked;
    }
}