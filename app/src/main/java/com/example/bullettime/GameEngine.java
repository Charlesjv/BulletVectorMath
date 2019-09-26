package com.example.bullettime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameEngine extends SurfaceView implements Runnable {
    private final String TAG = "VECTOR-MATH";

    // game thread variables
    private Thread gameThread = null;
    private volatile boolean gameIsRunning;

    // drawing variables
    private Canvas canvas;
    private Paint paintbrush;
    private SurfaceHolder holder;

    // Screen resolution varaibles
    private int screenWidth;
    private int screenHeight;

    // SPRITES
    Square bullet;

    // Array of bullets

    ArrayList<Square> bullets = new ArrayList<Square>();


    Square enemy;

    int SQUARE_WIDTH = 30;

    public GameEngine(Context context, int screenW, int screenH) {
        super(context);

        // intialize the drawing variables
        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        // set screen height and width
        this.screenWidth = screenW;
        this.screenHeight = screenH;

        // initalize sprites

        int bulletStartX = 100;

        int prevPos = 100;

        for (int i = 0; i<7;i++){

            int bulletX = (100+ 120*i);
          Square bullet = new Square(context, bulletX,300,SQUARE_WIDTH);

            this.bullets.add(bullet);



        }

        this.bullet = new Square(context, 100, 300, SQUARE_WIDTH);



        this.enemy = new Square(context, 100, 100, SQUARE_WIDTH);

    }

    @Override
    public void run() {
        // @TODO: Put game loop in here
        while (gameIsRunning == true) {
            updateGame();
            redrawSprites();
            controlFPS();
        }
    }
    int count = 0;

    // Game Loop methods
    public void updateGame() {

        count = count +1;
        if(count % 20 == 0){
//            this.spawnBullet();
        }

        for(int i = 0; i< this.bullets.size();i++ ){
            Square b = this.bullets.get(i);

          int yPos = b.getyPosition();
          b.setyPosition(yPos - 10);

        }

        Log.d(TAG,"Bullet position: " + this.bullet.getxPosition() + ", " + this.bullet.getyPosition());
        Log.d(TAG,"Enemy position: " + this.enemy.getxPosition() + ", " + this.enemy.getyPosition());



        // 1. calculate distance between bullet and enemy
        double a = this.enemy.getxPosition() - this.bullet.getxPosition();
        double b = this.enemy.getyPosition() - this.bullet.getyPosition();

        // d = sqrt(a^2 + b^2)

        double d = Math.sqrt((a * a) + (b * b));

        Log.d(TAG, "Distance to enemy: " + d);

        // 2. calculate xn and yn constants
        // (amount of x to move, amount of y to move)
        double xn = (a / d);
        double yn = (b / d);

        // 3. calculate new (x,y) coordinates
        int newX = this.bullet.getxPosition() + (int) (xn * 15);
        int newY = this.bullet.getyPosition() + (int) (yn * 15);
        this.bullet.setxPosition(newX);
        this.bullet.setyPosition(newY);

        Log.d(TAG,"----------");
    }

    public void redrawSprites() {
        if (holder.getSurface().isValid()) {

            // initialize the canvas
            canvas = holder.lockCanvas();
            // --------------------------------
            // @TODO: put your drawing code in this section

            // set the game's background color
            canvas.drawColor(Color.argb(255,255,255,255));

            // setup stroke style and width
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(8);

            // draw bullet
            paintbrush.setColor(Color.BLACK);
            canvas.drawRect(
                    this.bullet.getxPosition(),
                    this.bullet.getyPosition(),
                    this.bullet.getxPosition() + this.bullet.getWidth(),
                    this.bullet.getyPosition() + this.bullet.getWidth(),
                    paintbrush
            );

            paintbrush.setColor(Color.BLACK);

            for(int i = 0; i< this.bullets.size(); i++){

                Square b = this.bullets.get(i);
                canvas.drawRect(b.getxPosition(),b.getyPosition(),b.getxPosition() + b.getWidth(),b.getyPosition()+b.getWidth(),paintbrush);

            }

            // draw enemy
            paintbrush.setColor(Color.MAGENTA);
            canvas.drawRect(
                    this.enemy.getxPosition(),
                    this.enemy.getyPosition(),
                    this.enemy.getxPosition() + this.enemy.getWidth(),
                    this.enemy.getyPosition() + this.enemy.getWidth(),
                    paintbrush
            );


            // --------------------------------
            holder.unlockCanvasAndPost(canvas);
        }

    }

    public void controlFPS() {
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {

        }
    }


    // Deal with user input


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_DOWN:

                break;
        }
        return true;
    }

    // Game status - pause & resume
    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {

        }
    }
    public void  resumeGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}

