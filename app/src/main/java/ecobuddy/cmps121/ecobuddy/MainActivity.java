package ecobuddy.cmps121.ecobuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button eco_rem_btn, shower_timer_btn, data_btn, signout_btn, bill_btn;
    TextView username;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        0);
            }
        } else {
            // Permission has already been granted
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CALENDAR)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        0);
            }
        }



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // initialize google sign in stuff to log out later
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        username = (TextView) findViewById(R.id.TextView_username);
        eco_rem_btn = (Button) findViewById(R.id.ecoreminders_button);

        eco_rem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ecoReminders_nav();
            }
        });

        shower_timer_btn = (Button) findViewById(R.id.showertimer_button);
        shower_timer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showerTimer_nav();

            }
        });

        data_btn = (Button) findViewById(R.id.data_button);
        data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAnalytics_nav();
            }
        });



        bill_btn = (Button) findViewById(R.id.button_enterBills);
        bill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billEnter_nav();
            }
        });

        signout_btn = (Button) findViewById(R.id.signout_button);
        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUserInfo(currentUser);
    }

    public void updateUserInfo(FirebaseUser user){

        if(user != null){
            username.setText("Username: "+ user.getEmail());
        }
        else{
            toastMessage("Bye!", 1);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void ecoReminders_nav() {
        Intent intent = new Intent(this, EcoReminders.class);
        startActivity(intent);
    }

    public void showerTimer_nav() {
        Intent intent = new Intent(this, ShowerTimer.class);
        startActivity(intent);
    }

    public void dataAnalytics_nav() {
        Intent intent = new Intent(this, DataAnalytics.class);
        startActivity(intent);
    }

    public void billEnter_nav() {
        Intent intent = new Intent(this, EnterBills.class);
        startActivity(intent);
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUserInfo(null);
                    }
                });
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
