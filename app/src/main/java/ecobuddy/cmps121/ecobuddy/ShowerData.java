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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ShowerData extends AppCompatActivity {

    BarGraphSeries<DataPoint> series;
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
        series = new BarGraphSeries<>();


        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(!isValueX){
                    return super.formatLabel(value, isValueX)+"sec.";
                }

                    return super.formatLabel(value, isValueX);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        final String uid = FirebaseAuth.getInstance().getUid();
        user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_times = dataSnapshot.child(uid).child("totaltimes").getValue(Integer.class);
                int show_times = total_times - 7;
                int x = 0;
                int y = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.child(uid).child("times").getChildren()) {
                    x = Integer.parseInt(myDataSnapshot.getKey());
                    y = Integer.valueOf(String.valueOf(myDataSnapshot.getValue())) / 1000;

                    if(total_times <= 7){
                        series.appendData(new DataPoint(x, y), true, total_times);
                    }
                    else if (x >= show_times) {
                        series.appendData(new DataPoint(x, y), true, total_times);
                    }
                }
                graph.addSeries(series);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        user_db.child("times").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int x = 0;
                int y = 0;
                for(DataSnapshot myDataSnapshot: dataSnapshot.getChildren()){
                    x = Integer.parseInt(myDataSnapshot.getKey());
                    y = Integer.valueOf(String.valueOf(myDataSnapshot.getValue()));
                    series.appendData(new DataPoint(x,y),true,total_times);
                }
                graph.addSeries(series);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
