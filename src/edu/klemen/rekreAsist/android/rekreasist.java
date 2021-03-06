package edu.klemen.rekreAsist.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class rekreasist extends Activity implements OnClickListener {
    private static final int GLAVNO_OKNO_ID = 0;
	private static final int IZBIRA_POTI_ID = 1;
	private static final int SEZNAM_ID=2;
	ApplicationControl podatki;
	/** Called when the activity is first created. */
	ImageButton izhod,znova,pot,seznam;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.setRequestedOrientation(1);
        
        
        podatki=(ApplicationControl)getApplication();
        
        izhod=(ImageButton) findViewById(R.id.btnIzhod);
        znova=(ImageButton) findViewById(R.id.btnZnova);
        pot=(ImageButton) findViewById(R.id.btnIzberiPot);
        seznam=(ImageButton) findViewById(R.id.btnSeznam);
        
        izhod.setOnClickListener(this);
        znova.setOnClickListener(this);
        pot.setOnClickListener(this);
        seznam.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		//if(v.getId()==R.id.btnIzhod) finish();
		switch(v.getId()){
			case R.id.btnIzberiPot:
				Intent zagonPoti=new Intent(this, izbiraPoti.class);
				this.startActivityForResult(zagonPoti, IZBIRA_POTI_ID);
	//			startActivity(zagonPoti);
				break;
			case R.id.btnIzhod:
				finish();
				break;
			case R.id.btnZnova:
				Intent zagon=new Intent(this, glavniasist.class);
				this.startActivityForResult(zagon, GLAVNO_OKNO_ID);
				//startActivity(zagon);
				break;
			case R.id.btnSeznam:
				Intent zagonSeznam=new Intent(this, SeznamPodatkov.class);
				this.startActivityForResult(zagonSeznam,SEZNAM_ID);
	//			startActivity(zagon1);
	
				break;
				
				default:
					break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode) {

        case GLAVNO_OKNO_ID:
        	if(resultCode==RESULT_CANCELED) 
        	break;

        	
		}
	}
}