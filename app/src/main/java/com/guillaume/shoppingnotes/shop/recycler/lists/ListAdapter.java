package com.guillaume.shoppingnotes.shop.recycler.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private java.util.List<List> lists;
    private ListAdapterInterface listener;
    private java.util.List<HasForItem> listItems;

    public ListAdapter(java.util.List<List> lists, ListAdapterInterface listener, java.util.List<HasForItem> listItems) {
        this.lists = lists;
        this.listener = listener;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_recycler, viewGroup, false);
        return new ListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        int nbItems = 0;
        for (HasForItem item: listItems)
            if (item.getListId().equals(lists.get(i).getId()))
                nbItems += 1;
        listViewHolder.display(lists.get(i), nbItems);
    }

    @Override
    public int getItemCount() { return lists.size(); }
}
