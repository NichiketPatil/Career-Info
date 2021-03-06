package com.anspace.reviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.anspace.reviews.Model.Writer;
import com.anspace.reviews.SideNavSections.AboutUsActivity;
import com.anspace.reviews.SideNavSections.ContactUsActivity;
import com.anspace.reviews.SideNavSections.ForyouActivity;
import com.anspace.reviews.SideNavSections.OffersActivity;
import com.anspace.reviews.SideNavSections.SuggestionsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import me.ibrahimsn.lib.SmoothBottomBar;


public  class scrollingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     DrawerLayout mDrawerLayout;
     ActionBarDrawerToggle mToggle;
     Toolbar toolbar1;
     DatabaseReference reference;
     FirebaseUser fuser;
     Dialog welcome;
     ImageView close;
     TextView name;
     TextView phone_no;
     ImageView profile_img;
     RelativeLayout content;
    SmoothBottomBar bottomBar;
    NavController navController;

    @Override
    protected void onStart() {
        super.onStart();

        if (!isOnline()){
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("Please Connect to Internet and try again")
                    .setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(scrollingActivity.this,SplashScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        else
            checkFirstRun();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        setNavigationViewListener();

        content = findViewById(R.id.container);

       fuser = FirebaseAuth.getInstance().getCurrentUser();
        assert fuser != null;
        if (!fuser.isAnonymous())
       reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
       welcome = new Dialog(this);

         bottomBar = findViewById(R.id.nav_view);
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(navView, navController);
        NavigationView navigationView =findViewById(R.id.sidenav);
//        bottomBar.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout = findViewById(R.id.drawer);
        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("");

        View header = navigationView.getHeaderView(0);

          name = header.findViewById(R.id.username_header);
          profile_img = header.findViewById(R.id.profile_image_header);
          phone_no = header.findViewById(R.id.phone_header);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar1, R.string.open,R.string.close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
                float scaleFactor = 6f;
                content.setScaleX(1 - (slideOffset / scaleFactor));
                content.setScaleY(1 - (slideOffset / scaleFactor));
            }


        };

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        if (!fuser.isAnonymous()){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 Writer writer = dataSnapshot.getValue(Writer.class);
                if (fuser!=null){
                    name.setText(fuser.getDisplayName());
                    phone_no.setText(fuser.getEmail());}
                else{
                    name.setText("Guest");}

                try {
                Glide.with(scrollingActivity.this)
                        .load(fuser.getPhotoUrl())
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profile_img);}
                catch (Exception e){
                Toast.makeText(scrollingActivity.this,e.getMessage() , Toast.LENGTH_SHORT).show(); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });}

    }

    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        bottomBar.setupWithNavController(menu,navController);


//        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;

    }

    private void setNavigationViewListener() {
        NavigationView navigationView =  findViewById(R.id.sidenav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.logout: {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(scrollingActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.for_u: startActivity(new Intent(scrollingActivity.this, ForyouActivity.class));break;
            case R.id.about_us: startActivity(new Intent(scrollingActivity.this, AboutUsActivity.class));break;
            case R.id.suggestions: startActivity(new Intent(scrollingActivity.this, SuggestionsActivity.class));break;
            case R.id.offers: startActivity(new Intent(scrollingActivity.this, OffersActivity.class));break;
            case R.id.contact_us: startActivity(new Intent(scrollingActivity.this, SubjectActivity.class));break;
        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
           showPopup();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }

    public void showPopup(){
        welcome.setContentView(R.layout.popup_welcome);
        close = welcome.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome.dismiss();
            }
        });
        welcome.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        welcome.show();
    }










}
