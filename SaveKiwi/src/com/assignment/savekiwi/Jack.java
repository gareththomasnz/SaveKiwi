package com.assignment.savekiwi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Jack {
	

	private Bitmap bitmap;
	int x;
	int y;

	public static final double INITIAL_TIME = 10.5; // 3.5 default
	static final int REFRESH_RATE = 20;
	static final int VELOCITY = 1;
	double t = INITIAL_TIME;

	private Rect src; // the rectangle to be drawn from the animation bitmap
	private Rect dst; //rectangle to be drawn into
	
	public int spriteWidth; // the width of the sprite to calculate the cut out
								// rectangle
	public int spriteHeight; // the height of the sprite

	private int currentFrame = 0; // the current frame in sprite image
	public int jackDirection = 0; // 0 = normal 1 = left flame 2 = right flame 3 = left and right flame
								// 4 = burning Jack
	private double x_move = 0; // changes horizontal position of hero

	private long frameTicker; // the time of the last frame update
	private int framePeriod; // milliseconds between each frame (1000/fps)
	private int frameNr = 4; // number of frames in animation

//	public int DEAD = 0;
//	public int ALIVE = 1;
//	public int state;
	
	
	// public Jack(Bitmap bitmap) {
	// this.bitmap = bitmap;
	//
	// }
	
	public Jack(Bitmap bitmap, int width, int height) {
		this.bitmap = bitmap;		
		width = spriteWidth;
		height = spriteHeight;
		spriteWidth = bitmap.getWidth() / 4; // 4 rows
//		spriteWidth = spriteWidth / spriteWidth * 480;
		spriteHeight = bitmap.getHeight() / 4; //4 columns
//		spriteHeight = spriteHeight / spriteHeight * 800;
		
//		state = ALIVE;
	}

	public Jack(Bitmap bitmap) {
		this.bitmap = bitmap;		
		spriteWidth = bitmap.getWidth() / 4; // 4 rows
//		spriteWidth = spriteWidth / spriteWidth * 480;
		spriteHeight = bitmap.getHeight() / 4; //5 columns
//		spriteHeight = spriteHeight / spriteHeight * 800;
		
//		state = ALIVE;
	}

//	public int getstate() {
//		return state;
//	}
//
//	public void setstate(int s) {
//		state = s;
//	}

	public int getSpriteWidth() {
		spriteWidth = bitmap.getWidth() / 4;
		return spriteWidth;
	}

	public int getSpriteHeight() {
		spriteHeight = bitmap.getHeight() / 4;
		return spriteHeight;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setMoveX(double speedx) {
		
		x_move = speedx;
		// animate();
		// currentFrame = ++currentFrame % 3;
//		currentFrame++;
	}

//	public void setMoveY(double speedy) {
//		
//		y_move = speedy;
////		currentFrame++;
//	}

	public void setDirection(int direction) {
		this.jackDirection = direction;
	}

	public void update(int adj_mov) {
//		if(state == ALIVE){
		// left or right controls and actual movement
		x += (adj_mov * x_move);
//		y += (adj_mov * y_move);

//		// simulate Jack physics
//		moveJack();
		
//		}
	}	

	public void moveJack() {
		x = (int) x + (int) ((0.1 * (VELOCITY * t * t)));
//		y = (int) y + (int) ((0.1 * (GRAVITY * t * t)));
		// t = t + 0.01; // increment the parameter for synthetic time by a
		// small amount
	}

	public void animate(long gameTime) {

		if (gameTime > frameTicker + framePeriod) {

			frameTicker = gameTime;

			// increment the frame

			currentFrame++;

			if (currentFrame >= frameNr) {

				currentFrame = 0;

			}

		}

		// currentFrame = ++currentFrame % 9;
	}

	public void draw(Canvas canvas) {
		// canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
		// y - (bitmap.getHeight() / 2), null);
//		if(state == ALIVE){
		// direction 0 = top row sprite, 1 = bottom row sprite
		int srcY = jackDirection * spriteHeight;
		int srcX = currentFrame * spriteWidth;

		// Rect src = new Rect(0, srcY, spriteWidth, srcY + spriteHeight);
		src = new Rect(srcX, srcY, srcX + spriteWidth, srcY + spriteHeight);
		dst = new Rect(x, y, x + spriteWidth, y + spriteHeight);
		canvas.drawBitmap(bitmap, src, dst, null);
//		}
	}

}
