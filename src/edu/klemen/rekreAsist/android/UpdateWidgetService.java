package edu.klemen.rekreAsist.android;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {
	
	ApplicationExample app;
	
	@Override
	public void onStart(Intent intent, int startId) {
		if(app==null) app = (ApplicationExample) getApplicationContext();
		
		RSSReader rss = new RSSReader();
		
		if(app.getPodatkiKoledarWidget()==null || app.getPodatkiKoledarWidget().size()==0) app.setNews(rss.readNews());
		
		
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] appWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		if (appWidgetIds.length > 0) {
			for (int widgetId : appWidgetIds) {
				RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
				
				remoteViews.setTextViewText(R.id.textWidgetDatum, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).datum);
				remoteViews.setTextViewText(R.id.textWidgetKraj, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).kraj);
				remoteViews.setTextViewText(R.id.textWidgetNaziv, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).naziv);
				remoteViews.setTextViewText(R.id.textWidgetOpis, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).opis);
				
				app.setStevec(app.getStevec()+1);
				appWidgetManager.updateAppWidget(widgetId, remoteViews);
			}
			stopSelf();
		}
		super.onStart(intent, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
