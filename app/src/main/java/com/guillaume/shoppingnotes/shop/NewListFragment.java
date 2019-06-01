package com.guillaume.shoppingnotes.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.Item;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private List<Item> items;
    private View view;

    public NewListFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_list, container, false);
        items = new ArrayList<>();
        if (getActivity() != null)
            requestQueue = Volley.newRequestQueue(getActivity());
        progressBar = view.findViewById(R.id.progressBarNewList);
        progressBar.setVisibility(View.VISIBLE);
        showItems("Bread");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_items);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Bread");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
            mListener = (OnFragmentInteractionListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener (NewListFragment)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        items.clear();
        progressBar.setVisibility(View.VISIBLE);
        if (s == null || s.trim().isEmpty()) {
            showItems("Bread");
            return false;
        }
        showItems(s);
        return false;
    }

    private void showItems(String search) {
        String url = "https://api.walmartlabs.com/v1/search?apiKey=bbvbb7b76zdzff6ypet45vxu&query=" + search;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int index = 0; index < jsonArray.length(); index++) {
                        JSONObject item = jsonArray.getJSONObject(index);
                        if (item.has("upc") && item.has("name") && item.has("thumbnailImage") && item.has("salePrice"))
                            items.add(new Item(item.getString("upc"), item.getString("name"), item.getString("thumbnailImage"), item.getDouble("salePrice")));
                    }
                    mListener.showItemsFromNewListFragment(view, items);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
        requestQueue.add(request);
    }

    public interface OnFragmentInteractionListener {
        void showItemsFromNewListFragment(View view, List<Item> items);
    }
}
