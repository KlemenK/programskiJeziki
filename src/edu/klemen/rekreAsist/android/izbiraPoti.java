package edu.klemen.rekreAsist.android;

import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class izbiraPoti extends Activity implements OnClickListener {
	private static final int GLAVNO_OKNO_ID = 0;
	Button nazaj,zazeni;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.izbirapoti);
		
		
		nazaj=(Button) findViewById(R.id.btnNazaj);
		zazeni=(Button) findViewById(R.id.btn_zazeniPot);
		
		nazaj.setOnClickListener(this);
		zazeni.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_zazeniPot:
			Intent zagon=new Intent(this,glavniasist.class);
			startActivityForResult(zagon,GLAVNO_OKNO_ID);
			//startActivity(zagon);
			break;
		case R.id.btnNazaj:
			setResult(RESULT_CANCELED);
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode) {

        case GLAVNO_OKNO_ID:
        	if(resultCode==RESULT_CANCELED) this.finish();
        	break;        	
		}
	}

}
