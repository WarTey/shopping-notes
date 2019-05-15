package com.guillaume.shoppingnotes.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.firebase.database.FirebaseHasForItemsHelper;
import com.guillaume.shoppingnotes.firebase.database.FirebaseListsHelper;
import com.guillaume.shoppingnotes.firebase.database.FirebaseUsersHelper;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseHasForItemsInterface;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseListsInterface;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseUsersInterface;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.model.List;
import com.guillaume.shoppingnotes.shop.recycler.lists.ListAdapter;
import com.guillaume.shoppingnotes.shop.recycler.lists.ListAdapterInterface;
import com.guillaume.shoppingnotes.tools.ConnectivityHelper;

public class ShopActivity extends AppCompatActivity implements MyListsFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, ListAdapterInterface, FirebaseUsersInterface, FirebaseListsInterface, FirebaseHasForItemsInterface {

    private java.util.List<HasForItem> hasForItems;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private TextInputLayout inputListName;
    private TextView txtEmail, txtName;
    private java.util.List<List> lists;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
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

        if (ConnectivityHelper.isConnectedToNetwork(this))
            new FirebaseUsersHelper(this, firebaseDatabase).getUser();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyListsFragment()).addToBackStack(null).commit();
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

    @Override
    public void firebaseUserCreated() { }

    @Override
    public void firebaseUserNonCreated() { }

    @Override
    public void firebaseListsResponse(java.util.List<List> lists) {
        this.lists = lists;
        initRecyclerViewLists();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void firebaseHasForItemsResponse(java.util.List<HasForItem> hasForItems) {
        this.hasForItems = hasForItems;
        initRecyclerViewLists();
        progressBar.setVisibility(View.GONE);
    }

    public void initRecyclerViewLists() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ListAdapter(lists, this, hasForItems));
    }

    @Override
    public void newListFromMyListsFragment(TextInputLayout inputListName, AlertDialog alertDialog) {
        this.inputListName = inputListName;
        this.alertDialog = alertDialog;
        String txtListName = inputListName.getEditText().getText().toString().trim();

        /*if (ConnectivityHelper.isConnectedToNetwork(this)) {
            databaseReference = firebaseDatabase.getReference("lists");
            databaseReference.addValueEventListener(new FirebaseGetUser(this));
        }*/
        //java.util.List<List> userLists = db.listDao().getListsByUserId(db.userDao().getUserId(user.getEmail()), false);
        for (List list: lists)
            if (txtListName.equals(list.getName())) {
                inputListName.setError("This name is already taken by one of your lists");
                return;
            }
        //db.listDao().insertList(new List(txtListName, false, 0, db.userDao().getUserId(user.getEmail()), null));
        Toast.makeText(this, "List " + txtListName + " created", Toast.LENGTH_SHORT).show();
        alertDialog.cancel();

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewListFragment()).addToBackStack(null).commit();
        //toolbar.setTitle(R.string.new_list);
    }

    @Override
    public void listFromMyListsFragment(View view, ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            new FirebaseListsHelper(this, firebaseDatabase).getLists();
            new FirebaseHasForItemsHelper(this, firebaseDatabase).getHasForItems();
        }
    }

    @Override
    public void initAlert(List list) { }

    @Override
    public void removeList(List list) { }

    @Override
    public void historyList(List list) { }
}
