package edu.klemen.rekreAsist.android;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserAdapter;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class izbiraPoti extends MapActivity implements android.view.View.OnClickListener {
	private static final int GLAVNO_OKNO_ID = 0;
	Button nazaj,zazeni;
	
	MapController mapController1;
	MyPositionOverlay positionOverlay1;
	ArrayList<Location> locations1;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.izbirapoti);
		
		
		
		nazaj=(Button) findViewById(R.id.btnNazaj);
		zazeni=(Button) findViewById(R.id.btn_zazeniPot);
		
		nazaj.setOnClickListener(this);
		zazeni.setOnClickListener(this);
		
		locations1=new ArrayList<Location>();
		//-----------------------------------------maps--
		MapView myMapView = (MapView)findViewById(R.id.mapviewNova);
		mapController1 = myMapView.getController();

		myMapView.setSatellite(true);
		myMapView.setStreetView(true);
		myMapView.displayZoomControls(false);

		mapController1.setZoom(17);


		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		// Add the MyPositionOverlay
		positionOverlay1 = new MyPositionOverlay();
		List<Overlay> overlays = myMapView.getOverlays();
		overlays.add(positionOverlay1);

		Criteria criteria1 = new Criteria();
		criteria1.setAccuracy(Criteria.ACCURACY_FINE);
		criteria1.setAltitudeRequired(false);
		criteria1.setBearingRequired(false);
		criteria1.setCostAllowed(true);
		criteria1.setPowerRequirement(Criteria.POWER_LOW);
		String provider1 = locationManager.getBestProvider(criteria1, true);

		Location location1 = locationManager.getLastKnownLocation(provider1);
		my_updateWithNewLocation(location1);
		
		locationManager.requestLocationUpdates(provider1, 200, 5,   
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
			positionOverlay1.setLocation(location);

			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(), 
					geoLng.intValue());

			mapController1.animateTo(point);

			//double lat = location.getLatitude();
		//	double lng = location.getLongitude();
			//latLongString = "Lat:" + lat + "\nLong:" + lng;
			locations1.add(location);
			if((locations1.size()>=2)){
	    		
	    		for(int i=0;i<locations1.size()-1;i++){
	    			Location loc1=locations1.get(i);
	    			Location loc2=locations1.get(i+1);
	    			dolzina+=loc1.distanceTo(loc2);   
	    			DecimalFormat test=new DecimalFormat("#.##");
	    			dolzina=Double.valueOf(test.format(dolzina));
	    			//loc1.gett
	    		}
	    		//pot.setText("Pot: "+dolzina+"m");
	    		
			}
			//myLocationText.setText("Trenutni položaj je:" + latLongString);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_zazeniPot:
			Intent zagon=new Intent(this,glavniasist.class);
			startActivityForResult(zagon,GLAVNO_OKNO_ID);
			//startActivity(zagon);
			break;
		case R.id.btnNazaj:
			setResult(RESULT_CANCELED);
			finish();
			break;
		default:
			break;
		}
	}
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

}
