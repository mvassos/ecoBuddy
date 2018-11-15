package ecobuddy.cmps121.ecobuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataAnalytics extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference user_db;
    private long miliSec;
    private String TAG = "dataAnalytics";
    TextView time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_analytics_activity);

        time = (TextView) findViewById(R.id.TextView_showerTime);

        mAuth = FirebaseAuth.getInstance();


        user_db = FirebaseDatabase.getInstance().getReference("Users");



    }

    @Override
    protected void onStart() {
        super.onStart();

        final String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "onStart: uid = "+uid);

        user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                miliSec = dataSnapshot.child(uid).child("time").getValue(Long.class);
                Log.d(TAG, "onDataChange: miliSec values = "+miliSec);
                time.setText(String.valueOf(miliSec/1000) + " seconds");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
