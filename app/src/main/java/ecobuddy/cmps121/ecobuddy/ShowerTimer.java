package ecobuddy.cmps121.ecobuddy;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ShowerTimer extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    Button start_b, stop_b, log_b;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shower_timer_activity);

        mAuth = FirebaseAuth.getInstance();

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
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

            long time = (SystemClock.elapsedRealtime() - chronometer.getBase());

            Map newPost = new HashMap();

            newPost.put("time", time);

            user_db.setValue(newPost);

            Toast.makeText(this,"Time Recorded Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
