package edu.klemen.rekreAsist.android;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider{
	public String ACTION_BUTTON="button";//stisnemo na gumb
	public String ACTION_WIDGET="widget";//stisnemo na widget
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// Build the intent to call the service
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
			Intent intent = new Intent(context.getApplicationContext(),UpdateWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			
			Intent intentW= new Intent(context,OpenWidgetService.class);
			intentW.setAction(ACTION_WIDGET);
			intentW.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			
			
			// To react to a click we have to use a pending intent as the
			// onClickListener is
			// excecuted by the homescreen application
			PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
			PendingIntent wpendingIntent= PendingIntent.getService(context.getApplicationContext(), 0, intentW, PendingIntent.FLAG_UPDATE_CURRENT);
			
			remoteViews.setOnClickPendingIntent(R.id.btnWidget, pendingIntent);
			remoteViews.setOnClickPendingIntent(R.id.textWidgetOpis, wpendingIntent);
			remoteViews.setOnClickPendingIntent(R.id.textWidgetKraj, wpendingIntent);
			remoteViews.setOnClickPendingIntent(R.id.textWidgetNaziv, wpendingIntent);
			remoteViews.setOnClickPendingIntent(R.id.textWidgetDatum, wpendingIntent);
	
			
			// Finally update all widgets with the information about the click
			// listener
			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	
			// Update the widgets via the service
			//context.startService(intent);
		}

} 


