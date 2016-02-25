package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Result extends AppCompatActivity {

    private int exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        long time = getIntent().getLongExtra("time", 0);
        long minute = getIntent().getLongExtra("minute", 0);
        int hour = getIntent().getIntExtra("hour", 0);
        int dungeonLevel = getIntent().getIntExtra("dungeonLevel", 0);

        TextView clearTime = (TextView)findViewById(R.id.time);
        clearTime.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(minute - time)) + "s");



        if (minute <= 2) {
            TextView rank = (TextView)findViewById(R.id.rank);
            rank.setText("SSS");

            switch (dungeonLevel) {
                case 0:
                    exp = 50;
                    break;
                case 1:
                    exp = 500;
                    break;
                case 2:
                    exp = 5000;
                    break;
            }

            TextView expPoint = (TextView)findViewById(R.id.exp_point);
            expPoint.setText(String.valueOf(exp));
            SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            int getExp = prefs.getInt("exp", 0);
            exp += getExp;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("exp", exp);
            editor.apply();
        } else {
            TextView rank = (TextView)findViewById(R.id.rank);
            rank.setText("ザッコ!!!");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
        Log.e("Result :", "call Home");
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
