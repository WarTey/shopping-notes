package com.guillaume.shoppingnotes.auth;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.auth.async.RegisterUser;
import com.guillaume.shoppingnotes.auth.async.interfaces.RegisterUserInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.tools.CredentialsVerification;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, RegisterUserInterface {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.login, new LoginFragment()).commit();
    }

    @Override
    public void registerFromLoginFragment() { getSupportFragmentManager().beginTransaction().replace(R.id.login, new RegisterFragment()).addToBackStack(null).commit(); }

    @Override
    public void registerFromRegisterFragment(User user, TextInputLayout inputEmail) {
        RegisterUser registerUser = new RegisterUser(this, db, user);
        registerUser.execute(inputEmail);
    }

    @Override
    public void userRegistered() {
        getSupportFragmentManager().beginTransaction().replace(R.id.login, new LoginFragment()).addToBackStack(null).commit();
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userNonRegistered(TextInputLayout textInputLayout) { CredentialsVerification.registerFailed(textInputLayout); }
}
