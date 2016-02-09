package jp.ac.it_college.std.s14007.android.darkmaze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int time = getIntent().getIntExtra("time", 0);
        TextView clearTime = (TextView)findViewById(R.id.time);
        clearTime.setText(String.valueOf(time));

        if (time <= 2) {
            TextView rank = (TextView)findViewById(R.id.rank);
            rank.setText("SSS");
        } else {
            TextView rank = (TextView)findViewById(R.id.rank);
            rank.setText("ザッコ!!!");
        }
    }
}
