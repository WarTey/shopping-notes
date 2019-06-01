package com.guillaume.shoppingnotes.shop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.tools.ConnectivityHelper;
import com.muddzdev.styleabletoast.StyleableToast;

public class MyListsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MyListsFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_lists, container, false);
        mListener.listFromMyListsFragment((ProgressBar) view.findViewById(R.id.progressBarMyLists));
        view.findViewById(R.id.floatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (getActivity() != null) {
                if (ConnectivityHelper.isConnectedToNetwork(getActivity()))
                    showDialog();
                else
                    StyleableToast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
            }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) mListener = (OnFragmentInteractionListener) context;
        else throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener (MyListsFragment)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams") final View view = layoutInflater.inflate(R.layout.list_name, null);
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        view.findViewById(R.id.btnCreateList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (ConnectivityHelper.isConnectedToNetwork(getActivity())) {
                TextInputLayout inputListName = view.findViewById(R.id.inputListName);
                String txtListName = inputListName.getEditText().getText().toString().trim();
                if (txtListName.isEmpty())
                    inputListName.setError("This field cannot be empty");
                else
                    mListener.newListFromMyListsFragment(inputListName, alertDialog);
            } else
                StyleableToast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG, R.style.CustomToastConnection).show();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void newListFromMyListsFragment(TextInputLayout inputListName, AlertDialog alertDialog);
        void listFromMyListsFragment(ProgressBar progressBar);
    }
}
