package ecobuddy.cmps121.ecobuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;

public class SetBill extends AppCompatActivity {
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bill);
        @SuppressLint({"NewApi", "LocalSuppress"}) Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra("rrule", "FREQ=MONTHLY/");
        intent.putExtra("title", "Enter bill name here");
        intent.putExtra("allDay", true);
        startActivity(intent);
        finish();
    }
}
