package edu.klemen.rekreAsist.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class splash extends Activity{

	protected int _splashTime = 5000; //5s
		 
		    private Thread splashTread;
		 
		    /** Called when the activity is first created. */
		    @Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.splash);
		 
		        final splash sPlashScreen = this;
		        this.setRequestedOrientation(1);
		 
		        // thread for displaying the SplashScreen
		        splashTread = new Thread() {
		            @Override
		            public void run() {
		                try {
		                    synchronized(this){
		 
		                        //wait 5 sec
		                        wait(_splashTime);
		                    }
		 
		                } catch(InterruptedException e) {}
		                finally {
		                    finish();
		 
		                    //start a new activity
		                    Intent i = new Intent();
		                    i.setClass(sPlashScreen, rekreasist.class);
	                    startActivity(i);
	 
		                    stop();
		                }
		            }
		        };
		 
		        splashTread.start();
		    }
		 
		    //Function that will handle the touch
		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		            synchronized(splashTread){
		                splashTread.notifyAll();
		            }
		        }
		        return true;
		    }
}
