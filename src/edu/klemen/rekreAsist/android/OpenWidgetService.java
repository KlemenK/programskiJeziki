package edu.klemen.rekreAsist.android;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class OpenWidgetService extends Service{
	ApplicationControl app;
	
	@Override
	public void onStart(Intent intent, int startId) {
		if(app==null) app = (ApplicationControl) getApplicationContext();
	//	Log.d("test5", "shgf");
		RSSReader rss = new RSSReader();
		if(app.getPodatkiKoledarWidget()==null || app.getPodatkiKoledarWidget().size()==0)
//		try{
			app.setPodatkiKoledarWidget(rss.readNews());
		
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
//			Log.d("test","t1");
//			Log.wtf("test1", "t2");
			int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
			if (appWidgetIds.length > 0) {
//				Log.d("test","t2");
				for (int widgetId : appWidgetIds) {
//					Log.d("test","t3");
					if(app.getStevec()%app.getPodatkiKoledarWidget().size()-1!=-1){
						Intent nova= new Intent(this.getApplicationContext(),PrikazWidgetPodatkov.class);
//						Log.d("test","t4");
						nova.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(nova);
//						Log.d("test","t5");
					}
	
				}
				stopSelf();
			}
//			Log.d("test","t9");
//		}catch(Exception ex){
////			Log.d("test","t7");
//			Intent nova= new Intent(this.getApplicationContext(),PrikazWidgetPodatkov.class);
//			nova.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(nova);
//		}
//		Log.d("test","t8");
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
