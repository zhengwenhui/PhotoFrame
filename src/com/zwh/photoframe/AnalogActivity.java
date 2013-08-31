package com.zwh.photoframe;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class AnalogActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/*Intent myIntent = new Intent();
		myIntent.setClassName("com.android.deskclock", "com.android.deskclock.deskclock");
		startActivityForResult(myIntent, 1);*/
		ComponentName comp = new ComponentName("com.android.deskclock","com.android.deskclock.DeskClock"); 
		Intent intent = new Intent(); 
		intent.setComponent(comp); 
		startActivity(intent); 
		
		super.onCreate(savedInstanceState);
	}
}