package edu.klemen.rekreAsist.android;

import android.app.ListActivity;
import android.os.Bundle;

public class SeznamPodatkov extends ListActivity/* implements OnItemClickListener */ {
	
	ApplicationExample app;
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stevec_list_activity);
        
        this.setRequestedOrientation(1);
        
        app=(ApplicationExample) getApplication();
        setListAdapter(app.podatkiList);
		//this.getListView().setOnItemClickListener(this);
		

	}

}

    

