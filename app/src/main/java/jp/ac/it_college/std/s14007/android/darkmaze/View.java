package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by s14007 on 16/02/03.
 */
public class View extends SurfaceView implements SurfaceHolder.Callback {

    private Map map;

    private static final Paint PAINT = new Paint();
    private DrawThread drawThread;
    private Bitmap player;
    private FlickTouchListener flickTouchListener = new FlickTouchListener();

    public View(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.setOnTouchListener(flickTouchListener);

        player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
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
        int playerX = flickTouchListener.playerX;
        int playerY = flickTouchListener.playerY;
        int blockSize = player.getHeight();

        if (map == null) {
            map = new Map(canvas.getWidth(), canvas.getHeight(), blockSize);
        }

        canvas.drawColor(Color.WHITE);
        map.drawMap(canvas);
        canvas.drawBitmap(player, playerX, playerY, PAINT);
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
}
