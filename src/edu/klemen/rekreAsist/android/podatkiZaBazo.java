package edu.klemen.rekreAsist.android;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class podatkiZaBazo {
	public double kalorije;
	public String datum;
	public double pot;
	public double povPrecnaHitrost;
	public int stKrogov;
	public double maxSpeed; 
	public String cas;
	public long id;
	public void setID(long l)
	{id=l;}
	public podatkiZaBazo(){}
	public podatkiZaBazo(double p, double po, int s, double m, String c ,String d, double k){
		pot=p;
		povPrecnaHitrost=po;
		stKrogov=s;
		maxSpeed=m;
		cas=c;
		datum=d;
		kalorije=k;
	}
	
	
}
