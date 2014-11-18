package com.assignment.savekiwi;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameThread extends Thread {
	
	private boolean running;
	private SurfaceHolder holder;
	private GameView gameView;

	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameThread(SurfaceHolder holder, GameView gameView) {
		super();
		this.holder = holder;
		this.gameView = gameView;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (running) {
			Canvas canvas = null;
			canvas = holder.lockCanvas();
			// if statement prevents crash when back button pressed
			if (canvas != null) {
				synchronized (holder) {

					gameView.update();
					gameView.render(canvas);
				}
			}
			try {
				Thread.sleep(50); //book 30 higher number slows game
			} catch (Exception e) {
			}

			finally {
				if (canvas != null) { // if statement prevents crash when back button pressed
					holder.unlockCanvasAndPost(canvas);
				}
			}

		} // end of while running

	} // end of run

}
