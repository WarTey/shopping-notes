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

import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.hasforitems.GetHasForItems;
import com.guillaume.shoppingnotes.database.async.interfaces.HasForItemsInterface;
import com.guillaume.shoppingnotes.database.async.interfaces.ListsInterface;
import com.guillaume.shoppingnotes.database.async.lists.GetLists;
import com.guillaume.shoppingnotes.database.async.lists.InsertList;
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

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements MyListsFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, ListAdapterInterface, ListsInterface, HasForItemsInterface, FirebaseUsersInterface, FirebaseListsInterface, FirebaseHasForItemsInterface {

    private java.util.List<HasForItem> hasForItems;
    private FirebaseDatabase firebaseDatabase;
    private TextView txtEmail, txtName;
    private java.util.List<List> lists;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AppDatabase db;
    private boolean online;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = getIntent().getParcelableExtra("user");
        online = getIntent().getBooleanExtra("online", false);
        db = AppDatabase.getInstance(this);

        initToolbar();
        initNavigationView();

        if (online && ConnectivityHelper.isConnectedToNetwork(this))
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
    public void firebaseListsResponse(java.util.List<List> lists) {
        this.lists = lists;
        new FirebaseHasForItemsHelper(this, firebaseDatabase).getHasForItems();
    }

    @Override
    public void firebaseHasForItemsResponse(java.util.List<HasForItem> hasForItems) {
        this.hasForItems = hasForItems;
        initRecyclerViewLists();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void firebaseListCreated(List list) {
        new InsertList(db, list, user.getEmail()).execute();
        Toast.makeText(this, "List " + list.getName() + " created", Toast.LENGTH_SHORT).show();
        alertDialog.cancel();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewListFragment()).addToBackStack(null).commit();
        //toolbar.setTitle(R.string.new_list);
    }

    @Override
    public void firebaseListNonCreated(String listName) { Toast.makeText(this, "List " + listName + " not created", Toast.LENGTH_SHORT).show(); }

    @Override
    public void listsResponse(java.util.List<List> lists) {
        this.lists = lists;
        new GetHasForItems(db).execute(this);
    }

    @Override
    public void hasForItemsResponse(java.util.List<HasForItem> hasForItems) {
        this.hasForItems = hasForItems;
        initRecyclerViewLists();
        progressBar.setVisibility(View.GONE);
    }

    public void initRecyclerViewLists() {
        java.util.List<List> listsNotDone = new ArrayList<>();
        if (lists != null)
            for (List list : lists)
                if (!list.getDone())
                    listsNotDone.add(list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(listsNotDone, this, hasForItems));
    }

    @Override
    public void newListFromMyListsFragment(TextInputLayout inputListName, AlertDialog alertDialog) {
        String txtListName = inputListName.getEditText().getText().toString().trim();
        this.alertDialog = alertDialog;

        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            for (List list: lists)
                if (txtListName.equals(list.getName())) {
                    inputListName.setError("This name is already taken by one of your lists");
                    return;
                }
            new FirebaseListsHelper(this, firebaseDatabase).createList(txtListName);
        } else if (!online) Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void listFromMyListsFragment(View view, ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            new FirebaseListsHelper(this, firebaseDatabase).getLists();
        } else if (!online) new GetLists(db, user.getEmail()).execute(this);
    }

    @Override
    public void addItemsToList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {

        } else if (!online) Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initAlert(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {

        } else if (!online) Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {

        } else if (!online) Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void historyList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            list.setDone(true);
        } else if (!online) Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }
}
