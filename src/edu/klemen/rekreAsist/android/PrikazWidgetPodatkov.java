package edu.klemen.rekreAsist.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class PrikazWidgetPodatkov extends Activity implements OnClickListener {
	ApplicationControl app;
	TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;

	int link;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prikazpodatkovwidget);
		app=(ApplicationControl)getApplication();
		
		tv1=(TextView) findViewById(R.id.tvDatumW);
		tv3=(TextView) findViewById(R.id.tvEmailW);
		tv2=(TextView) findViewById(R.id.tvKontaktW);
		tv4=(TextView) findViewById(R.id.tvKrajW);
		tv5=(TextView) findViewById(R.id.tvNazivW);
		tv6=(TextView) findViewById(R.id.tvOpisW);
		tv7=(TextView) findViewById(R.id.tvOrganizatorW);
		tv8=(TextView) findViewById(R.id.tvSportW);
		tv9=(TextView) findViewById(R.id.tvTelefonskaW);
		tv10=(TextView) findViewById(R.id.tvUrlW);
		tv10.setOnClickListener(this);

		
		
		
		try{
		int stevec=app.getStevec()%app.getPodatkiKoledarWidget().size()-1;
			link=stevec;
			tv1.setText("Datum:\n"+app.getPodatkiKoledarWidget().get(stevec).datum);
			//tv2.setText("E-pošta:\n"+app.getPodatkiKoledarWidget().get(stevec).email);
			tv2.setText("Kontakt:\n"+app.getPodatkiKoledarWidget().get(stevec).kontakt.replace("kontaktna oseba-e: ", ""));
	//		tv3.setText("Kontakt:\n"+app.getPodatkiKoledarWidget().get(stevec).kontakt);
			tv3.setText("");
			tv4.setText("Kraj:\n"+app.getPodatkiKoledarWidget().get(stevec).kraj);
			tv5.setText("Naziv:\n"+app.getPodatkiKoledarWidget().get(stevec).naziv);
			tv6.setText("Opis:\n"+app.getPodatkiKoledarWidget().get(stevec).opis.replace("opis prireditve: ", ""));
			tv7.setText("Organizator:\n"+app.getPodatkiKoledarWidget().get(stevec).organizator.replace("organizator-ji: ", ""));
			tv8.setText("Sport:\n"+app.getPodatkiKoledarWidget().get(stevec).sport);
	//		tv9.setText("Telefon:\n"+app.getPodatkiKoledarWidget().get(stevec).telefonska);
			tv9.setText("");
			SpannableString tekst= new SpannableString("URL:\n"+app.getPodatkiKoledarWidget().get(stevec).url);
			tekst.setSpan(new UnderlineSpan(), 5, tekst.length(), 0);
			tv10.setText(tekst);
			tv10.setTextColor(Color.CYAN);
			tv10.setTextAppearance(this, 2);
		}catch(Exception ex){
			tv1.setText("Datum:");
			//tv2.setText("E-pošta:\n"+app.getPodatkiKoledarWidget().get(stevec).email);
			tv2.setText("Kontakt:");
	//		tv3.setText("Kontakt:\n"+app.getPodatkiKoledarWidget().get(stevec).kontakt);
			tv3.setText("Kontakt:");
			tv4.setText("Kraj:");
			tv5.setText("Naziv:");
			tv6.setText("Opis:");
			tv7.setText("Organizator:");
			tv8.setText("Sport:");
	//		tv9.setText("Telefon:\n"+app.getPodatkiKoledarWidget().get(stevec).telefonska);
			tv9.setText("Kontakt:");
			tv10.setText("URL:");
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
			tv10.setTextColor(Color.RED);
			Uri povezava= Uri.parse(app.getPodatkiKoledarWidget().get(link).url);
			Intent pogon= new Intent(Intent.ACTION_VIEW,povezava);
			startActivity(pogon);
			}catch(Exception e){}
		
	}

}
