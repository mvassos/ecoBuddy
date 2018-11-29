package ecobuddy.cmps121.ecobuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EnterBills extends AppCompatActivity {

    String billAmount;
    String month;

    String[] months;
    ArrayAdapter <String> adapter;

    EditText inputAmount;
    Spinner inputMonth;
    Button enterInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bills);

        final String TAG = "billInput";

        inputAmount = (EditText) findViewById(R.id.EditText_bill);
        inputMonth = (Spinner) findViewById(R.id.spinner_months);
        enterInfo = (Button) findViewById(R.id.button_trackBill);

        months = new String[]{"Jan", "Feb", "Mar", "Arp", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputMonth.setAdapter(adapter);

        enterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAmount = inputAmount.getText().toString();
                month = inputMonth.getSelectedItem().toString();

                Log.d(TAG, "onClick: month = "+month+", amount = "+billAmount);


            }
        });
    }
}
