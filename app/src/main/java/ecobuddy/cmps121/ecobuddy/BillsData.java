package ecobuddy.cmps121.ecobuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillsData extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    GraphView graph;

    private FirebaseAuth mAuth;
    private DatabaseReference user_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_data);

        mAuth = FirebaseAuth.getInstance();
        user_db = FirebaseDatabase.getInstance().getReference("Users");

        graph = findViewById(R.id.GraphView_bills);
        series = new LineGraphSeries<>();

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(!isValueX && (value%1 ==0)){
                    return "$"+super.formatLabel(value, isValueX);
                }
                if(isValueX){
                    return super.formatLabel(value, isValueX);
                }
                return "";
            }
        });

        graph.getGridLabelRenderer().setNumHorizontalLabels(12);
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(12);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(50);
        graph.getViewport().setMaxY(300);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        series = new LineGraphSeries<DataPoint>();
        graph.removeAllSeries();
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();

        final String uid = FirebaseAuth.getInstance().getUid();
        user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String monthVal = "";
                Date xD = new Date();
                int x = 0;
                double y = 0;

                for(DataSnapshot myDataSnapshot: dataSnapshot.child(uid).child("Bills").getChildren()){
                    monthVal = String.valueOf(myDataSnapshot.getKey());
                    x = Integer.parseInt(monthVal);
                    y = Double.parseDouble(String.valueOf(myDataSnapshot.getValue()));
                    series.appendData(new DataPoint(x,y),true,12);
                }
                graph.addSeries(series);
                graph.getGridLabelRenderer().setHumanRounding(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}