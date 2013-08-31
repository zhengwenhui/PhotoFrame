package com.zwh.photoframe;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*IntentFilter filter = new IntentFilter("android.appwidget.action.APPWIDGET_UPDATE"); // 和广播中Intent的action对应  
		filter.addAction("android.appwidget.action.APPWIDGET_CLASS_ONCLICK");
		ClassAppWidgetProvider br = new ClassAppWidgetProvider();
		//br.setResultExtras(extras);
		registerReceiver(br, filter);
		
		ComponentName cn = new ComponentName(this, ClassAppWidgetProvider.class);  
        try {  
            ActivityInfo info = getPackageManager().getReceiverInfo(cn,  
                    PackageManager.GET_META_DATA);  
            String msg = info.metaData.getString("receiver_name");
        } catch (NameNotFoundException e) {
            e.printStackTrace();  
        }*/
	}
}
