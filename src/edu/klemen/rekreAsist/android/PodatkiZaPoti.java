package edu.klemen.rekreAsist.android;

import com.google.android.maps.GeoPoint;


public class PodatkiZaPoti {
	public long idP;
//	public double Xkord;// koordinati X in Y
//	public double Ykord;
//	public GeoPoint tocka;
	public int lat;
	public int lon;
	
	
	public void setID(long l)
	{idP=l;}
	public PodatkiZaPoti(){}
	
	public PodatkiZaPoti(GeoPoint g){
//		tocka=g;
		lat=g.getLatitudeE6();
		lon=g.getLongitudeE6();
	}
	public PodatkiZaPoti(int a, int b){
//		tocka= new GeoPoint(a, b);
		lat=a;
		lon=b;
	}
	public void setTocka(int a, int b){
//		tocka= new GeoPoint(a,b);
		lat=a;
		lon=b;
	}
	public void setTocka(GeoPoint a) {
		// TODO Auto-generated method stub
//		tocka= a;
		lat=a.getLatitudeE6();
		lon=a.getLongitudeE6();
	}

	
}
