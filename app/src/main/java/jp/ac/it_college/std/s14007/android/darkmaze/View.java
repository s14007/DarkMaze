package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class View extends SurfaceView implements SurfaceHolder.Callback {

    private Map map;

    private DrawThread drawThread;
    private Bitmap playerBitmap;
    private FlickTouchListener flickTouchListener = new FlickTouchListener();
    private Player player;
    private static final float BALL_SCALE = 0.8f;
    private Callback callback;

    public View(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.setOnTouchListener(flickTouchListener);

        playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
    }

    public void startDrawThread() {
        stopDrawThread();

        drawThread = new DrawThread();
        drawThread.start();
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
        int blockSize = 5;
        player = flickTouchListener.player;

        /*if (player != null) {
            player.move(playerX, playerY);
        }*/

        if (map == null) {
            map = new Map(canvas.getWidth(), canvas.getHeight(), blockSize, callback);

        }

        if (player == null) {
            player = new Player(playerBitmap, map.getStartBlock(), BALL_SCALE);
            player.setOnMoveListener(map);
        }

        flickTouchListener.player = player;
        canvas.drawColor(Color.BLACK);
        map.drawMap(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        Paint paintGoal = new Paint();
        paintGoal.setColor(Color.RED);
        canvas.drawRect(map.getStartBlock().rect, paint);
        canvas.drawRect(map.getGoalBlock().rect, paintGoal);
        player.draw(canvas);
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
        public void onGoal();
    }
}
