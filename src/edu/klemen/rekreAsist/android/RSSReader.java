package edu.klemen.rekreAsist.android;


import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import android.widget.Toast;

public class RSSReader {

	public RSSReader() {}
//	private static RSSReader instance = null;
	private static final String SOAP_ACTION="http://sportniKoledar.klemen.edu/preberiPodatke";
	private static final String METHOD_NAME="preberiPodatke";
	private static final String NAMESPACE="http://sportniKoledar.klemen.edu";
	private static final String URL="http://192.168.1.6:8080/SportniKoledar/services/PreberiPodatke?wsdl";
	private String rezultat="";
//	public static RSSReader getInstance() {
//		if (instance == null) {
//			instance = new RSSReader();
//		}
//		return instance;
//	}

	public List<PodatkiSportniKoledar> readNews() {
		List<PodatkiSportniKoledar> news = new ArrayList<PodatkiSportniKoledar>();
		
		SoapObject request= new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet=false;
			envelope.setOutputSoapObject(request);
			HttpTransportSE httpTransport= new HttpTransportSE(URL);
		try {
			
			
			httpTransport.call(SOAP_ACTION, envelope);
			Object response= envelope.getResponse();
			rezultat= response.toString();
			Log.e(""+rezultat.length(), rezultat.substring(0, 200));
			
			
		
		// podatki ;; objekti ;::;
		PodatkiSportniKoledar vmesni;
		String[] objekti;
		String[] razb;
		String[] podatkiObjektov;
		objekti=rezultat.split(";::;");                  Log.d(""+objekti.length,"");

		for(int i=0;i<objekti.length;i++){

			vmesni= new PodatkiSportniKoledar();
			podatkiObjektov=objekti[i].split(";;");
			
			vmesni.datum=podatkiObjektov[0];
			vmesni.kraj=podatkiObjektov[1];
			vmesni.sport=podatkiObjektov[2];
			vmesni.naziv=podatkiObjektov[3];
			vmesni.url=podatkiObjektov[4].replace(" ", "");
			vmesni.email=podatkiObjektov[5];
			vmesni.opis=podatkiObjektov[6];
			vmesni.organizator=podatkiObjektov[7];
			
//			String test="pe;e; ; ";
//			razb=test.split(";");
//			Log.d(""+razb.length, "test");
			vmesni.kontakt=podatkiObjektov[8];
//			razb=podatkiObjektov[8].split("|");
//			for(int j=0;j<razb.length;j++){
//				if(razb[j].contains("@")){ vmesni.email=razb[j];//pridobim email iz kontaktov če še ni bil izluščen	
//				}else if(razb[j].contains("0")) vmesni.telefonska=razb[j];//pridobim telefonsko iz kontaktov
//				else vmesni.kontakt=razb[j].replace("kontaktna oseba-e:", "");//pridobim kontakt iz kontaktov
//			}
			news.add(vmesni);
			
//			Log.d("datum: "+vmesni.datum,"");
//			Log.d("kraj: "+vmesni.kraj,"");
//			Log.d("sport: "+vmesni.sport,"");
//			Log.d("naziv: "+vmesni.naziv,"");
//			Log.d("url "+vmesni.url,"");
//			Log.d("email "+vmesni.email,"");
//			Log.d("opis "+vmesni.opis,"");
//			Log.d("organizator "+vmesni.organizator,"");
//			Log.d("telefonska "+vmesni.telefonska,"");
//			Log.d("kontakt "+vmesni.kontakt,"");
//			Log.d("----------------------------","");
			
		}
		}// try
		catch (Exception ex) {
		return null;
		
	}
		
		return news;

	}

}