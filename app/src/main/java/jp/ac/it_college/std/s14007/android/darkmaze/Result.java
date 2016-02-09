package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private int exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int time = getIntent().getIntExtra("time", 0);
        int minute = getIntent().getIntExtra("minute", 0);

        TextView clearTime = (TextView)findViewById(R.id.time);
        clearTime.setText(String.valueOf(minute + ":" + time));

        if (minute <= 2) {
            TextView rank = (TextView)findViewById(R.id.rank);
            rank.setText("SSS");
            exp = 0;


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
        finish();
        return true;
    }
}
