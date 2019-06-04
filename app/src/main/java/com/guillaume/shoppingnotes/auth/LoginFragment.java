package com.guillaume.shoppingnotes.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.tools.CredentialsVerification;

public class LoginFragment extends Fragment {

    TextInputLayout inputEmail, inputPassword;

    private OnFragmentInteractionListener mListener;

    private void initializeInput(View view) {
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
    }

    public LoginFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeInput(view);
        view.findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mListener.registerFromLoginFragment(); }
        });
        view.findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loginClicked();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
            mListener = (OnFragmentInteractionListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener (LoginFragment)");
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    void loginClicked() {
        for (TextInputLayout field : new TextInputLayout[]{inputEmail, inputPassword}) {
            field.setError(null);
            field.setErrorEnabled(false);
        }

        if (CredentialsVerification.loginVerification(inputEmail, inputPassword))
            mListener.loginFromLoginFragment(inputEmail, inputPassword);
    }

    public interface OnFragmentInteractionListener {
        void registerFromLoginFragment();
        void loginFromLoginFragment(TextInputLayout inputEmail, TextInputLayout inputPassword);
    }
}
