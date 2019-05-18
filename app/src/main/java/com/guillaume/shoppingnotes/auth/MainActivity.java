package com.guillaume.shoppingnotes.auth;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.database.async.users.GetUser;
import com.guillaume.shoppingnotes.database.async.users.InsertUser;
import com.guillaume.shoppingnotes.database.async.interfaces.UsersInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.firebase.auth.FirebaseLogin;
import com.guillaume.shoppingnotes.firebase.auth.FirebaseRegister;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseLoginInterface;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseRegisterInterface;
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.shop.ShopActivity;
import com.guillaume.shoppingnotes.tools.ConnectivityHelper;
import com.guillaume.shoppingnotes.tools.CredentialsVerification;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, UsersInterface, FirebaseRegisterInterface, FirebaseLoginInterface {

    private TextInputLayout inputEmail, inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private AppDatabase db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = AppDatabase.getInstance(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.login, new LoginFragment()).commit();
    }

    @Override
    public void registerFromLoginFragment() {
        if (ConnectivityHelper.isConnectedToNetwork(this))
            getSupportFragmentManager().beginTransaction().replace(R.id.login, new RegisterFragment()).addToBackStack(null).commit();
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loginFromLoginFragment(TextInputLayout inputEmail, TextInputLayout inputPassword) {
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;

        progressBar = findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.VISIBLE);

        if (ConnectivityHelper.isConnectedToNetwork(this))
            auth.signInWithEmailAndPassword(inputEmail.getEditText().getText().toString().trim(), inputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(new FirebaseLogin(this));
        else
            new GetUser(db, inputEmail.getEditText().getText().toString().trim()).execute(this);
    }

    @Override
    public void firebaseLogged() {
        progressBar.setVisibility(View.GONE);
        connectionSuccessful(user, true);
    }

    @Override
    public void firebaseNonLogged() {
        progressBar.setVisibility(View.GONE);
        CredentialsVerification.loginFailed(inputEmail, inputPassword);
    }

    @Override
    public void userResponse(User user) {
        progressBar.setVisibility(View.GONE);
        if (user != null && user.getPassword().equals(inputPassword.getEditText().getText().toString().trim())) {
            connectionSuccessful(user, false);
            return;
        }
        CredentialsVerification.loginFailed(inputEmail, inputPassword);
    }

    private void connectionSuccessful(User user, boolean online) {
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("online", online);
        startActivityForResult(intent, 1);
        if (online) Toast.makeText(this, "Connection successful", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Connection successful (Offline)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFromRegisterFragment(User user, TextInputLayout inputEmail) {
        this.inputEmail = inputEmail;
        this.user = user;

        progressBar = findViewById(R.id.progressBarRegister);
        progressBar.setVisibility(View.VISIBLE);

        if (ConnectivityHelper.isConnectedToNetwork(this))
            auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new FirebaseRegister(this, user));
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void firebaseRegistered() {
        user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        new InsertUser(db, user).execute(this);
    }

    @Override
    public void firebaseNonRegistered() {
        progressBar.setVisibility(View.GONE);
        CredentialsVerification.registerFailed(inputEmail);
    }

    @Override
    public void userCreated() {
        progressBar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.login, new LoginFragment()).addToBackStack(null).commit();
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }
}
