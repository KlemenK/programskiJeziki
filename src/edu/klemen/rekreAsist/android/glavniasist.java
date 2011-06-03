package edu.klemen.rekreAsist.android;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class glavniasist extends MapActivity implements android.view.View.OnClickListener {
	Button startstop,pavza,novKrog;
	TextView cas,pot,krog,hitrost;
	Chronometer stoparca;
	
	
	MapController mapController;
	MyPositionOverlay positionOverlay;
	ArrayList<Location> locations;
	List<Float> speed;
	public int trenKrog=0;
	public boolean pause=false;
	public boolean flag=true;
	public long start = 0;//SystemClock.elapsedRealtime();
	public long end = 0;//SystemClock.currentTimeMillis();
	public long time=0;
	boolean stoparcaFlag=false;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glavni);
		
		
		locations=new ArrayList<Location>();
		speed=new ArrayList<Float>();
		
		
		
		startstop=(Button) findViewById(R.id.btnStartStop);
		pavza=(Button) findViewById(R.id.btnPavza);
		cas=(TextView) findViewById(R.id.cas);
		pot=(TextView) findViewById(R.id.pot);
		krog=(TextView) findViewById(R.id.krog);
		hitrost=(TextView) findViewById(R.id.tw_Hitrost);
		novKrog=(Button) findViewById(R.id.btnNovKrog);
		
		startstop.setOnClickListener(this);
		pavza.setOnClickListener(this);
		novKrog.setOnClickListener(this);
		
		stoparca=(Chronometer) findViewById(R.id.stoparca);
		
		krog.setText("Krog: "+trenKrog);
		
		
		stoparca.setFormat(null);
		//-----------------------------------------maps--
		MapView myMapView1 = (MapView)findViewById(R.id.myMapView);
		mapController = myMapView1.getController();

		myMapView1.setSatellite(true);
		myMapView1.setStreetView(true);
		myMapView1.displayZoomControls(false);

		mapController.setZoom(17);


		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		// Add the MyPositionOverlay
		positionOverlay = new MyPositionOverlay();
		List<Overlay> overlays = myMapView1.getOverlays();
		overlays.add(positionOverlay);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);
		my_updateWithNewLocation(location);
		
		locationManager.requestLocationUpdates(provider, 35, 5,   
				locationListener);
		
		//-----------------------------------------maps/-
		
		
	}
	
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			my_updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider){
			my_updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status, 
				Bundle extras){ }
	};

	private void my_updateWithNewLocation(Location location) {
		//String latLongString;
		Double dolzina=0.0;
		//TextView myLocationText;
		//myLocationText = (TextView)findViewById(R.id.myLocationText);

		if (location != null) {
			positionOverlay.setLocation(location);

			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(), 
					geoLng.intValue());

			mapController.animateTo(point);

			//double lat = location.getLatitude();
		//	double lng = location.getLongitude();
			//latLongString = "Lat:" + lat + "\nLong:" + lng;
			locations.add(location);
			speed.add((float)(location.getSpeed()*3.6));
			hitrost.setText("Hitrost: "+(location.getSpeed()*3.6)+"km/h");
			if((locations.size()>=2)){
	    		
	    		for(int i=0;i<locations.size()-1;i++){
	    			Location loc1=locations.get(i);
	    			Location loc2=locations.get(i+1);
	    			dolzina+=loc1.distanceTo(loc2);   
	    			DecimalFormat test=new DecimalFormat("#.##");
	    			dolzina=Double.valueOf(test.format(dolzina));
	    			//loc1.gett
	    		}
	    		pot.setText("Pot: "+dolzina+"m");
	    		
			}
			//myLocationText.setText("Trenutni položaj je:" + latLongString);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnStartStop:
		//	Toast test;
			if(pause==false){
				if(flag==false){
					//start=SystemClock.elapsedRealtime();
					stoparca.setBase(SystemClock.elapsedRealtime()-end);
					stoparca.start();
					stoparcaFlag=true;
					pause=true;
				}else{
					stoparca.setBase(SystemClock.elapsedRealtime());//prviè
					stoparca.start();
					stoparcaFlag=true;
					pause=true;
					flag=false;
				}
			}
			else{
				stoparca.stop();
				end=SystemClock.elapsedRealtime() - stoparca.getBase();
				pause=false;
			}
			break;

		case R.id.btnPavza:
			stoparca.stop();
			pause=false;
			break;
		case R.id.btnNovKrog:
			trenKrog++;
			krog.setText("Krog: "+trenKrog);
			stoparca.setBase(SystemClock.elapsedRealtime());
			break;

		default:
			break;
		}
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
