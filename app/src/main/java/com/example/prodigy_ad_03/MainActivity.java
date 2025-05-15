package com.example.prodigy_ad_03;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStart, btnPause, btnReset;

    private Handler handler = new Handler();
    private long startTime = 0L;
    private long timeInMillis = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMillis;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000) / 10;

            String time = String.format("%02d:%02d:%02d", mins, secs, milliseconds);
            tvTimer.setText(time);
            handler.postDelayed(this, 50);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener(v -> {
            startTime = System.currentTimeMillis();
            handler.postDelayed(runnable, 0);
        });

        btnPause.setOnClickListener(v -> {
            timeSwapBuff += timeInMillis;
            handler.removeCallbacks(runnable);
        });

        btnReset.setOnClickListener(v -> {
            startTime = 0L;
            timeInMillis = 0L;
            timeSwapBuff = 0L;
            updatedTime = 0L;
            handler.removeCallbacks(runnable);
            tvTimer.setText("00:00:00");
        });
    }
}
