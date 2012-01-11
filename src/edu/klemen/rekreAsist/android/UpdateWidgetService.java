package edu.klemen.rekreAsist.android;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class UpdateWidgetService extends Service{
	
	ApplicationExample app;
	
	@Override
	public void onStart(Intent intent, int startId) {
		if(app==null) app = (ApplicationExample) getApplicationContext();
		
		RSSReader rss = new RSSReader();
		
		if(app.getPodatkiKoledarWidget()==null || app.getPodatkiKoledarWidget().size()==0) app.setPodatkiKoledarWidget(rss.readNews());
		
		try{
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

		int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		if (appWidgetIds.length > 0) {
		//	Log.d("test54", "sad");
			for (int widgetId : appWidgetIds) {
				RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
				
				remoteViews.setTextViewText(R.id.textWidgetDatum, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).datum);
				remoteViews.setTextViewText(R.id.textWidgetKraj, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).kraj);
				remoteViews.setTextViewText(R.id.textWidgetNaziv, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).naziv);
				if(app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).opis.startsWith("opis prireditve:")==false) remoteViews.setTextViewText(R.id.textWidgetOpis, "Opis ni na voljo");
				else remoteViews.setTextViewText(R.id.textWidgetOpis, app.getPodatkiKoledarWidget().get(app.getStevec()%app.getPodatkiKoledarWidget().size()).opis.replace("opis prireditve: ", "Opis prireditve:\n"));
				
				app.setStevec(app.getStevec()+1);
				appWidgetManager.updateAppWidget(widgetId, remoteViews);
			}
			stopSelf();
		}
		}catch(Exception ex){
			//Toast.makeText(this, "Prosim preverite ali imate povezavo z internetom", Toast.LENGTH_LONG).show();
		}
		super.onStart(intent, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
