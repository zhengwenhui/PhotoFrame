/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zwh.photoframe;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Simple widget to show analog clock.
 */
public class AnalogAppWidgetProvider extends AppWidgetProvider {
	static final String TAG = "AnalogAppWidgetProvider";

	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		//TODO Auto-generated method stub
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.analog_appwidget);
		Intent intentClick = new Intent(context, AnalogActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intentClick, 0);
		mRemoteViews.setOnClickPendingIntent(R.id.analog_appwidget, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
	}
}

