package edu.klemen.rekreAsist.android;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class izbiraPoti extends MapActivity implements OnClickListener, OnTouchListener{
	private static final int GLAVNO_OKNO_ID = 0;
	
	
	MapController mapController1;
//	MyPositionOverlay positionOverlay1;
	ArrayList<Location> locations1;
//	GeoPoint konec, zacetek;
	MapView myMapView;
	
	private int ROUTE_FLAG=0;
	private int ROUTE_FLAG1=0;
	Button nazaj,zazeni,premik;
	LocationManager locationManager;
	Boolean lokacijaFlag=false;
	GeoPoint prvaLokacija;
	ArrayList<GeoPoint> izbranaPotLokacije;
	ApplicationExample podatki;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.izbirapoti);
		
        this.setRequestedOrientation(1);
		
		nazaj=(Button) findViewById(R.id.btnNazaj);
		zazeni=(Button) findViewById(R.id.btn_zazeniPot);
		premik=(Button) findViewById(R.id.btn_Premik1);
		
		
		nazaj.setOnClickListener(this);
		zazeni.setOnClickListener(this);
		premik.setOnClickListener(this);
		
		locations1=new ArrayList<Location>();
		izbranaPotLokacije= new ArrayList<GeoPoint>();
		podatki=(ApplicationExample)getApplication();

		try{
		//-----------------------------------------maps--
			myMapView = (MapView)findViewById(R.id.mapviewNova);
			mapController1 = myMapView.getController();
			
			
			myMapView.setOnTouchListener(this);
			
	
			myMapView.displayZoomControls(false);
	
	
			mapController1.setZoom(15);
				
			String context = Context.LOCATION_SERVICE;
			locationManager = (LocationManager)getSystemService(context);
	
			Criteria criteria1 = new Criteria();
			criteria1.setAccuracy(Criteria.ACCURACY_FINE);
			criteria1.setAltitudeRequired(false);
			criteria1.setBearingRequired(false);
			criteria1.setCostAllowed(true);
			criteria1.setPowerRequirement(Criteria.POWER_LOW);
			String provider1 = locationManager.getBestProvider(criteria1, true);
	
			Location location1 = locationManager.getLastKnownLocation(provider1);
			
			my_updateWithNewLocation(location1);
			locationManager.requestLocationUpdates(provider1, 0, 0, locationListener);
	

		}catch (Exception e) {
			// TODO: handle exception
		}
		
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

		if (location != null) {

			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(),geoLng.intValue());

			mapController1.animateTo(point);
			locations1.add(location);
			if(lokacijaFlag==false){
				Double lat= location.getLatitude()*1E6;
				Double lng= location.getLongitude()*1E6;
				prvaLokacija= new GeoPoint(lat.intValue(),lng.intValue());
				lokacijaFlag=true;
			}
	    	GeoPoint srcGeoPoint = new GeoPoint((int) (location.getLatitude() * 1E6),(int) (location.getLongitude() * 1E6)); 
	    	myMapView.getController().animateTo(srcGeoPoint);
		}

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_zazeniPot:
			Intent zagon=new Intent(this,glavniasist.class);
			startActivityForResult(zagon,GLAVNO_OKNO_ID);
			podatki.FLAG_IZBRANA_POT=true;
			//startActivity(zagon);
			break;
		case R.id.btnNazaj:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.btn_Premik1:
			if(ROUTE_FLAG==1 && ROUTE_FLAG1==1){
				myMapView.getOverlays().clear();
				izbranaPotLokacije.clear();
				podatki.resetIzbranaPot();
				premik.setText("Premikaj");
				ROUTE_FLAG=0;
				ROUTE_FLAG1=0;
			}else if(ROUTE_FLAG==0){
				ROUTE_FLAG=-1;
				premik.setTextSize(10);
				premik.setText("Premik stop");
			}
			else{
				ROUTE_FLAG=0;
				premik.setTextSize(14);
				premik.setText("Premikaj");
			}
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
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if((event.getActionMasked()==0)&&(ROUTE_FLAG==0)&&(ROUTE_FLAG1==0)){
			GeoPoint zadnaLokacija = myMapView.getProjection().fromPixels((int) event.getX(),(int) event.getY());
	       // Toast.makeText(getBaseContext(), p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 , Toast.LENGTH_SHORT).show();		
	        if(prvaLokacija!=null){
	        	DrawPath(prvaLokacija, zadnaLokacija, Color.RED, myMapView);
	        	ROUTE_FLAG=1;//pridobim samo eno pot
	        	ROUTE_FLAG1=1;
	        	premik.setText("Reset");
	        }
		}	
		return false;
	}
	
	
	
	private void DrawPath(GeoPoint src,GeoPoint dest, int color, MapView mMapView01) 
	{ 
		// connect to map web service 
		StringBuilder urlString = new StringBuilder(); 
		urlString.append("http://maps.google.com/maps?f=d&hl=en"); 
		urlString.append("&saddr=");//from 
		urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 )); 
		urlString.append("&daddr=");//to 
		urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 )); 
		urlString.append("&ie=UTF8&0&om=0&output=kml"); 
		//Log.d("xxx","URL="+urlString.toString()); 
		// get the kml (XML) doc. And parse it to get the coordinates(direction route). 
		Document doc = null; 
		HttpURLConnection urlConnection= null; 
		URL url = null; 
		try 
		{ 
			url = new URL(urlString.toString()); 
			urlConnection=(HttpURLConnection)url.openConnection(); 
			urlConnection.setRequestMethod("GET"); 
			urlConnection.setDoOutput(true); 
			urlConnection.setDoInput(true); 
			urlConnection.connect(); 
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			doc = db.parse(urlConnection.getInputStream());
			
			
			if(doc.getElementsByTagName("GeometryCollection").getLength()>0) 
			{ 
				//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName(); 
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ; 
				Log.d("xxx","path="+ path); 
				String [] pairs = path.split(" "); 
				String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height 
				// src 
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
				mMapView01.getOverlays().add(new MyOverlay(startGP,startGP,1));
				GeoPoint gp1; 
				GeoPoint gp2 = startGP; 
				izbranaPotLokacije.add(gp2);
				for(int i=1;i<pairs.length;i++) // the last one would be crash 
				{ 
					lngLat = pairs[i].split(","); 
					gp1 = gp2; 
					// watch out! For GeoPoint, first:latitude, second:longitude 
					gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
					izbranaPotLokacije.add(gp2);
					
					
					mMapView01.getOverlays().add(new MyOverlay(gp1,gp2,2,color)); 
					//Log.d("xxx","pair:" + pairs[i]); 
				} 
				mMapView01.getOverlays().add(new MyOverlay(dest,dest, 3)); // use the default color 
			} 
		} 
		catch (MalformedURLException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (ParserConfigurationException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (SAXException e) 
		{ 
			e.printStackTrace(); 
		}
		podatki.setIzbranoPot(izbranaPotLokacije);
		Log.d("asd", "test "+izbranaPotLokacije.size());
		Log.d("asd", "test1 "+myMapView.getOverlays().size());
	}

}
