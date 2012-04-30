package edu.klemen.rekreAsist.android;


public class PodatkiZaPoti {
	public long idP;
	public double Xkord;// koordinati X in Y
	public double Ykord;
	
	
	public void setID(long l)
	{idP=l;}
	public PodatkiZaPoti(){}
	public PodatkiZaPoti(double m, double c){
		Xkord=m;
		Ykord=c;
	}
	
	
}
