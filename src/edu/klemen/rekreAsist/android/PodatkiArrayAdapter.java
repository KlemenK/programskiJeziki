package edu.klemen.rekreAsist.android;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class PodatkiArrayAdapter extends ArrayAdapter<podatkiZaBazo>{
	
	LayoutInflater mInflater;
	int i=0;
	
	public PodatkiArrayAdapter(Context context, int textViewResourceId, List<podatkiZaBazo> objects) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	    i=0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		podatkiZaBazo tmp = getItem(position); //Step Step 4.7 pridobi data
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.seznam_podatkov, null); //Step Step 4.7.5 DOLOČI ROW LL
			// Creates a ViewHolder and store references to the skupnaCena children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.datum = (TextView) convertView.findViewById(R.id.tv_ListDatum); //Step 4.8 POPRAVI
			holder.cas = (TextView) convertView.findViewById(R.id.tv_ListCas); //Step 4.8 POPRAVI
			holder.steviloKrogov=(TextView)convertView.findViewById(R.id.tv_ListStKrogov);
			holder.pot=(TextView) convertView.findViewById(R.id.tv_ListPot);
			holder.hitrost=(TextView) convertView.findViewById(R.id.tv_ListHitrost);
			holder.povprecnaHitrost=(TextView) convertView.findViewById(R.id.tv_ListPovprecnaHitrost);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		// Bind the data efficiently with the holder.
		holder.datum.setText("Datum: "+tmp.datum); //Step 4.8 POPRAVI
		String[] s=tmp.cas.split(":");
		if(s.length==3) holder.cas.setText("cas:"+tmp.cas+"h");
		else holder.cas.setText("cas:"+tmp.cas+"min");
		holder.steviloKrogov.setText("st. krogov:\n"+tmp.stKrogov);
		holder.povprecnaHitrost.setText("~hitrost:"+tmp.povPrecnaHitrost+"km/h");
		holder.hitrost.setText("max hitrost:"+tmp.maxSpeed+"km/h");
		holder.pot.setText("pot:"+tmp.pot+"m");
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		i++;
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView datum; //Step 4.8 POPRAVI
		TextView pot; //Step 4.8 POPRAVI
		TextView hitrost;
		TextView povprecnaHitrost;
		TextView cas;
		TextView steviloKrogov;
	}


}
