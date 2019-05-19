package com.guillaume.shoppingnotes.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.hasforitems.DeleteHasForItems;
import com.guillaume.shoppingnotes.database.async.hasforitems.GetHasForItems;
import com.guillaume.shoppingnotes.database.async.hasforitems.InsertHasForItems;
import com.guillaume.shoppingnotes.database.async.interfaces.HasForItemsInterface;
import com.guillaume.shoppingnotes.database.async.interfaces.ItemsInterface;
import com.guillaume.shoppingnotes.database.async.interfaces.ListsInterface;
import com.guillaume.shoppingnotes.database.async.items.InsertItems;
import com.guillaume.shoppingnotes.database.async.lists.DeleteList;
import com.guillaume.shoppingnotes.database.async.lists.GetLists;
import com.guillaume.shoppingnotes.database.async.lists.InsertList;
import com.guillaume.shoppingnotes.database.async.lists.UpdateList;
import com.guillaume.shoppingnotes.firebase.database.FirebaseHasForItemsHelper;
import com.guillaume.shoppingnotes.firebase.database.FirebaseItemsHelper;
import com.guillaume.shoppingnotes.firebase.database.FirebaseListsHelper;
import com.guillaume.shoppingnotes.firebase.database.FirebaseUsersHelper;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseHasForItemsInterface;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseItemsInterface;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseListsInterface;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseUsersInterface;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.Item;
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.model.List;
import com.guillaume.shoppingnotes.shop.recycler.items.ItemAdapter;
import com.guillaume.shoppingnotes.shop.recycler.items.ItemAdapterInterface;
import com.guillaume.shoppingnotes.shop.recycler.lists.ListAdapter;
import com.guillaume.shoppingnotes.shop.recycler.lists.ListAdapterInterface;
import com.guillaume.shoppingnotes.tools.ConnectivityHelper;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements MyListsFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, NewListFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, ListAdapterInterface, ItemAdapterInterface, ListsInterface, HasForItemsInterface, ItemsInterface, FirebaseUsersInterface, FirebaseListsInterface, FirebaseHasForItemsInterface, FirebaseItemsInterface {

    private java.util.List<HasForItem> hasForItems;
    private FirebaseDatabase firebaseDatabase;
    private TextView txtEmail, txtName;
    private java.util.List<List> lists;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private boolean online, history;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AppDatabase db;
    private String listId;
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
                toolbar.setTitle(R.string.my_lists);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyListsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_my_history:
                toolbar.setTitle(R.string.my_history);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).addToBackStack(null).commit();
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
        startNewListFragment(list.getId());
    }

    @Override
    public void firebaseListUpdated(List list) {
        Toast.makeText(this, "List " + list.getName() + " updated", Toast.LENGTH_SHORT).show();
        new UpdateList(db, list, user.getEmail()).execute();
    }

    @Override
    public void firebaseListDeleted(List list) {
        Toast.makeText(this, "List " + list.getName() + " deleted", Toast.LENGTH_SHORT).show();
        new DeleteHasForItems(user.getEmail(), list, db).execute(this);
    }

    @Override
    public void firebaseItemCreated(Item item) {
        new FirebaseHasForItemsHelper(this, firebaseDatabase).createHasForItems(listId, item.getId());
        new InsertItems(user.getEmail(), db, item).execute(this);
    }

    @Override
    public void firebaseHasForItemsCreated(String itemId) {
        Toast.makeText(this, "Item added to your list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemCreated(Item item) {
        new InsertHasForItems(user.getEmail(), listId, item.getId(), db).execute();
    }

    @Override
    public void hasForItemsDeleted(List list) {
        new DeleteList(db, list, user.getEmail()).execute();
    }

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
        } else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void listFromMyListsFragment(ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);
        history = false;
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            new FirebaseListsHelper(this, firebaseDatabase).getLists(history);
        } else if (!online)
            new GetLists(db, user.getEmail(), this.history).execute(this);
    }

    @Override
    public void listFromHistoryFragment(ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);
        history = true;
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            new FirebaseListsHelper(this, firebaseDatabase).getLists(history);
        } else if (!online)
            new GetLists(db, user.getEmail(), this.history).execute(this);
    }

    @Override
    public void addItemsToList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            startNewListFragment(list.getId());
        else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initAlert(final List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.list_name, null);
            final TextInputLayout inputListName = view.findViewById(R.id.inputListName);
            Button btnCreate = view.findViewById(R.id.btnCreateList);

            inputListName.getEditText().setText(list.getName());
            btnCreate.setText(R.string.rename);
            builder.setCancelable(true);
            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            view.findViewById(R.id.btnCreateList).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { renameList(list, inputListName); }
            });
        } else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            new FirebaseListsHelper(this, firebaseDatabase).deleteList(list);
        else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void historyList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            list.setDone(true);
            new FirebaseListsHelper(this, firebaseDatabase).updateList(list);
        } else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void seeItems(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {

        } else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noHistoryList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            list.setDone(false);
            new FirebaseListsHelper(this, firebaseDatabase).updateList(list);
        } else if (!online)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showItemsFromNewListFragment(View view, java.util.List<Item> items) {
        RecyclerView recyclerView = findViewById(R.id.newListRecyclerView);
        if (recyclerView != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ItemAdapter(items, ShopActivity.this));
        }
    }

    @Override
    public void addItemToList(Item item) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            new FirebaseItemsHelper(this, firebaseDatabase).createItem(item);
    }

    private void startNewListFragment(String listId) {
        this.listId = listId;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewListFragment()).addToBackStack(null).commit();
        toolbar.setTitle(R.string.new_list);
    }

    private void initRecyclerViewLists() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ListAdapter(lists, this, hasForItems, history));
        }
    }

    private void renameList(List list, TextInputLayout inputListName) {
        String txtListName = inputListName.getEditText().getText().toString().trim();
        if (txtListName.isEmpty())
            inputListName.setError("This field cannot be empty");
        else {
            for (List userList: lists)
                if (txtListName.equals(userList.getName())) {
                    inputListName.setError("This name is already taken by one of your lists");
                    return;
                }
            list.setName(txtListName);
            new FirebaseListsHelper(this, firebaseDatabase).updateList(list);
            alertDialog.cancel();
        }
    }
}
