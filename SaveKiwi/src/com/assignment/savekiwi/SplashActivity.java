package com.assignment.savekiwi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.content.Context; 
import android.os.Handler; 
import android.view.WindowManager;

public class SplashActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SKGameVars.display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/*display the splash screen image*/            
		setContentView(R.layout.splashscreen);
		/*start up the splash screen and main menu in a time delayed thread*/            
		SKGameVars.context = this;            
		new Handler().postDelayed(new Thread() {                
			@Override                
			public void run() {
				 Intent mainMenu = new Intent(SplashActivity.this, SKMainMenu.class);
				 SplashActivity.this.startActivity(mainMenu);
				 SplashActivity.this.finish();
				 overridePendingTransition(R.layout.fadein,R.layout.fadeout); 
			}
		 }, SKGameVars.GAME_THREAD_DELAY); 
		}
}