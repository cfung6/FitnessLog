package com.example.fitnesslog.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesslog.R;


public class Stopwatch extends AppCompatActivity {
    Button startButton, stopButton, lapButton, resetButton;
    TextView timerValue;
    Handler customerHandler = new Handler();
    LinearLayout container;

    long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updateTime = 0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            timerValue.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customerHandler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        lapButton = findViewById(R.id.lap_button);
        resetButton = findViewById(R.id.reset_button);

        timerValue = findViewById(R.id.timerValue);
        container = findViewById(R.id.container);

        final Thread thread = new Thread(updateTimerThread);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();

                customerHandler.postDelayed(updateTimerThread, 0);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customerHandler.removeCallbacks(updateTimerThread);
            }
        });

        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row, null);
                TextView txtValue = addView.findViewById(R.id.txtContent);
                txtValue.setText(timerValue.getText());
                container.addView(addView);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread.interrupt();
                startTime = 0L;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updateTime = 0L;
                customerHandler.postDelayed(updateTimerThread, 0);
                customerHandler.removeCallbacks(updateTimerThread);
                timerValue.setText("0:00:000");
                container.removeAllViews();
            }
        });
    }

    //Stopwatch runs in background
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
