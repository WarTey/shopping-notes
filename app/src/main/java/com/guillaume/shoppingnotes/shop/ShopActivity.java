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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.hasforitems.DeleteHasForItems;
import com.guillaume.shoppingnotes.database.async.hasforitems.DeleteHasForItemsFromList;
import com.guillaume.shoppingnotes.database.async.hasforitems.GetHasForItems;
import com.guillaume.shoppingnotes.database.async.hasforitems.InsertHasForItems;
import com.guillaume.shoppingnotes.database.async.hasforitems.UpdateHasForItem;
import com.guillaume.shoppingnotes.database.async.interfaces.HasForItemsInterface;
import com.guillaume.shoppingnotes.database.async.interfaces.ItemsInterface;
import com.guillaume.shoppingnotes.database.async.interfaces.ListsInterface;
import com.guillaume.shoppingnotes.database.async.items.GetItems;
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
import com.muddzdev.styleabletoast.StyleableToast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements MyListFragment.OnFragmentInteractionListener, MyListsFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, NewListFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, ListAdapterInterface, ItemAdapterInterface, ListsInterface, HasForItemsInterface, ItemsInterface, FirebaseUsersInterface, FirebaseListsInterface, FirebaseHasForItemsInterface, FirebaseItemsInterface {

    private java.util.List<HasForItem> hasForItems;
    private FirebaseDatabase firebaseDatabase;
    private java.util.List<Item> items;
    private TextView txtEmail, txtName;
    private java.util.List<List> lists;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private boolean online, history;
    private ItemAdapter itemAdapter;
    private ListAdapter listAdapter;
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
            String firstname = user.getFirstname().length() > 8 ? user.getFirstname().substring(0, 8) + "..." : user.getFirstname();
            String lastname = user.getLastname().length() > 8 ? user.getLastname().substring(0, 8) + "..." : user.getLastname();
            txtName.setText(firstname + " " + lastname);
            txtEmail.setText(user.getEmail().length() > 8 ? user.getEmail().substring(0, 8) + "..." : user.getEmail());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_my_lists:
                toolbar.setTitle(R.string.my_lists);
                listAdapter = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyListsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_my_history:
                toolbar.setTitle(R.string.my_history);
                listAdapter = null;
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
                StyleableToast.makeText(this, "Disconnected", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void firebaseUserResponse(User user) {
        this.user = user;
        String firstname = user.getFirstname().length() > 8 ? user.getFirstname().substring(0, 8) + "..." : user.getFirstname();
        String lastname = user.getLastname().length() > 8 ? user.getLastname().substring(0, 8) + "..." : user.getLastname();
        txtName.setText(firstname + " " + lastname);
        txtEmail.setText(user.getEmail().length() > 8 ? user.getEmail().substring(0, 8) + "..." : user.getEmail());
    }

    @Override
    public void firebaseListsResponse(java.util.List<List> lists) {
        this.lists = lists;
        new FirebaseHasForItemsHelper(this, firebaseDatabase).getHasForItems();
    }

    @Override
    public void firebaseHasForItemsResponse(java.util.List<HasForItem> hasForItems) {
        this.hasForItems = hasForItems;
        if (items != null)
            initRecyclerViewItems();
        initRecyclerViewLists();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void firebaseListCreated(List list) {
        new InsertList(db, list, user.getEmail()).execute();
        StyleableToast.makeText(this, "List " + list.getName() + " created", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
        alertDialog.cancel();
        startNewListFragment(list.getId());
    }

    @Override
    public void firebaseListUpdated(List list) {
        StyleableToast.makeText(this, "List " + list.getName() + " updated", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
        new UpdateList(db, list, user.getEmail()).execute();
    }

    @Override
    public void firebaseListDeleted(List list) {
        StyleableToast.makeText(this, "List " + list.getName() + " deleted", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
        new DeleteHasForItemsFromList(user.getEmail(), list, db).execute(this);
    }

    @Override
    public void firebaseItemCreated(Item item) {
        new FirebaseHasForItemsHelper(this, firebaseDatabase).createHasForItems(listId, item.getId());
        new InsertItems(user.getEmail(), db, item).execute(this);
    }

    @Override
    public void firebaseHasForItemsCreated(String itemId) {
        StyleableToast.makeText(this, "Item added to your list", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
    }

    @Override
    public void firebaseItemsResponse(java.util.List<Item> items) {
        this.items = items;
        initRecyclerViewItems();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void firebaseHasForItemsChecked(HasForItem hasForItem) {
        StyleableToast.makeText(this, "Item checked", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
        new UpdateHasForItem(db, user.getEmail(), hasForItem).execute();
    }

    @Override
    public void firebaseHasForItemsDeleted(HasForItem hasForItem) {
        StyleableToast.makeText(this, "Item deleted", Toast.LENGTH_LONG, R.style.CustomToastCheck).show();
        new DeleteHasForItems(db, user.getEmail(), hasForItem).execute();
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
        if (items != null)
            initRecyclerViewItems();
        initRecyclerViewLists();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void itemsResponse(java.util.List<Item> items) {
        this.items = items;
        initRecyclerViewItems();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void newListFromMyListsFragment(TextInputLayout inputListName, AlertDialog alertDialog) {
        String txtListName = "";
        if (inputListName.getEditText() != null)
            txtListName = inputListName.getEditText().getText().toString().trim();
        this.alertDialog = alertDialog;

        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            for (List list: lists)
                if (txtListName.equals(list.getName())) {
                    inputListName.setError("This name is already taken by one of your lists");
                    return;
                }
            new FirebaseListsHelper(this, firebaseDatabase).createList(txtListName);
        } else if (!online)
            StyleableToast.makeText(this, "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
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
    public void showItemsFromNewListFragment(View view, java.util.List<Item> items) {
        recyclerView = findViewById(R.id.newListRecyclerView);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ItemAdapter(items, ShopActivity.this, true, null, null));
        }
    }

    @Override
    public void listFromMyListFragment(ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setVisibility(View.VISIBLE);

        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            new FirebaseItemsHelper(this, firebaseDatabase).getItems();
        else
            new GetItems(db).execute(this);
    }

    @Override
    public void addItemsToList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            startNewListFragment(list.getId());
        else if (!online)
            StyleableToast.makeText(this, "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
    }

    @Override
    public void initAlert(final List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.list_name, null);
            final TextInputLayout inputListName = view.findViewById(R.id.inputListName);
            Button btnCreate = view.findViewById(R.id.btnCreateList);

            if (inputListName.getEditText() != null)
                inputListName.getEditText().setText(list.getName());
            btnCreate.setText(R.string.rename);
            builder.setCancelable(true);
            builder.setView(view);
            alertDialog = builder.create();
            if (alertDialog.getWindow() != null)
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            view.findViewById(R.id.btnCreateList).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { renameList(list, inputListName); }
            });
        } else if (!online)
            StyleableToast.makeText(this, "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
    }

    @Override
    public void removeList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            new FirebaseListsHelper(this, firebaseDatabase).deleteList(list);
        else if (!online)
            StyleableToast.makeText(this, "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
    }

    @Override
    public void historyList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            list.setDone(true);
            new FirebaseListsHelper(this, firebaseDatabase).updateList(list);
        } else if (!online)
            StyleableToast.makeText(this, "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
    }

    @Override
    public void seeItems(List list) { startListFragment(list); }

    @Override
    public void noHistoryList(List list) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this)) {
            list.setDone(false);
            new FirebaseListsHelper(this, firebaseDatabase).updateList(list);
        } else if (!online)
            StyleableToast.makeText(this, "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
    }

    @Override
    public void addItemToList(Item item) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            new FirebaseItemsHelper(this, firebaseDatabase).createItem(item);
    }

    @Override
    public void checkItem(Item item, ImageView checked) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            for (HasForItem hasForItem: hasForItems)
                if (hasForItem.getListId().equals(this.listId) && hasForItem.getItemId().equals(item.getId()))
                    new FirebaseHasForItemsHelper(this, firebaseDatabase).checkHasForItems(this.listId, item.getId(), !hasForItem.getChecked());
    }

    @Override
    public void removeItem(Item item) {
        if (online && ConnectivityHelper.isConnectedToNetwork(this))
            for (HasForItem hasForItem: hasForItems)
                if (hasForItem.getListId().equals(this.listId) && hasForItem.getItemId().equals(item.getId()))
                    new FirebaseHasForItemsHelper(this, firebaseDatabase).deleteHasForItems(this.listId, item.getId(), hasForItem.getChecked());
    }

    private void startNewListFragment(String listId) {
        this.listId = listId;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewListFragment()).addToBackStack(null).commit();
        for (List list: lists)
            if (list.getId().equals(listId))
                toolbar.setTitle(list.getName());
    }

    private void startListFragment(List list) {
        this.listId = list.getId();
        itemAdapter = null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyListFragment()).addToBackStack(null).commit();
    }

    private void initRecyclerViewLists() {
        if (listAdapter == null) {
            listAdapter = new ListAdapter(lists, this, hasForItems, history);
            recyclerView = findViewById(R.id.recyclerView);
            if (recyclerView != null) {
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(listAdapter);
            }
        } else
            listAdapter.updateData(lists, hasForItems);
    }

    private void initRecyclerViewItems() {
        java.util.List<Item> listItems = new ArrayList<>();
        for (Item item: items)
            for (HasForItem hasForItem: hasForItems)
                if (hasForItem.getItemId().equals(item.getId()) && hasForItem.getListId().equals(listId))
                    listItems.add(item);

        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter(listItems, ShopActivity.this, false, hasForItems, listId);
            recyclerView = findViewById(R.id.newListRecyclerView);
            if (recyclerView != null) {
                setMyListToolbar(listItems);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(itemAdapter);
            }
        } else
            itemAdapter.updateData(listItems, hasForItems);
    }

    private void setMyListToolbar(java.util.List<Item> items) {
        double price = 0;
        for (Item item: items)
            price += item.getPrice();

        for (List list: lists)
            if (list.getId().equals(listId)) {
                if (list.getName().length() > 15)
                    toolbar.setTitle(list.getName().substring(0, 15) + "... - " + new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue() + "€");
                else
                    toolbar.setTitle(list.getName() + " - " + new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue() + "€");
            }
    }

    private void renameList(List list, TextInputLayout inputListName) {
        String txtListName = "";
        if (inputListName.getEditText() != null)
            txtListName = inputListName.getEditText().getText().toString().trim();
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
