package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by s14007 on 16/02/03.
 */
public class View extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private static final Paint PAINT = new Paint();
    private Bitmap player;

    public View(Context context) {
        super(context);
        getHolder().addCallback(this);

        player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
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
        canvas.drawBitmap(player, 50, 50, PAINT);
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
}
