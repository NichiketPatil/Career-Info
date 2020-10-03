package com.anspace.reviews;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {


    Button btnNext;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    ViewPager pager;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    String TAG = "Nike";
    int RC_SIGN_IN = 1;


    @Override
    protected void onStart(){
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        overridePendingTransition(0,0);

        if (firebaseUser!= null){
            Intent intent = new Intent(LoginActivity.this,scrollingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnNext = findViewById(R.id.btn_next);
        progressBar = findViewById(R.id.progressBar);

        pager =findViewById(R.id.photos_viewpager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
        ImageAdapter adapter = new ImageAdapter(this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipe();

            }
        });


        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0||position==1){
                    btnNext.setText("NEXT");
                }
                else
                    btnNext.setText("ENTER");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private  void swipe(){
//        if (pager.getCurrentItem() != 2){
//            if (pager.getCurrentItem() == 1){
//                btnNext.setText("Enter");}
//            else
//                btnNext.setText("Next");
//            pager.setCurrentItem(getItem(+1), true);}
//
//        else {
//            btnNext.setText("Enter");
//            progressBar.setVisibility(View.VISIBLE);
//            Toast.makeText(LoginActivity.this, "Hehehe", Toast.LENGTH_SHORT).show();}
        if (pager.getCurrentItem()!=2){
            pager.setCurrentItem(getItem(+1), true);
        }
        else
            signIn();

    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    private void signIn() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressBar.setVisibility(View.GONE);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google Sign in failed", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressBar.setVisibility(View.GONE);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private  void updateUI(FirebaseUser firebaseUser){
        Intent intent = new Intent(LoginActivity.this,scrollingActivity.class);
        intent.putExtra("firebaseUser",firebaseUser);
        startActivity(intent);
    }

}
