package edu.klemen.rekreAsist.android;


public class podatkiZaBazo {
	public double kalorije;
	public String datum;
	public double pot;
	public double povPrecnaHitrost;
	public int stKrogov;
	public double maxSpeed; 
	public String cas;
	public long idPovezava;
	public long id;
	public void setID(long l)
	{id=l;}
	public podatkiZaBazo(){}
	public podatkiZaBazo(double p, double po, int s, double m, String c ,String d, double k, long iP){
		pot=p;
		povPrecnaHitrost=po;
		stKrogov=s;
		maxSpeed=m;
		cas=c;
		datum=d;
		kalorije=k;
		idPovezava=iP;
	}
	
	
}
