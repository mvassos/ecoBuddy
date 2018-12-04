package ecobuddy.cmps121.ecobuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button2;
    Button button3;
    Button signout;
    Button buttonBill;

    TextView username;

    String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
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
        button = (Button) findViewById(R.id.ecoreminders_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ecoReminders_nav();
            }
        });

        button2 = (Button) findViewById(R.id.showertimer_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showerTimer_nav();

            }
        });

        button3 = (Button) findViewById(R.id.data_button);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAnalytics_nav();
            }
        });



        buttonBill = (Button) findViewById(R.id.button_enterBills);
        buttonBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billEnter_nav();
            }
        });

        signout = (Button) findViewById(R.id.signout_button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: headed to signout()");
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
            Toast.makeText(this, "Signed Out", Toast.LENGTH_LONG).show();
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
        Log.d(TAG, "signOut: entered");
        // Firebase sign out
        mAuth.signOut();
        Log.d(TAG, "signOut: mAuth.signOut");
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: google sign out finish");
                        updateUserInfo(null);
                    }
                });
    }

}
