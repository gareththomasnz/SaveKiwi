//public static Display display; 
//public static Context context;
//public static final int GAME_THREAD_DELAY = 3000;

package com.assignment.savekiwi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.ImageButton;

public class SKMainMenu extends Activity{
	
	
	/** Called when the activity first created */
	SKGameVars SKGameEngine = new SKGameVars();
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SKGameVars.context = getApplicationContext();
		
		/** Set menu button options */ 
		ImageButton start = (ImageButton)findViewById(R.id.btnStart);
		ImageButton exit = (ImageButton)findViewById(R.id.btnExit);
		start.getBackground().setAlpha(SKGameVars.MENU_BUTTON_ALPHA);
		start.setHapticFeedbackEnabled(SKGameVars.HAPTIC_BUTTON_FEEDBACK);
		exit.getBackground().setAlpha(SKGameVars.MENU_BUTTON_ALPHA);
		exit.setHapticFeedbackEnabled(SKGameVars.HAPTIC_BUTTON_FEEDBACK);
		
		 start.setOnClickListener(new OnClickListener(){
			 @Override
			public void onClick(View v) {
				 /** Start Game!!!! */
				 Intent game = new Intent(getApplicationContext(), MainActivity.class); 
				 SKMainMenu.this.startActivity(game); 
				
			}
		 });
		exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int pid = android.os.Process.myPid();
				android.os.Process.killProcess(pid);
			} 
		});
	 }
}
