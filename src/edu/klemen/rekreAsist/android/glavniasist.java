package edu.klemen.rekreAsist.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class glavniasist extends Activity implements android.view.View.OnClickListener {
	Button startstop,pavza;
	TextView cas,pot;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glavni);
		
		startstop=(Button) findViewById(R.id.btnStartStop);
		pavza=(Button) findViewById(R.id.btnPavza);
		cas=(TextView) findViewById(R.id.cas);
		pot=(TextView) findViewById(R.id.pot);
		
		startstop.setOnClickListener(this);
		pavza.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnStartStop:
			setResult(RESULT_OK);
			finish();
			break;
		case R.id.btnPavza:
			setResult(RESULT_CANCELED);
			finish();
			break;

		default:
			break;
		}
	}

}
