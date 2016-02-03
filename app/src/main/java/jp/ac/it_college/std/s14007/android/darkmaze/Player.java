package jp.ac.it_college.std.s14007.android.darkmaze;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by s14007 on 16/02/03.
 */
public class Player {
    private Bitmap player;
    private final Rect rect;
    private static final Paint PAINT = new Paint();

    public interface OnMoveListener {
        public boolean canMove(int left, int top, int right, int bottom);
    }

    private OnMoveListener listener;

    public void setOnMoverListener(OnMoveListener moverListener) {
        listener = moverListener;
    }

    public Player(Bitmap bitmap, int left, int top) {
        player = bitmap;

        int right = left + bitmap.getWidth();
        int bottom = top + bitmap.getHeight();
        rect = new Rect(left, top, right, bottom);
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(player, rect.left, rect.top, PAINT);
    }

    void move(float xOffset, float yOffset) {
        int align = yOffset >= 0 ? 1 : -1;
        while (!tryMoveVertical(yOffset)) {
            yOffset -= align;
        }

        align = xOffset >= 0 ? 1 : -1;
        while (!tryMoveHorizontal(xOffset)) {
            xOffset -= align;
        }
    }

    private boolean tryMoveHorizontal(float xOffset) {
        int left = rect.left + Math.round(xOffset);
        int right = left + rect.width();

        if (!listener.canMove(rect.left, rect.top, rect.right, rect.bottom)) {
            return false;
        }

        rect.left = left;
        rect.right = right;
        return true;
    }

    private boolean tryMoveVertical(float yOffset) {
        int top = rect.top + Math.round(yOffset);
        int bottom = rect.bottom + rect.width();

        if (!listener.canMove(rect.left, top, rect.right, bottom)) {
            return false;
        }

        rect.top = top;
        rect.bottom = bottom;
        return true;
    }
}
