package com.guillaume.shoppingnotes.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private List<Item> items;

    public NewListFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_list, container, false);
        items = new ArrayList<>();
        if (getActivity() != null)
            requestQueue = Volley.newRequestQueue(getActivity());
        progressBar = view.findViewById(R.id.progressBarNewList);
        progressBar.setVisibility(View.VISIBLE);
        showItems(view);
        return view;
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

    private void showItems(final View view) {
        String url = "https://api.walmartlabs.com/v1/search?apiKey=bbvbb7b76zdzff6ypet45vxu&query=ipod";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int index = 0; index < jsonArray.length(); index++) {
                        JSONObject item = jsonArray.getJSONObject(index);
                        if (item.has("name") && item.has("thumbnailImage") && item.has("salePrice"))
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
