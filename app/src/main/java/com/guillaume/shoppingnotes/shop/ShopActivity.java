package com.guillaume.shoppingnotes.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.firebase.database.get.FirebaseGetUser;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseGetUserInterface;
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.tools.ConnectivityHelper;

public class ShopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FirebaseGetUserInterface {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView txtEmail, txtName;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AppDatabase db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = getIntent().getParcelableExtra("user");
        db = AppDatabase.getInstance(this);

        initToolbar();
        initNavigationView();

        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            databaseReference = firebaseDatabase.getReference("users");
            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new FirebaseGetUser(this));
        }

        //if (savedInstanceState == null)
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyListsFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.my_lists);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.shop);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressLint("SetTextI18n")
    void initNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_my_lists);

        View viewNavHeader = navigationView.getHeaderView(0);
        txtName = viewNavHeader.findViewById(R.id.txtName);
        txtEmail = viewNavHeader.findViewById(R.id.txtEmail);
        if (user != null) {
            txtName.setText(user.getFirstname() + " " + user.getLastname());
            txtEmail.setText(user.getEmail());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_my_lists:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyListsFragment()).addToBackStack(null).commit();
                toolbar.setTitle(R.string.my_lists);
                break;
            case R.id.nav_my_history:
                toolbar.setTitle(R.string.my_history);
                break;
            case R.id.nav_my_groups:
                toolbar.setTitle(R.string.my_groups);
                break;
            case R.id.nav_my_account:
                toolbar.setTitle(R.string.my_account);
                break;
            case R.id.nav_sign_out:
                setResult(Activity.RESULT_OK);
                finish();
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void firebaseUserResponse(User user) {
        this.user = user;
        txtName.setText(user.getFirstname() + " " + user.getLastname());
        txtEmail.setText(user.getEmail());
    }
}
