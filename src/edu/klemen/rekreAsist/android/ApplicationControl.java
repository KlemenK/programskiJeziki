package edu.klemen.rekreAsist.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Application;
import android.database.Cursor;

import com.google.android.maps.GeoPoint;

import edu.klemen.rekreAsist.android.database.DBAdapterBaza;


public class ApplicationControl extends Application {
	//Step 4.1
	//Step 4.2 popravi AndroidManifest.xml
	
	/*
	 * widget
	 */
	//private List<PodatkiSportniKoledar> news;
	private int stevec;
	public long izbranaPrikazPot=0;
	
	private List<PodatkiSportniKoledar> podatkiKoledarWidget;
	
	public void setPodatkiKoledarWidget(List<PodatkiSportniKoledar> list) {
		this.podatkiKoledarWidget = list;
	}
	public List<PodatkiSportniKoledar> getPodatkiKoledarWidget() {
		return podatkiKoledarWidget;
	}
	
	public int getStevec() {
		return stevec;
	}
	public void setStevec(int stevec) {
		this.stevec = stevec;
	}
	//konec widget
	/*
	 * baze
	 * */
    public  PodatkiArrayAdapter podatkiList; //seznam
    
	DBAdapterBaza db;
	public ArrayList<podatkiZaBazo> lista;
	
//	DBAdapterBazaPoti dbPoti;
	public ArrayList<PodatkiZaPoti> listaPoti;
//konec baze
/*
 * Izbira poti
 * */
	ArrayList<GeoPoint> izbranaPotLokacije= new ArrayList<GeoPoint>();
	public boolean FLAG_IZBRANA_POT=false;
	public void setIzbranoPot(ArrayList<GeoPoint> izbrana){
		this.izbranaPotLokacije=izbrana;
		FLAG_IZBRANA_POT=true;
	}
	public ArrayList<GeoPoint> getIzbranaPot(){
		return this.izbranaPotLokacije;
	}
	public boolean resetIzbranaPot(){
		if(!(this.izbranaPotLokacije.isEmpty())){
			this.izbranaPotLokacije.clear();
			FLAG_IZBRANA_POT=false;
			return true;
		}
		return false;
	}
	
	/*
	 * konec Izbira poti
	 * */
	public void onCreate() {
        super.onCreate(); //ne pozabi
        db = new DBAdapterBaza(this);
//        dbPoti= new DBAdapterBazaPoti(this);
        
      
        lista= new ArrayList<podatkiZaBazo>();
        listaPoti= new ArrayList<PodatkiZaPoti>();
 
        init();
        initPoti(); //testni podatki
        
//        fillFromDB();
//        if(lista.size()!=0) ID_ZA_POTI=lista.get(lista.size()-1).idPovezava;
        podatkiList = new PodatkiArrayAdapter(this, R.layout.seznam_podatkov,lista);//podatkiList-seznam, R-layout ki bo izpiso, s keriga seznama

	}
	

	
	public void init()
	{
		//testni podatki
		lista.add(new podatkiZaBazo(5202,9,5,14,"35:55","5.6.2011",150,1));
		lista.add(new podatkiZaBazo(6020,16,2,25,"43:45","7.6.2011",196,2));
		lista.add(new podatkiZaBazo(370,13,2,18,"00:55","7.6.2011",10,3));
		lista.add(new podatkiZaBazo(650,7,2,10,"02:45","9.6.2011",12,4));


		addDB(lista.get(0));
		addDB(lista.get(1));
		addDB(lista.get(2));
		addDB(lista.get(3));

	}
	public void initPoti()
	{
		//testni podatki
		Random ran= new Random(System.currentTimeMillis());
		PodatkiZaPoti tmp = new PodatkiZaPoti();
		for(int i=0;i<200;i++){
//			Log.e("test", "v zanki "+i);
			if(i<50) tmp.idP=1;
			else if(i<100) tmp.idP=2;
			else if(i<150) tmp.idP=3;
			else if(i<200) tmp.idP=4; //extDouble() * (5.12 - (-5.12)) + (-5.12);
			tmp.Xkord=ran.nextDouble()* (90 -(-90)) + (-90);
			tmp.Ykord=ran.nextDouble()* (90 -(-90)) + (-90);
			
			addDBPot(tmp);
		}

	}

	public void dodajPodatke(podatkiZaBazo tmp)//za rekreacije
	{
		lista.add(tmp);//orginal
		addDB(tmp);//orginal
		
		long ID_ZA_POTI=lista.get(lista.size()-1).idPovezava;//dobim zadni števec
		
		for(int i=0;i<listaPoti.size();i++){
			listaPoti.get(i).setID(ID_ZA_POTI);
			addDBPot(listaPoti.get(i));
		}
	}
	
	
	
//	public void dodajPodatkePot(PodatkiZaPoti tmp)//za poti
//	{
//		listaPoti.add(tmp);
//		addDBPot(tmp);
//	}

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
			tmp.kalorije=c.getInt(DBAdapterBaza.POS__KALORIJE);
			tmp.idPovezava=c.getLong(DBAdapterBaza.POS_ID_POVEZAVA);
			
			lista.add(tmp); 
			
			
		}
		c.close();
		db.close();
	}
	public void addDB(podatkiZaBazo s) {
		db.open();
	//	s.setID(db.insertPodatki(s));
		db.insertPodatki(s);
	//	trenutnID=s.id;
		db.close();	
	}
	public void addDBPot(PodatkiZaPoti s) {
		db.open();
		db.insertPodatki(s);
		db.close();	
	}
	
	public void getPotIzDB(int index) {//vrnem točke določene poti
		db.open();
		PodatkiZaPoti tmp;
		Cursor c= db.getRoute(index);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new PodatkiZaPoti();
			
			
			tmp.Xkord= c.getDouble(DBAdapterBaza.POS__XKORD);
			tmp.Ykord= c.getDouble(DBAdapterBaza.POS__YKORD);
			
			listaPoti.add(tmp); 

		}
		c.close(); 
		db.close();
	}
	public ArrayList<GeoPoint> getIzbranoPotIzDB(int index) {//vrnem točke določene poti
		ArrayList<GeoPoint> temp= new ArrayList<GeoPoint>();
		db.open();
		GeoPoint tmp;
		Cursor c= db.getRoute(index);
		Double a,b;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
//			tmp= new 
			a=c.getDouble(DBAdapterBaza.POS__XKORD);
			b=c.getDouble(DBAdapterBaza.POS__YKORD);
			
			tmp = new GeoPoint(a.intValue(),b.intValue());

//			tmp.= c.getDouble(DBAdapterBaza.POS__XKORD);
//			tmp.Ykord= c.getDouble(DBAdapterBaza.POS__YKORD);
			temp.add(tmp); 

		}
		c.close(); 
		db.close();
		return temp;
	}
	public void brisiRekreacijo(long index){//brišem rekreacijo
		db.open();		
		Cursor c= db.getStevec(index);
		
		//c.getCount();
//		Log.d("t1", "pred vrednostjo "+ index+"    "+c.getLong(DBAdapterBaza.POS_ID_POVEZAVA));
		for(int i=0;i<lista.size();i++) System.out.println("Pred indeksi pod:"+lista.get(i).id+"   pot:"+lista.get(i).idPovezava);
		try{
			long idPo= c.getLong(DBAdapterBaza.POS_ID_POVEZAVA);
			db.deletePodatekPoti(idPo);
			db.deletePodatek(index);
		}catch(Exception e){};
		c.close();
		db.close();
		lista.clear();
		fillFromDB();
		for(int i=0;i<lista.size();i++) System.out.println("Po indeksi pod:"+lista.get(i).id+"   pot:"+lista.get(i).idPovezava);
		System.out.println("po brisanju"+lista.size());
		
//		Log.d("t1", "vrednost "+idPo);
//		dbPoti.open();
//		dbPoti.deletePodatek(idPo);
//		dbPoti.close();
	}



}
