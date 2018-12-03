package ecobuddy.cmps121.ecobuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ShowerData extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    GraphView graph;

    private int total_times;

    private FirebaseAuth mAuth;
    private DatabaseReference user_db;

    private final String TAG = "showerData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shower_data);

        mAuth = FirebaseAuth.getInstance();
        user_db = FirebaseDatabase.getInstance().getReference("Users");

        graph = findViewById(R.id.GraphView_shower);
        series = new LineGraphSeries<>();


    }

    @Override
    protected void onStart() {
        super.onStart();

        final String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "onStart: uid = "+uid);


        user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_times = dataSnapshot.child(uid).child("totaltimes").getValue(Integer.class);
                Log.d(TAG, "onDataChange: total times = "+total_times);

                int x = 0;
                int y = 0;

                Log.d(TAG, "onDataChange: x = "+x+", y = "+y);

                for(DataSnapshot myDataSnapshot : dataSnapshot.child(uid).child("times").getChildren()){
                    x = Integer.parseInt(myDataSnapshot.getKey())+1;
                    y = Integer.valueOf(String.valueOf(myDataSnapshot.getValue()))/1000;

                    Log.d(TAG, "onDataChange: x = "+x+", y = "+y);

                    series.appendData(new DataPoint(x,y),true,total_times);
                }

                graph.addSeries(series);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        user_db.child("times").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int x = 0;
                int y = 0;

                for(DataSnapshot myDataSnapshot: dataSnapshot.getChildren()){
                    x = Integer.parseInt(myDataSnapshot.getKey());
                    y = Integer.valueOf(String.valueOf(myDataSnapshot.getValue()));

                    Log.d(TAG, "onDataChange: x = "+x+", y = "+y);

                    series.appendData(new DataPoint(x,y),true,total_times);
                }

                graph.addSeries(series);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
