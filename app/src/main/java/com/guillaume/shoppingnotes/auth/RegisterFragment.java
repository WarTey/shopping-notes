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
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.tools.CredentialsVerification;

public class RegisterFragment extends Fragment {

    TextInputLayout inputLastname, inputFirstname, inputEmail, inputPassword, inputPasswordRepeat;

    private OnFragmentInteractionListener mListener;

    private void initializeInput(View view) {
        inputLastname = view.findViewById(R.id.inputLastname);
        inputFirstname = view.findViewById(R.id.inputFirstname);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputPasswordRepeat = view.findViewById(R.id.inputPasswordRepeat);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initializeInput(view);
        view.findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClicked();
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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener (RegisterFragment)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void registerClicked() {
        String txtLastname = inputLastname.getEditText().getText().toString().trim();
        String txtFirstname = inputFirstname.getEditText().getText().toString().trim();
        String txtEmail = inputEmail.getEditText().getText().toString().trim();
        String txtPassword = inputPassword.getEditText().getText().toString().trim();

        for (TextInputLayout field : new TextInputLayout[]{inputLastname, inputFirstname, inputEmail, inputPassword, inputPasswordRepeat}) {
            field.setError(null);
            field.setErrorEnabled(false);
        }

        if (CredentialsVerification.registerVerification(inputLastname, inputFirstname, inputEmail, inputPassword, inputPasswordRepeat))
            mListener.registerFromRegisterFragment(new User("", txtLastname, txtFirstname, txtEmail, txtPassword), inputEmail);
    }

    public interface OnFragmentInteractionListener { void registerFromRegisterFragment(User user, TextInputLayout inputEmail);}
}
