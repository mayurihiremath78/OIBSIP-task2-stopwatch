package com.example.oasis_stop_watch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonReset;

    private Handler handler;
    private Runnable runnable;

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.text_view_timer);
        buttonStart = findViewById(R.id.button_start);
        buttonPause = findViewById(R.id.button_pause);
        buttonReset = findViewById(R.id.button_reset);

        handler = new Handler();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                buttonReset.setEnabled(false);
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                handler.removeCallbacks(runnable);
                buttonReset.setEnabled(true);
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff = 0L;
                startTime = 0L;
                updatedTime = 0L;
                textViewTimer.setText("00:00:00");
                buttonReset.setEnabled(false);
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                int seconds = (int) (updatedTime / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                int milliseconds = (int) (updatedTime % 1000);
                textViewTimer.setText("" + String.format("%02d", minutes) + ":"
                        + String.format("%02d", seconds) + ":"
                        + String.format("%03d", milliseconds));
                handler.postDelayed(this, 0);
            }
        };
    }
}

