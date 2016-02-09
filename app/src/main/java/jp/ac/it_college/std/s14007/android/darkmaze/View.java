package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class View extends SurfaceView implements SurfaceHolder.Callback {

    private Map map;

    private DrawThread drawThread;
    private Bitmap playerBitmap;
    private Bitmap enemyBitmap;
    private FlickTouchListener flickTouchListener = new FlickTouchListener();
    private Player player;
    private Enemy enemy;
    private static final float BALL_SCALE = 0.8f;
    private Callback callback;
    private int dungeonLevel;

    public View(Context context, int dungeonLevel) {
        super(context);
        this.dungeonLevel = dungeonLevel;
        getHolder().addCallback(this);
        this.setOnTouchListener(flickTouchListener);

        playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
    }

    public void startDrawThread() {
        stopDrawThread();

        drawThread = new DrawThread();
        drawThread.start();
        callback.timer();

    }

    public boolean stopDrawThread() {
        if (drawThread == null) {
            return false;
        }

        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    public void drawMaze(Canvas canvas) {
        int blockSize = 0;
        switch (dungeonLevel) {
            case 0:
                blockSize = 150;
                break;
            case 1:
                blockSize = 50;
                break;
            case 2:
                blockSize = playerBitmap.getHeight();
                break;
        }

        player = flickTouchListener.player;
        enemy = flickTouchListener.enemy;

        if (map == null) {
            map = new Map(canvas.getWidth(), canvas.getHeight(), blockSize, callback);

        }

        if (player == null) {
            player = new Player(playerBitmap, map.getStartBlock(), BALL_SCALE);
            player.setOnMoveListener(map);
        }

        if (enemy == null) {
            enemy = new Enemy(enemyBitmap, map.getStartBlock(), BALL_SCALE);
            enemy.setOnMoveListener(map);
        }

        flickTouchListener.player = player;
        flickTouchListener.enemy = enemy;
        canvas.drawColor(Color.BLACK);
        map.drawMap(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        Paint paintGoal = new Paint();
        paintGoal.setColor(Color.YELLOW);
        canvas.drawRect(map.getStartBlock().rect, paint);
        canvas.drawRect(map.getGoalBlock().rect, paintGoal);
        player.draw(canvas);
        enemy.draw(canvas);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }

    private class DrawThread extends Thread {
        private boolean isFinished;

        @Override
        public void run() {

            while (!isFinished) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    drawMaze(canvas);
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    interface Callback {
        void timer();
        void onGoal();
    }
}
