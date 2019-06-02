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

    TextInputLayout inputLastname, inputFirstname;
    private OnFragmentInteractionListener mListener;
    private User user;

    public AccountFragment() { }

    private void initializeInput(View view) {
        inputLastname = view.findViewById(R.id.inputLastname);
        inputFirstname = view.findViewById(R.id.inputFirstname);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        assert getArguments() != null;
        user = getArguments().getParcelable("user");

        initializeInput(view);
        inputLastname.getEditText().setText(user.getLastname());
        inputFirstname.getEditText().setText(user.getFirstname());

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
        String txtLastname = inputLastname.getEditText().getText().toString().trim();
        String txtFirstname = inputFirstname.getEditText().getText().toString().trim();

        for (TextInputLayout field : new TextInputLayout[]{inputLastname, inputFirstname}) {
            field.setError(null);
            field.setErrorEnabled(false);
        }

        if (getActivity() != null && txtFirstname.equals(user.getFirstname()) && txtLastname.equals(user.getLastname()))
            StyleableToast.makeText(getActivity(), "Please edit at least one field", Toast.LENGTH_LONG, R.style.CustomToastInvalid).show();
        else {
            if (!txtFirstname.isEmpty() && !txtLastname.isEmpty()) {
                user.setFirstname(txtFirstname);
                user.setLastname(txtLastname);
                mListener.editFromAccountFragment(new User(user.getId(), txtLastname, txtFirstname, user.getEmail(), user.getPassword()));
            } else
                for (TextInputLayout field : new TextInputLayout[]{inputFirstname, inputLastname})
                    if (field.getEditText().getText().toString().trim().isEmpty()) {
                        field.setError("This field cannot be empty");
                        field.requestFocus();
                    }
        }
    }

    public interface OnFragmentInteractionListener {
        void editFromAccountFragment(User user);
    }
}
