package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity implements jp.ac.it_college.std.s14007.android.darkmaze.View.Callback {
    private jp.ac.it_college.std.s14007.android.darkmaze.View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button_hard = (Button)findViewById(R.id.button_hard);
        button_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = new jp.ac.it_college.std.s14007.android.darkmaze.View(Home.this);
                view.setCallback(Home.this);
                setContentView(view);
            }
        });

    }

    @Override
    public void onGoal() {
        Toast.makeText(this, "Goal!!", Toast.LENGTH_SHORT).show();
        view.stopDrawThread();
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
        finish();
    }
}
