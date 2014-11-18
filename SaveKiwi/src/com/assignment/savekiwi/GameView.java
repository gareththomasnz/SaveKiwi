package com.assignment.savekiwi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread thread;
	private Jack jack;
	private Kiwi kiwi;
	private Bitmap background;
	// float x, y;
	private Paint groundPaint = new Paint();
	private int lineYGround; // ground boundary collision

	public boolean showFruit; // change to small bool

	private int screenWidth = 1;
	private int screenHeight = 1;
	// private int screenW = 1;
	// private int screenH = 1;

	private Paint scorePaint = new Paint();
	private int score = 0;

	private Paint missedPaint = new Paint();
	private int missed = 0;

	private float drawScaleW;
	private float drawScaleH;

	private Context con;
	private SoundPool soundPool;
	private int flame;
	private int explode;
	private int scoreSound;
	private int soundId;

	// private Bitmap fruitKiwi;
	private Bitmap gameOverDialog;

	private int backgroundOrigW;
	private int backgroundOrigH;
	private float scaleW;
	private float scaleH;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initialize(context);
	}

	public void initialize(Context context) {
		// gameOver = false;
		con = context;
		// display score
		scorePaint.setAntiAlias(true);
		scorePaint.setColor(Color.RED);
		scorePaint.setTextAlign(Paint.Align.RIGHT);
		scorePaint.setStyle(Paint.Style.STROKE);
		// // scorePaint.setTextSize(drawScaleW * 30); // 400

		// display missed
		missedPaint.setAntiAlias(true);
		missedPaint.setColor(Color.BLUE);
		missedPaint.setTextAlign(Paint.Align.RIGHT);
		missedPaint.setStyle(Paint.Style.STROKE);
		//
		// // display the fuel gauge
		// fuelBar = new RectF();
		// fuelPaint.setAntiAlias(true);
		// fuelPaint.setARGB(255, 0, 255, 0);
		// // fuelBar.set(4, 4, 4 + paintWidthBar, 4 + paintHeightBar);
		//
		// fuelText.setAntiAlias(true);
		// fuelText.setARGB(255, 0, 255, 0);
		// fuelText.setTextAlign(Paint.Align.LEFT);
		// fuelText.setStyle(Paint.Style.STROKE);
		// fuelText.setTextSize(drawScaleW * 30); // 400

		// color of line
		groundPaint.setColor(Color.DKGRAY);

		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.gamearea); // background

		// jack = BitmapFactory.decodeResource(getResources(),
		// R.drawable.craftmain);
		jack = new Jack(BitmapFactory.decodeResource(getResources(),
				R.drawable.jack_256));

		kiwi = new Kiwi(BitmapFactory.decodeResource(getResources(),
				R.drawable.kiwi_128));

		// gameOverDialog = BitmapFactory.decodeResource(getResources(),
		// R.drawable.imgover);

		// sounds
		// soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		// flame = soundPool.load(context, R.raw.flame, 1);
		// explode = soundPool.load(context, R.raw.explode, 1);
		// scoreSound = soundPool.load(context, R.raw.score, 1);

		// fruitKiwi = BitmapFactory.decodeResource(getResources(),
		// R.drawable.kiwifruit_32); // background

		getHolder().addCallback(this);

		thread = new GameThread(getHolder(), this);
		// so it can handle events
		setFocusable(true);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

		//
		// // fuelText.setTextSize(drawScaleW * 20); // 400
		// paintWidthBar = (int) (drawScaleW * 20);
		// paintHeightBar = (int) (drawScaleH * 20);
		// fuelBar.set(drawScaleW + 20, drawScaleH + 20, paintWidthBar + 20,
		// paintHeightBar + 20);

		screenWidth = width;
		screenHeight = height;

		// desired screen size for all devices maybe wrong test further
		drawScaleW = (float) screenWidth / 800;
		drawScaleH = (float) screenHeight / 400; // book 600

		scaleW = (float) screenWidth / (float) backgroundOrigW;
		scaleH = (float) screenHeight / (float) backgroundOrigH;

		backgroundOrigW = background.getWidth();
		backgroundOrigH = background.getHeight();

		background = Bitmap.createScaledBitmap(background, screenWidth,
				screenHeight, true);

		// ground height boundary for collision
		lineYGround = screenHeight - (screenHeight / 6); // a little higher than
															// ground	
		// size of score display
		scorePaint.setTextSize(drawScaleW * 40); // 400

		// size of missed display
		missedPaint.setTextSize(drawScaleW * 40); // 400
		
		// scale jack size
		jack = new Jack(BitmapFactory.decodeResource(getResources(),
				R.drawable.jack_256), (int) (jack.getSpriteWidth() * scaleW),
				(int) (jack.getSpriteHeight() * scaleH));

		// jack = new Jack(BitmapFactory.decodeResource(getResources(),
		// R.drawable.jack_256),
		// (int) (drawScaleW * jack.getSpriteWidth()),
		// (int) (drawScaleH * jack.getSpriteHeight()));

		// scale kiwi size
		kiwi = new Kiwi(BitmapFactory.decodeResource(getResources(),
				R.drawable.kiwi_128), (int) (kiwi.getSpriteWidth() * scaleW),
				(int) (kiwi.getSpriteHeight() * scaleH));

		// kiwi = new Kiwi(BitmapFactory.decodeResource(getResources(),
		// R.drawable.kiwi_128),
		// (int) (drawScaleW * kiwi.getSpriteWidth()),
		// (int) (drawScaleH * kiwi.getSpriteHeight()));

		// initial position of jack
		jack.setX(screenWidth / 3);
		jack.setY(screenHeight - (screenHeight / 4));
		// ship.setDirection(0);

		// initial position of kiwi
		kiwi.setX((int) (Math.random() * screenWidth - 60));
		kiwi.setY(0);

		// gameOverSprite
		// .setX((screenWidth - gameOverSprite.getSpriteWidth()) / 2);
		// gameOverSprite.setY(screenHeight / 2);

		// gameOverDialog = Bitmap.createScaledBitmap(gameOverDialog,
		// (screenWidth - gameOverDialog.getWidth()) / 2, screenHeight / 2,
		// true);
		// size of bitmap
		// gameOverDialog = Bitmap.createScaledBitmap(gameOverDialog, (int)
		// (drawScaleW * gameOverDialog.getWidth()), (int) (drawScaleH *
		// gameOverDialog.getHeight()), true);
		//

		// scale fruit size
		// fruitKiwi = Bitmap.createScaledBitmap(fruitKiwi,
		// (int) (drawScaleW * fruitKiwi.getWidth()),
		// (int) (drawScaleH * fruitKiwi.getHeight()), true);

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
				e.printStackTrace();
			}
		}
		soundPool.release();

	}

	public void playSound(int soundId) {

		soundPool.play(soundId, 1.0f, 1.0f, 10, 0, 1f);
	}

	public void checkWallCollision() {

		boolean rightWallHit = collideRightWall(screenWidth, jack.getX());

		if (rightWallHit) {

			jack.setX(screenWidth - (screenWidth / 8)); // test on other devices
		}

		boolean leftWallHit = collideLeftWall(screenWidth, jack.getX());

		if (leftWallHit) {
			jack.setX(1);
		}
	}

	public boolean collideRightWall(int lineX, double heroX) {

		int crossings = 0;

		lineX = screenWidth - (screenWidth / 8);

		boolean cond1 = (lineX <= heroX); // must be within range

		if (cond1) {
			crossings = 1;
		}

		return (crossings != 0); // must be a boolean condition
	}

	public boolean collideLeftWall(int lineX, double heroX) {

		int crossings = 0;
		screenWidth = lineX;
		lineX = 0;
		boolean cond1 = (heroX <= lineX);

		if (cond1) {
			crossings = 1;
		}

		return (crossings != 0); // must be a boolean condition
	}

	public void checkGroundCollision() {
		boolean didNotCatch = collideGround(lineYGround, kiwi.getY());

		if (didNotCatch) {

//			 kiwi.setY(lineYGround); // for testing only stops kiwi at ground

			// restarts kiwi position after hitting ground
			 kiwi.setX((int) (Math.random() * screenWidth - 60));
			 kiwi.setY(0);

			missed = missed + 1;
			showFruit = true;
		}
	}

	public boolean collideGround(int lineY, double kiwiY) {

		int crossings = 0;
		lineYGround = lineY;
		boolean cond1 = (lineY <= kiwiY); // is it in the range?

		if (cond1) {
			crossings = 1;
			// soundPool.play(flame, 1.0f, 1.0f, 10, 0, 1f);
			// playSound(flame);
		}
		// gameOver = true;
		return (crossings != 0); // has to be a boolean condition
	}

	public void collideScore() {
		int kiwiCenterX = kiwi.getX() + (kiwi.getSpriteWidth() / 2);
		int kiwiCenterY = kiwi.getY() + (kiwi.getSpriteHeight() / 2);

		if (kiwiCenterX < jack.getX() + jack.getSpriteWidth()
				- jack.getSpriteWidth() / 6
				&& kiwiCenterX > jack.getX() + jack.getSpriteWidth() / 6)// 100px
																			// for
																			// collide
																			// near
																			// head
			if (kiwiCenterY < jack.getY() + jack.getSpriteHeight()
					&& kiwiCenterY > jack.getY())

			{
				kiwi.setY(0);
				score = score + 1;
				kiwi.setX((int) (Math.random() * (screenWidth - 60)));
				// soundPool.play(scoreSound, 1.0f, 1.0f, 10, 0, 1f);
				// playSound(scoreSound);
			}

	}

	// movement and animation of game elements
	public void update() {
		showFruit = false;

		checkWallCollision();

		checkGroundCollision();

		collideScore();

		jack.update(1); // defines speed of movement
		jack.animate(System.currentTimeMillis() / 200); // animation effect
														// higher number slows

		kiwi.moveKiwi();
		kiwi.animate(System.currentTimeMillis() / 200);

	}

	// drawing of game elements on screen
	public void render(Canvas canvas) {

		canvas.drawColor(Color.RED);// behind background bitmap, backbuffer
									// maybe this

		canvas.drawBitmap(background, 0, 0, null); // bitmap background

		// canvas.drawText("Fuel: ", drawScaleW / 10, fuelPaint.getTextSize() +
		// 10, fuelText);
		// canvas.drawRect(fuelBar, fuelPaint);

		// display score
		canvas.drawText("Score: " + Integer.toString(score), screenWidth
				- (int) (20 * drawScaleW), scorePaint.getTextSize() + 10,
				scorePaint);

		// display misses
		canvas.drawText("Misses: " + Integer.toString(missed), screenWidth
				- (int) (200 * drawScaleW), missedPaint.getTextSize() + 10,
				missedPaint); // check location

		kiwi.draw(canvas);
		jack.draw(canvas);
		// drawKiwiFruit(canvas);
	}

	// displays kiwi fruit when bird is not catched
	public void drawKiwiFruit(Canvas canvas) {

		// render fruit if there is a miss
		if (missed != 0) {
			// canvas.drawBitmap(fruitKiwi, kiwi.getX(), kiwi.getY(), null);
		} else {

		}

		// if (!showFruit) {
		// kiwi.draw(canvas);
		// } else {
		// // canvas.drawBitmap(gameOverDialog, ((screenWidth / 2) -
		// // (gameOverDialog.getWidth()) / 2), screenHeight / 2, null);
		//
		// // position of fruit depending on bird ground location
		// canvas.drawBitmap(fruitKiwi, kiwi.getX(), kiwi.getY(), null);
		// }

	}

	// sprite sheet direction for non button pressed and jack not moving
	public void resetFrame() {
		if (jack.jackDirection == 2) {
			jack.setMoveX(0);
			jack.setDirection(0); // sprite sheet first row
		}
		if (jack.jackDirection == 3) {
			jack.setMoveX(0);
			jack.setDirection(1); // sprite sheet second row
		}
	}

	public void moveRight() {

		jack.setMoveX(10);
		jack.setDirection(2); // sprite sheet third row
	}

	public void moveLeft() {

		jack.setMoveX(-10);
		jack.setDirection(3); // sprite sheet fourth row
	}

	// public void reset() {
	//
	// gameOver = false;
	// score = 0;
	// jack.setDirection(0);
	//
	// }

}
