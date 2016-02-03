package jp.ac.it_college.std.s14007.android.darkmaze;

import android.util.Log;
import android.view.*;
import android.view.View;

/**
 * Created by s14007 on 16/02/03.
 */
public class FlickTouchListener implements View.OnTouchListener {
    // レイアウトの定数を省略してメンバに保持
//    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    //--------------------------------------------------------------------------
    // フリックされた方向を算出する
    //--------------------------------------------------------------------------

    public Player player;
    public int playerX = 0;
    public int playerY = 0;
    private int ballSize = 50;

    // 最後にタッチされた座標
    private float startTouchX;
    private float startTouchY;

    // 現在タッチ中の座標
    private float nowTouchedX;
    private float nowTouchedY;

    // フリックの遊び部分（最低限移動しないといけない距離）
    private float adjust = 120;

    @Override
    public boolean onTouch(android.view.View v_, MotionEvent event_) {

        if (player != null) {
            player.move(-playerX, playerY);
        }

        // タッチされている指の本数
        Log.v("motionEvent", "--touch_count = " + event_.getPointerCount());

        // タッチされている座標
        Log.v("Y", "" + event_.getY());
        Log.v("X", "" + event_.getX());

        switch (event_.getAction()) {

            // タッチ
            case MotionEvent.ACTION_DOWN:
                Log.v("motionEvent", "--ACTION_DOWN");
                startTouchX = event_.getX();
                startTouchY = event_.getY();
                break;

            // タッチ中に追加でタッチした場合
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.v("motionEvent", "--ACTION_POINTER_DOWN");
                break;

            // スライド
            case MotionEvent.ACTION_MOVE:
                Log.v("motionEvent", "--ACTION_MOVE");
                break;

            // タッチが離れた
            case MotionEvent.ACTION_UP:
                Log.v("motionEvent", "--ACTION_UP");
                nowTouchedX = event_.getX();
                nowTouchedY = event_.getY();

                if (startTouchY > nowTouchedY) {
                    if (startTouchX > nowTouchedX) {
                        if ((startTouchY - nowTouchedY) > (startTouchX - nowTouchedX)) {
                            if (startTouchY > nowTouchedY + adjust) {
                                Log.v("Flick", "--上");
                                // 上フリック時の処理を記述する
                                --playerY;
                            }
                        } else if ((startTouchY - nowTouchedY) < (startTouchX - nowTouchedX)) {
                            if (startTouchX > nowTouchedX + adjust) {
                                Log.v("Flick", "--左");
                                // 左フリック時の処理を記述する
                                ++playerX;
                            }
                        }
                    } else if (startTouchX < nowTouchedX) {
                        if ((startTouchY - nowTouchedY) > (nowTouchedX - startTouchX)) {
                            if (startTouchY > nowTouchedY + adjust) {
                                Log.v("Flick", "--上");
                                // 上フリック時の処理を記述する
                                --playerY;
                            }
                        } else if ((startTouchY - nowTouchedY) < (nowTouchedX - startTouchX)) {
                            if (startTouchX < nowTouchedX + adjust) {
                                Log.v("Flick", "--右");
                                // 右フリック時の処理を記述する
                                --playerX;
                            }
                        }
                    }
                } else if (startTouchY < nowTouchedY) {
                    if (startTouchX > nowTouchedX) {
                        if ((nowTouchedY - startTouchY) > (startTouchX - nowTouchedX)) {
                            if (startTouchY < nowTouchedY + adjust) {
                                Log.v("Flick", "--下");
                                // 下フリック時の処理を記述する
                                ++playerY;
                            }
                        } else if ((nowTouchedY - startTouchY) < (startTouchX - nowTouchedX)) {
                            if (startTouchX > nowTouchedX + adjust) {
                                Log.v("Flick", "--左");
                                // 左フリック時の処理を記述する
                                ++playerX;
                            }
                        }
                    } else if (startTouchX < nowTouchedX) {
                        if ((nowTouchedY - startTouchY) > (nowTouchedX - startTouchX)) {
                            if (startTouchY < nowTouchedY + adjust) {
                                Log.v("Flick", "--下");
                                // 下フリック時の処理を記述する
                                ++playerY;
                            }
                        } else if ((nowTouchedY - startTouchY) < (nowTouchedX - startTouchX)) {
                            if (startTouchX < nowTouchedX + adjust) {
                                Log.v("Flick", "--右");
                                // 右フリック時の処理を記述する
                                --playerX;
                            }
                        }
                    }
                }
                break;

            // アップ後にほかの指がタッチ中の場合
            case MotionEvent.ACTION_POINTER_UP:
                Log.v("motionEvent", "--ACTION_POINTER_UP");
                break;

            // UP+DOWNの同時発生(タッチのキャンセル）
            case MotionEvent.ACTION_CANCEL:
                Log.v("motionEvent", "--ACTION_CANCEL");

                // ターゲットとするUIの範囲外を押下
            case MotionEvent.ACTION_OUTSIDE:
                Log.v("motionEvent", "--ACTION_OUTSIDE");
                break;
        }

        return (true);
    }

}
