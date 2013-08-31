package com.zwh.photoframe;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

public class ClockAppWidgetProvider extends AppWidgetProvider {

	public static String ACTION_APPWIDGET_ONCLICK = "android.appwidget.action.APPWIDGET_CLOCK_ONCLICK";

	RemoteViews mRemoteViews;
	int[] mAppWidgetIds;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.i("Windows", "onReceive:"+action);

		if (ACTION_APPWIDGET_ONCLICK.equals(action)) {
			SharedPreferences shared = context.getSharedPreferences(ACTION_APPWIDGET_ONCLICK,
					Activity.MODE_PRIVATE);
			int length = shared.getInt("length", 0);
			Log.e("onReceive", "length:"+length);
			if( length > 0 ){
				mAppWidgetIds = new int[length]; 
				for (int i = 0; i < length; i++) {
					mAppWidgetIds[i] = shared.getInt(String.valueOf(i), 0);
				}
				AppWidgetManager gm = AppWidgetManager.getInstance(context);
				mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.photo_frame_clock);
				FileUtils fileUtils = new FileUtils();
				String avatarUri = fileUtils.getSDPATH()+ClockActivity.AVATAR_FILE_NAME;
				Bitmap bitmap = BitmapFactory.decodeFile(avatarUri);
				if ( null != bitmap){
					mRemoteViews.setImageViewBitmap(R.id.frameImageButton, bitmap);
				}
				gm.updateAppWidget(mAppWidgetIds, mRemoteViews);
			}
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		//TODO Auto-generated method stub
		SharedPreferences shared = context.getSharedPreferences(ACTION_APPWIDGET_ONCLICK,
				Activity.MODE_PRIVATE);
		int length = shared.getInt("length", 0);
		SharedPreferences.Editor editor = shared.edit();
		editor.putInt("length", appWidgetIds.length+length);
		for (int i = 0; i < appWidgetIds.length; i++) {
			editor.putInt(String.valueOf(i+length), appWidgetIds[i]);
		}
		editor.commit();

		mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.photo_frame_clock);
		FileUtils fileUtils = new FileUtils();
		String avatarUri = fileUtils.getSDPATH()+ClockActivity.AVATAR_FILE_NAME;
		Bitmap bitmap = BitmapFactory.decodeFile(avatarUri);
		if ( null != bitmap){
			mRemoteViews.setImageViewBitmap(R.id.frameImageButton, bitmap);
		}
		Intent intentClick = new Intent(context, ClockActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intentClick, 0);
		mRemoteViews.setOnClickPendingIntent(R.id.frameImageButton, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
	}
}