package ecobuddy.cmps121.ecobuddy;

import android.content.Intent;
import android.support.annotation.NonNull;
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

    TextView username;

    String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //initialize google sign in stuff to log out later
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
            username.setText("Username: "+user.getEmail());
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
