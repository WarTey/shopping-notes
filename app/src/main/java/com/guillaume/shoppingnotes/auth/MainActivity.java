package com.guillaume.shoppingnotes.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guillaume.shoppingnotes.R;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.login, new LoginFragment()).commit();
    }
}
