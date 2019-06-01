package com.guillaume.shoppingnotes.shop.recycler.items;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private boolean json;
    private String listId;
    private List<Item> items;
    private List<HasForItem> hasForItems;
    private ItemAdapterInterface listener;

    public ItemAdapter(List<Item> items, ItemAdapterInterface listener, boolean json, List<HasForItem> hasForItems, String listId) {
        this.items = items;
        this.listener = listener;
        this.json = json;
        this.listId = listId;
        this.hasForItems = hasForItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
        return new ItemViewHolder(view, listener, json);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        if (json)
            itemViewHolder.display(items.get(i));
        else
            itemViewHolder.display(items.get(i), hasForItems, listId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Item> items, List<HasForItem> hasForItems) {
        this.items.clear();
        this.hasForItems.clear();
        this.items.addAll(items);
        this.hasForItems.addAll(hasForItems);
        notifyDataSetChanged();
    }
}
