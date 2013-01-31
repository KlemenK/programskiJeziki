package edu.klemen.rekreAsist.android;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class PrikazIzbranePoti extends MapActivity{
	private static final int GLAVNO_OKNO_ID = 0;
	
	
	MapController mapController1;

	MapView myMapView;
	
	Button nazajIzbira;
	ZoomControls zoom;
	
	ArrayList<GeoPoint> izbranaPotLokacije;
	ApplicationControl podatki;
	int zoomkoeficient=15;//zoom
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prikaz_izbrane_poti);
		
        this.setRequestedOrientation(1);
		
		nazajIzbira=(Button) findViewById(R.id.btnNazajIzbranaPot);
		zoom=(ZoomControls)	findViewById(R.id.zoomControls1);
		
		
		nazajIzbira.setOnClickListener(new OnClickListener() {// krajši naèin onclick listenerja - za kratke kode
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				finish();
			}
		});


		
//		locations1=new ArrayList<Location>();
		izbranaPotLokacije= new ArrayList<GeoPoint>();
		podatki=(ApplicationControl)getApplication();

		try{
		//-----------------------------------------maps--
			myMapView = (MapView)findViewById(R.id.mapviewizbranapot);
			mapController1 = myMapView.getController();
			myMapView.displayZoomControls(true);
			mapController1.setZoom(zoomkoeficient);

//			Criteria criteria1 = new Criteria();
//			criteria1.setAccuracy(Criteria.ACCURACY_FINE);
//			criteria1.setAltitudeRequired(false);
//			criteria1.setBearingRequired(false);
//			criteria1.setCostAllowed(true);
//			criteria1.setPowerRequirement(Criteria.POWER_LOW);
			
//			my_updateWithNewLocation(null/*location1*/);//pride prva lokacija v polji
			//my_updateWithNewLocation();
		}catch (Exception e) {
			// TODO: handle exception
		}
		zoom.setOnZoomInClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zoomkoeficient++;
				mapController1.setZoom(zoomkoeficient);
			}
		});
		zoom.setOnZoomOutClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zoomkoeficient--;
				mapController1.setZoom(zoomkoeficient);
			}
		});
		
		
		//branje iz baze in izris
		izbranaPotLokacije=podatki.getIzbranoPotIzDB((int)podatki.izbranaPrikazPot);//pot iz baze za prikaz
		
		for(int i=0;i<izbranaPotLokacije.size();i++){
			Log.d("lat lng iz baze direkt",izbranaPotLokacije.get(i).getLatitudeE6()+"  "+izbranaPotLokacije.get(i).getLongitudeE6());
		}
		DrawPath(izbranaPotLokacije, 190, myMapView);
		
		
		
	}

//	private void my_updateWithNewLocation(GeoPoint location) {
//
//		if (location != null) {
//			
//
//				int geoLat = location.getLatitudeE6();
//				int geoLng = location.getLongitudeE6();
//				GeoPoint point = new GeoPoint(geoLat,geoLng);
//				mapController1.animateTo(point);
//			
////			locations1.add(location);
////			if(lokacijaFlag==false){
////				Double lat= location.getLatitude()*1E6;
////				Double lng= location.getLongitude()*1E6;
////				prvaLokacija= new GeoPoint(lat.intValue(),lng.intValue());
////				lokacijaFlag=true;
////			}
////	    	GeoPoint srcGeoPoint = new GeoPoint((int) (location.getLatitude() * 1E6),(int) (location.getLongitude() * 1E6)); 
////	    	myMapView.getController().animateTo(srcGeoPoint);
//		}
//
//	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode) {
        case GLAVNO_OKNO_ID:
        	if(resultCode==RESULT_CANCELED) this.finish();
        	break;        	
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		// TODO Auto-generated method stub
//		if((event.getActionMasked()==0)&&(ROUTE_FLAG==0)&&(ROUTE_FLAG1==0)){
//			GeoPoint zadnaLokacija = myMapView.getProjection().fromPixels((int) event.getX(),(int) event.getY());
//	       // Toast.makeText(getBaseContext(), p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 , Toast.LENGTH_SHORT).show();		
//	        if(prvaLokacija!=null){
//	        	DrawPath(prvaLokacija, zadnaLokacija, Color.RED, myMapView);
//	        	ROUTE_FLAG=1;//pridobim samo eno pot
//	        	ROUTE_FLAG1=1;
////	        	premik.setText("Reset");
//	        }
//		}	
//		return false;
//	}
	
	
	
	private void DrawPath(ArrayList<GeoPoint> pot, int color, MapView mMapView01) 
	{ 
		GeoPoint gp1,gp2;
			if(pot.size()>0) 
			{ 
				gp1 = new GeoPoint(pot.get(0).getLatitudeE6(),pot.get(0).getLongitudeE6());
			//	mapController1.animateTo(gp1);
				for(int i=1;i<pot.size();i++) // the last one would be crash 
				{ 
					// watch out! For GeoPoint, first:latitude, second:longitude 
					
					gp2 = new GeoPoint(pot.get(i).getLatitudeE6(), pot.get(i).getLongitudeE6());
					
					Log.d("geolat in geolng",pot.get(i).getLatitudeE6()+"   "+pot.get(i).getLongitudeE6());
					mMapView01.getOverlays().add(new MyOverlay(gp1,gp2,2,color)); 
					gp1=gp2;
					//Log.d("xxx","pair:" + pairs[i]); 
				}
				
				gp2 = new GeoPoint(pot.get(pot.size()-1).getLatitudeE6(),
									pot.get(pot.size()-1).getLongitudeE6());
				mMapView01.getOverlays().add(new MyOverlay(gp1,gp2,2,color));
				mMapView01.getOverlays().add(new MyOverlay(gp2,gp2, 3)); // default barva 
				mapController1.animateTo(gp2);
			}  
		Log.d("asd", "test1 "+myMapView.getOverlays().size());
	}

}
