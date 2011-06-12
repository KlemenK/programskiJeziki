package edu.klemen.rekreAsist.android;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class Vadba {
	public ArrayList<Location> pot;
	public List<Float> hitrost;
	public int stKrogov;
	public float maxSpeed; 
	public String time;
	
	public Vadba(ArrayList<Location> p, List<Float> h, int stK, float max, String t){
		pot=p;
		hitrost=h;
		stKrogov=stK;
		maxSpeed=max;
		time=t;
	}

}
