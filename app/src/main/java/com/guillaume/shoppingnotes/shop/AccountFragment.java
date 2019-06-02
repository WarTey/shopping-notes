package com.guillaume.shoppingnotes.shop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.User;
import com.guillaume.shoppingnotes.tools.CredentialsVerification;
import com.muddzdev.styleabletoast.StyleableToast;

public class AccountFragment extends Fragment {

    TextInputLayout inputLastname, inputFirstname, inputEmail, inputPassword, inputPasswordRepeat;
    private OnFragmentInteractionListener mListener;
    private User user;

    public AccountFragment() { }

    private void initializeInput(View view) {
        inputLastname = view.findViewById(R.id.inputLastname);
        inputFirstname = view.findViewById(R.id.inputFirstname);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputPasswordRepeat = view.findViewById(R.id.inputPasswordRepeat);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        assert getArguments() != null;
        user = getArguments().getParcelable("user");

        initializeInput(view);
        inputLastname.getEditText().setText(user.getLastname());
        inputFirstname.getEditText().setText(user.getFirstname());
        inputEmail.getEditText().setText(user.getEmail());
        inputPassword.getEditText().setText(user.getPassword());
        inputPasswordRepeat.getEditText().setText(user.getPassword());

        view.findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClicked();
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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener (AccountFragment)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void editClicked() {
        String txtLastname = user.getLastname();
        String txtFirstname = user.getFirstname();
        String txtEmail = user.getEmail();
        String txtPassword = user.getPassword();
        if (inputLastname.getEditText() != null && inputFirstname.getEditText() != null && inputEmail.getEditText() != null && inputPassword.getEditText() != null && inputPasswordRepeat.getEditText() != null) {
            txtLastname = inputLastname.getEditText().getText().toString().trim();
            txtFirstname = inputFirstname.getEditText().getText().toString().trim();
            txtEmail = inputEmail.getEditText().getText().toString().trim();
            txtPassword = inputPassword.getEditText().getText().toString().trim();
        }

        for (TextInputLayout field : new TextInputLayout[]{inputLastname, inputFirstname, inputEmail, inputPassword, inputPasswordRepeat}) {
            field.setError(null);
            field.setErrorEnabled(false);
        }

        if (CredentialsVerification.registerVerification(inputLastname, inputFirstname, inputEmail, inputPassword, inputPasswordRepeat))
            mListener.editFromAccountFragment(new User(user.getId(), txtLastname, txtFirstname, txtEmail, txtPassword), inputEmail);
    }

    public interface OnFragmentInteractionListener {
        void editFromAccountFragment(User user, TextInputLayout inputEmail);
    }
}
