package ecobuddy.cmps121.ecobuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

public class ShowerTimer extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shower_timer);

        chronometer = findViewById(R.id.timer_shower);

    }

    public void startChronometer(View v){
        if(!running){
            chronometer.start();
            running = true;

        }
    }

    public void stopChronometer(View v){
        if(running){
            chronometer.stop();
            running = false;

        }

    }
}
