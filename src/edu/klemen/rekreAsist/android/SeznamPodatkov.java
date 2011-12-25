package edu.klemen.rekreAsist.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SeznamPodatkov extends ListActivity implements OnItemClickListener  {
	
	ApplicationExample app;
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stevec_list_activity);
        
        this.setRequestedOrientation(1);
        
        app=(ApplicationExample) getApplication();
        setListAdapter(app.podatkiList);
		this.getListView().setOnItemClickListener(this);
		

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e("test",""+arg2);
	}

}

    

