package ecobuddy.cmps121.ecobuddy;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EnterBills extends AppCompatActivity {

    String billAmount;
    String month;

    String[] months;
    ArrayAdapter <String> adapter;

    EditText inputAmount;
    Spinner inputMonth;
    Button enterInfo;
    HashMap<String, String> choices;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bills);

        final String TAG = "billInput";

        mAuth = FirebaseAuth.getInstance();

        inputAmount = (EditText) findViewById(R.id.EditText_bill);
        inputMonth = (Spinner) findViewById(R.id.spinner_months);
        enterInfo = (Button) findViewById(R.id.button_trackBill);

        months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        choices = new HashMap<>();

        for (int i = 1; i < 13; i++) {
            choices.put(months[i - 1], Integer.toString(i));
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputMonth.setAdapter(adapter);

        enterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAmount = inputAmount.getText().toString();

                if(billAmount.length()==0){
                    toastMessage("Enter an amount", 0);
                }
                else {
                    month = choices.get(inputMonth.getSelectedItem().toString());
                    logData(v, month, billAmount);
                }
            }
        });
    }

    private void logData(View v, String month, String amount) {

            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Bills");
            Map newPost = new HashMap();
            newPost.put(month, amount);
            user_db.updateChildren(newPost);

            toastMessage("Bill Recorded Successfully", 0);

            finish();

    }

    private void toastMessage(String msg, int len) {
        Toast toast = Toast.makeText(this, msg, len);
        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_IN);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.Teal));
        toast.show();
    }
}
