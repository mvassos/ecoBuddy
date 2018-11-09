package ecobuddy.cmps121.ecobuddy;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class ShowerTimer extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    Button start_b, stop_b, log_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shower_timer_activity);
        chronometer = findViewById(R.id.timer_shower);
        start_b = findViewById(R.id.button_start_timer);
        start_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometer(v);
            }
        });

        stop_b = findViewById(R.id.button_stop_timer);
        stop_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseChronometer(v);
            }
        });

        log_b = findViewById(R.id.logdata_button);
        log_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logData(v);
            }
        });
    }

    public void startChronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;

        }
    }

    public void pauseChronometer(View v){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

        }
    }

    public void resetChronometer(View v){
        pauseChronometer(v);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

    }

    private void logData(View v) {
        if((SystemClock.elapsedRealtime() - chronometer.getBase()) <= 37000){
            Toast.makeText(this,"Shower Must Be A Realistic Time", Toast.LENGTH_LONG).show();
        }
        else{
            //do backend work and show the user a log successfull messgae
            Toast.makeText(this,"Time Recorded Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
