package ecobuddy.cmps121.ecobuddy;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ShowerTimer extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    Button start_b, stop_b, log_b;

    private int total_times;

    private FirebaseAuth mAuth;
    private DatabaseReference user_db;

    private final String TAG = "showerTimer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shower_timer_activity);

        mAuth = FirebaseAuth.getInstance();
        user_db = FirebaseDatabase.getInstance().getReference("Users");

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

    @Override
    protected void onStart() {
        super.onStart();

        final String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "onStart: uid = "+uid);


        user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(uid).child("totaltimes") != null) {
                    total_times = dataSnapshot.child(uid).child("totaltimes").getValue(Integer.class);
                } else {
                    total_times = 0;
                }
                Log.d(TAG, "onDataChange: total times = "+total_times);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
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

        if((SystemClock.elapsedRealtime() - chronometer.getBase()) <= 10000){
            Toast.makeText(this,"Shower Must Be A Realistic Time", Toast.LENGTH_LONG).show();
        }
        else{
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

            long time = (SystemClock.elapsedRealtime() - chronometer.getBase());

            Map newPost = new HashMap();

            newPost.put(""+total_times, time);
            user_db.child("times").updateChildren(newPost);

            total_times++;
            Map newTimes = new HashMap();

            newTimes.put("totaltimes",total_times);
            user_db.updateChildren(newTimes);

            resetChronometer(v);

            Toast.makeText(this,"Time Recorded Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
