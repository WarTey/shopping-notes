package com.guillaume.shoppingnotes.shop.recycler.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.List;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private boolean history, group;
    private java.util.List<List> lists;
    private ListAdapterInterface listener;
    private java.util.List<HasForItem> listItems;
    private java.util.List<HasForGroup> hasForGroups;

    public ListAdapter(java.util.List<List> lists, ListAdapterInterface listener, java.util.List<HasForItem> listItems, boolean history, boolean group, java.util.List<HasForGroup> hasForGroups) {
        this.listener = listener;
        this.listItems = listItems;
        this.history = history;
        this.group = group;
        this.hasForGroups = hasForGroups;
        sortList(lists);
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
        java.util.List<List> tempList = new ArrayList<>(lists);
        this.lists.clear();
        this.listItems.clear();
        this.listItems.addAll(listItems);
        if (group) {
            java.util.List<HasForGroup> tempArr = new ArrayList<>(hasForGroups);
            this.hasForGroups.clear();
            this.hasForGroups.addAll(tempArr);
        }
        sortList(tempList);
        notifyDataSetChanged();
    }

    public void sortList(java.util.List<List> lists) {
        if (group) {
            this.lists = new ArrayList<>();
            for (HasForGroup hasForGroup : hasForGroups)
                if (hasForGroup.isOwner())
                    for (List list : lists)
                        if (hasForGroup.getListId().equals(list.getId()))
                            this.lists.add(list);
            for (HasForGroup hasForGroup : hasForGroups)
                if (hasForGroup.isStatus() && !hasForGroup.isOwner())
                    for (List list : lists)
                        if (hasForGroup.getListId().equals(list.getId()))
                            this.lists.add(list);
            for (HasForGroup hasForGroup : hasForGroups)
                if (!hasForGroup.isStatus())
                    for (List list : lists)
                        if (hasForGroup.getListId().equals(list.getId()))
                            this.lists.add(list);
        } else
            this.lists = lists;
    }
}
