package com.assignment.savekiwi;


import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		// setContentView(new gameView(this));
		setContentView(R.layout.activity_main);
		gameView = (GameView) findViewById(R.id.gameView);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC); //for music later
				
		//for additional buttons later
//		final ImageButton btnUp = (ImageButton) findViewById(R.id.btnUp);
//		btnUp.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View view, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					gameView.moveUp();
//					// gameView.update();
////					return true;
//				}
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					gameView.resetFrameLeft();
//					// gameView.burning = false;
////					return true;
//				}
//				return false;
//			}
//		});

		final ImageButton btnLeft = (ImageButton) findViewById(R.id.btnLeft);
		btnLeft.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.moveLeft();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.resetFrame();
				}
				return false;
			}
		});

		final ImageButton btnRight = (ImageButton) findViewById(R.id.btnRight);
		btnRight.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.moveRight();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.resetFrame();
				}

				return false;
			}
		});
		
	}
	
	public void alert() {
		// exit button
					AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);			
					exitDialog.setMessage("Would you like to exit ?");
					exitDialog.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									finish();
									int pid = android.os.Process.myPid();
									android.os.Process.killProcess(pid);
								}
							});
					exitDialog.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					exitDialog.show();
	}

	//backbutton on phone closes app
	@Override
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		finish();
		// maybe ok
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
	}

}
