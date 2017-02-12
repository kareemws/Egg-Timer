package com.example.kareemwaleed.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private MediaPlayer airHorn;
    boolean status;
    CountDownTimer countDownTimer;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepare();
    }

    public void buttonClicked(View view) {
        if (status) {
            status = false;
            button.setText("STOP !");
            timerSeekBar.setVisibility(View.INVISIBLE);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000, 1000) {
                public void onTick(long millisecondsUntilFinish) {
                    timerTextView.setText(convert(millisecondsUntilFinish));
                }

                public void onFinish() {
                    status = true;
                    button.setText("GO !");
                    airHorn.start();
                    reset();
                    timerSeekBar.setVisibility(View.VISIBLE);
                }
            }.start();
        } else {
            status = true;
            button.setText("GO !");
            countDownTimer.cancel();
            reset();
            timerSeekBar.setVisibility(View.VISIBLE);
        }
    }

    private void prepare() {
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timerTextView.setText(convert(progress*1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText(convert(30000));
        status = true;
        airHorn = MediaPlayer.create(this, R.raw.airhorn);
        button = (Button) findViewById(R.id.button);
    }

    private void reset() {
        timerSeekBar.setProgress(30);
        timerTextView.setText(convert(30000));
    }

    private String convert(long milliseconds)
    {
        long seconds = milliseconds/1000;
        long minutes = seconds/60;
        seconds = seconds % 60;
        String stringMinutes = String.valueOf(minutes);
        String stringSeconds = String.valueOf(seconds);
        if(stringMinutes.length() == 1)
        {
            String temp = "0";
            temp += stringMinutes;
            stringMinutes = temp;
        }
        if(stringSeconds.length() == 1)
        {
            String temp = "0";
            temp += stringSeconds;
            stringSeconds = temp;
        }
        return stringMinutes + ":" + stringSeconds;
    }
}
