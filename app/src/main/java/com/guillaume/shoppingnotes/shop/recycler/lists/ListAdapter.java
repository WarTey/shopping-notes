package com.guillaume.shoppingnotes.shop.recycler.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private boolean history, group;
    private java.util.List<List> lists;
    private ListAdapterInterface listener;
    private java.util.List<HasForItem> listItems;
    private java.util.List<HasForGroup> hasForGroups;

    public ListAdapter(java.util.List<List> lists, ListAdapterInterface listener, java.util.List<HasForItem> listItems, boolean history, boolean group, java.util.List<HasForGroup> hasForGroups) {
        this.lists = lists;
        this.listener = listener;
        this.listItems = listItems;
        this.history = history;
        this.group = group;
        this.hasForGroups = hasForGroups;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_recycler, viewGroup, false);
        return new ListViewHolder(view, listener, history);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        int nbItems = 0, nbChecked = 0;
        for (HasForItem item: listItems) {
            if (item.getListId().equals(lists.get(i).getId())) {
                nbItems += 1;
                if (item.getChecked())
                    nbChecked += 1;
            }
        }
        if (group) {
            for (HasForGroup hasForGroup: hasForGroups)
                if (hasForGroup.getListId().equals(lists.get(i).getId()))
                    listViewHolder.display(lists.get(i), nbItems, nbChecked, group, hasForGroup);
        } else
            listViewHolder.display(lists.get(i), nbItems, nbChecked, group, null);
    }

    @Override
    public int getItemCount() { return lists.size(); }

    public void updateData(java.util.List<List> lists, java.util.List<HasForItem> listItems, java.util.List<HasForGroup> hasForGroups) {
        this.lists.clear();
        this.listItems.clear();
        this.hasForGroups.clear();
        this.lists.addAll(lists);
        this.listItems.addAll(listItems);
        this.hasForGroups.addAll(hasForGroups);
        notifyDataSetChanged();
    }
}
