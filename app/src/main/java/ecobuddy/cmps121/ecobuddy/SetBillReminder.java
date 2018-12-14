package ecobuddy.cmps121.ecobuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SetBillReminder extends AppCompatActivity {
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bill);
        // We need to get an instance of Calendar object
        @SuppressLint({"NewApi", "LocalSuppress"}) Calendar cal = Calendar.getInstance();
        if (cal != null) {
            // Create intent to edit cal
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            // Give the calendar some defaults
            intent.putExtra("rrule", "FREQ=MONTHLY/");
            intent.putExtra("title", "Enter bill name here");
            intent.putExtra("allDay", true);
            startActivity(intent);
        } else {
            toastMessage("No calendar found...", 0);
        }
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
