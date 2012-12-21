package edu.klemen.rekreAsist.android;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class SeznamPodatkov extends ListActivity implements OnItemClickListener , OnItemLongClickListener {
	
	ApplicationControl app;
	Intent prikazIzbire;
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stevec_list_activity);
        
        this.setRequestedOrientation(1);
        app=(ApplicationControl) getApplication();
        setListAdapter(app.podatkiList);
		this.getListView().setOnItemClickListener(this);
		this.getListView().setOnItemLongClickListener(this);
		prikazIzbire=new Intent(this,PrikazIzbranePoti.class);

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		Log.e("test",""+arg2);
	}
	private long a=0;
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		// TODO Auto-generated method stub
		//dialogi +
		a=arg2;
		final Dialog izbira= new Dialog(this);
		izbira.setContentView(R.layout.seznam_dialog);
		izbira.setTitle("Rekreacija "+(arg2+1)+"");
		izbira.setCancelable(true);
		Button prikazi, preklici, zbrisi;
		prikazi=(Button) izbira.findViewById(R.id.button_seznam_prikazi);
		preklici=(Button ) izbira.findViewById(R.id.button_seznam_preklici);
		zbrisi=(Button) izbira.findViewById(R.id.button_seznam_zbrisi);
		
		
		final Dialog potrditev= new Dialog(this);
		potrditev.setContentView(R.layout.seznam_dialog_potrditev);
		potrditev.setTitle("Prosim potrdi za brisanje");
		potrditev.setCancelable(true);
		Button potrditevDA, potrditevNE;
		potrditevDA=(Button) potrditev.findViewById(R.id.button_seznam_dialog_potrditev_da);
		potrditevNE=(Button) potrditev.findViewById(R.id.button_seznam_dialog_potrditev_preklici);
		
		//dialogi -
		
		
		
		
		prikazi.setOnClickListener(new OnClickListener() { // gumb na dialogi za prikaz poti
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				app.izbranaPrikazPot=app.lista.get((int)a).idPovezava;//preko id povezave dobi katero pot prikazat
				startActivity(prikazIzbire);
				izbira.dismiss();
			}

		});
		preklici.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				izbira.dismiss();
				
			}
		});
		zbrisi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				
				potrditev.show();
				
				izbira.dismiss();
			}
			
		});
		potrditevDA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				app.brisiRekreacijo(app.lista.get((int)a).idPovezava);//preko id povezave zbriše celotno rekreacijo 
			//	app.lista.remove((int)a);
				app.podatkiList.notifyDataSetChanged();//refresha seznam
				potrditev.dismiss();
				
			}
		});
		potrditevNE.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				potrditev.dismiss();
			}
		});
		
		
		izbira.show();
		
		
//		Log.e("test1",""+arg2);
//		Log.e("test2",""+arg3);
		return false;
	}

}

    

