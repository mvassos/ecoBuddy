package ecobuddy.cmps121.ecobuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillsData extends AppCompatActivity {

    BarGraphSeries<DataPoint> series;
    GraphView graph;

    private FirebaseAuth mAuth;
    private DatabaseReference user_db;

    private final String TAG = "billsData";

    SimpleDateFormat sdf = new SimpleDateFormat("MMM YY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_data);

        mAuth = FirebaseAuth.getInstance();
        user_db = FirebaseDatabase.getInstance().getReference("Users");

        graph = findViewById(R.id.GraphView_bills);
        series = new BarGraphSeries<>();

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(isValueX){
                    return sdf.format(new Date((long) value));
                }
                else {
                    if(value%10 == 0)
                    return "$"+super.formatLabel(value, isValueX);
                }
                return"";
            }
        });

        //graph.getViewport().setScalable(true);
        //graph.getViewport().setScrollable(true);

        //graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        graph.getGridLabelRenderer().setHumanRounding(true);





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        series = new BarGraphSeries<DataPoint>();
        graph.removeAllSeries();
       // Toast.makeText(this, "back pressed",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    protected void onStart() {
        super.onStart();

        final String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "onStart: uid = "+uid);


        user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String monthVal = "";
                Date xD = new Date();
                int x = 0;
                double y = 0;
                Log.d(TAG, "onDataChange: x = "+x+", y = "+y);

                for(DataSnapshot myDataSnapshot: dataSnapshot.child(uid).child("Bills").getChildren()){
                    //make x be 1-12 via more switch statements
                    monthVal = String.valueOf(myDataSnapshot.getKey());
                    Log.d(TAG, "onDataChange: monthVal = "+monthVal);

                    switch (monthVal){
                        case "1": xD = new Date(2018,0,1);
                            break;
                        case "2": xD = new Date(2018,1,1);
                            break;
                        case "3": xD = new Date(2018,2,1);
                            break;
                        case "4": xD = new Date(2018,3,1);
                            break;
                        case "5": xD = new Date(2018,4,1);
                            break;
                        case "6": xD = new Date(2018,5,1);
                            break;
                        case "7": xD = new Date(2018,6,1);
                            break;
                        case "8": xD = new Date(2018,7,1);
                            break;
                        case "9": xD = new Date(2018,8,1);
                            break;
                        case "10": xD = new Date(2018,9,1);
                            break;
                        case "11": xD = new Date(2018,10,1);
                            break;
                        case "12": xD = new Date(2018,11,1);
                            break;
                    }

                    y = Double.parseDouble(String.valueOf(myDataSnapshot.getValue()));

                    Log.d(TAG, "onDataChange: x = "+xD.toString()+", y = "+y);

                    series.appendData(new DataPoint(xD,y),true,12);
                }

                graph.addSeries(series);


                graph.getGridLabelRenderer().setHumanRounding(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
