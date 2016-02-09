package jp.ac.it_college.std.s14007.android.darkmaze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity implements jp.ac.it_college.std.s14007.android.darkmaze.View.Callback {
    private jp.ac.it_college.std.s14007.android.darkmaze.View view;
    public int dungeonLevel;

    private Timer mainTimer;					//タイマー用
    private MainTimerTask mainTimerTask;
    private TextView countText;					//テキストビュー
    private int count = 0;						//カウント
    private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ProgressBar bar = (ProgressBar)findViewById(R.id.progressBar1);
        bar.setMax(100);
        bar.setProgress(80);

        Button button_hard = (Button)findViewById(R.id.button_hard);
        button_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dungeonLevel = 2;
                view = new jp.ac.it_college.std.s14007.android.darkmaze.View(Home.this, dungeonLevel);
                view.setCallback(Home.this);
                setContentView(view);
            }
        });

        Button button_normal = (Button)findViewById(R.id.button_normal);
        button_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dungeonLevel = 1;
                view = new jp.ac.it_college.std.s14007.android.darkmaze.View(Home.this, dungeonLevel);
                view.setCallback(Home.this);
                setContentView(view);
            }
        });

        Button button_easy = (Button)findViewById(R.id.button_easy);
        button_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dungeonLevel = 0;
                view = new jp.ac.it_college.std.s14007.android.darkmaze.View(Home.this, dungeonLevel);
                view.setCallback(Home.this);
                setContentView(view);
            }
        });
    }

    @Override
    public void timer() {
        mainTimer = new Timer();
        mainTimerTask = new MainTimerTask();
        //タイマースケジュール設定＆開始
        mainTimer.schedule(mainTimerTask, 1000, 1000);
        //テキストビュー
//        countText = (TextView)findViewById(R.id.clear_time);
    }

    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post( new Runnable() {
                public void run() {

                    //実行間隔分を加算処理
                    count += 1;
                    //画面にカウントを表示
//                    countText.setText(String.valueOf(count));
                }
            });
        }
    }


    @Override
    public void onGoal() {
        Toast.makeText(this, "Goal!!", Toast.LENGTH_SHORT).show();
        mainTimer.cancel();
        view.stopDrawThread();
        Intent intent = new Intent(this, Result.class);
        intent.putExtra("time", count);
        startActivity(intent);
        finish();
    }
}
