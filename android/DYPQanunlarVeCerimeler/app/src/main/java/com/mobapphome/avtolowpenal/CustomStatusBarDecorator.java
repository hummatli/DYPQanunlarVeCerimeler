package com.mobapphome.avtolowpenal;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CustomStatusBarDecorator {

	static public int getStatusBarHeight(Activity activity) { 
	      int result = 0;
	      int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = activity.getResources().getDimensionPixelSize(resourceId);
	      } 
	      return result;
	} 
	
	static public void decorateStatusBar(Activity activity){
        View customStatusBarForKitKat = activity.findViewById(R.id.statusBarForKitKat);
        customStatusBarForKitKat.getLayoutParams().height = 0;
        
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
        	int id = activity.getResources().getIdentifier("config_enableTranslucentDecor", "bool", "android");
        	if (id > 0) {
        		boolean enabled = activity.getResources().getBoolean(id);
        		// enabled = are translucent bars supported on this device
        		//Log.i("Test", "On kitkat enabled= " + enabled);
        		if(enabled){
        			Window w = activity.getWindow(); // in Activity's onCreate() for instance
        			//w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        			int statusBarHeight = getStatusBarHeight(activity);
        			Log.i("Test", "Status bar height = " + statusBarHeight);
        			customStatusBarForKitKat.getLayoutParams().height = statusBarHeight;
        		}
        	} else {
        		// not on KitKat
        		// Log.i("Test", "Not KitKat");
        	}
        }
	}
}
