package edu.klemen.rekreAsist.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.database.Cursor;
import edu.klemen.rekreAsist.android.database.DBAdapterBaza;

public class ApplicationExample extends Application {
	//Step 4.1
	//Step 4.2 popravi AndroidManifest.xml
	
	/*
	 * widget
	 */
	private List<String> news;
	private int stevec;
	public List<String> getNews() {
		return news;
	}
	public void setNews(List<String> news) {
		this.news = news;
	}
	public int getStevec() {
		return stevec;
	}
	public void setStevec(int stevec) {
		this.stevec = stevec;
	}
	//konec widget
	
    public  PodatkiArrayAdapter podatkiList;
	DBAdapterBaza db;
	public ArrayList<podatkiZaBazo> lista;


	public void onCreate() {
        super.onCreate(); //ne pozabi
        db = new DBAdapterBaza(this); 
        
      
        lista= new ArrayList<podatkiZaBazo>();
 
        //init();
        fillFromDB();
        podatkiList = new PodatkiArrayAdapter(this, R.layout.seznam_podatkov,lista);//podatkiList-seznam, R-layout ki bo izpiso, s keriga seznama

	}
	

	
	public void init()
	{
		//testni podatki
		lista.add(new podatkiZaBazo(5202,9,5,14,"35:55","5.6.2011"));
		lista.add(new podatkiZaBazo(6020,16,2,25,"43:45","7.6.2011"));
		lista.add(new podatkiZaBazo(370,13,2,18,"00:55","7.6.2011"));
		lista.add(new podatkiZaBazo(650,7,2,10,"02:45","9.6.2011"));


		addDB(lista.get(0));
		addDB(lista.get(1));
		addDB(lista.get(2));
		addDB(lista.get(3));

	}

	public void dodajPodatke(podatkiZaBazo tmp)
	{
		lista.add(tmp);
		addDB(tmp);
	}

	//DB dodano
	public void fillFromDB() {
		db.open();
		Cursor c = db.getAll();
		podatkiZaBazo tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new podatkiZaBazo();
			
			tmp.pot= c.getDouble(DBAdapterBaza.POS__POT);
			tmp.povPrecnaHitrost= c.getDouble(DBAdapterBaza.POS__POVPRECNA_HITROST);
			tmp.stKrogov=c.getInt(DBAdapterBaza.POS__ST_KROGOV);
			tmp.maxSpeed=c.getDouble(DBAdapterBaza.POS__MAX_SPEED);
			tmp.cas=c.getString(DBAdapterBaza.POS__CAS);
			tmp.datum= c.getString(DBAdapterBaza.POS__DATUM);
			
			
			lista.add(tmp); 
			
			
		}
		c.close();
		db.close();
	}
	public void addDB(podatkiZaBazo s) {
		db.open();
		s.setID(db.insertPodatki(s));
		db.close();	
	}

	

}
