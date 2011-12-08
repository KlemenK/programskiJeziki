package edu.klemen.rekreAsist.android;


public class PodatkiZaPoti {
	public long id;
	public double Xkord;// koordinati X in Y
	public double Ykord;
	
	public void setID(long l)
	{id=l;}
	public PodatkiZaPoti(){}
	public PodatkiZaPoti(double m, double c){
		Xkord=m;
		Ykord=c;
	}
	
	
}
