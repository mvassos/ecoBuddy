package ecobuddy.cmps121.ecobuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataAnalytics extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference user_db;
    private long miliSec;
    private String TAG = "dataAnalytics";
    TextView time;
    Button showerButton;
    Button billsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analytics);

        mAuth = FirebaseAuth.getInstance();
        user_db = FirebaseDatabase.getInstance().getReference("Users");

        showerButton = (Button) findViewById(R.id.button_view_showerData);
        showerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showerData_nav();
            }
        });

        billsButton = (Button) findViewById(R.id.button_view_billData);
        billsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billData_nav();
            }
        });

    }

    public void showerData_nav() {
        Intent intent = new Intent(this, ShowerData.class);
        startActivity(intent);
    }

    public void billData_nav(){
        Intent intent = new Intent(this, BillsData.class);
        startActivity(intent);
    }
}
