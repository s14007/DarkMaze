package jp.ac.it_college.std.s14007.android.darkmaze;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button_hard = (Button)findViewById(R.id.button_hard);
        button_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jp.ac.it_college.std.s14007.android.darkmaze.View view =
                        new jp.ac.it_college.std.s14007.android.darkmaze.View(Home.this);
                setContentView(view);
            }
        });

    }

}
