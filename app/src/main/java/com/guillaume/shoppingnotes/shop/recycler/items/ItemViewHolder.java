package com.guillaume.shoppingnotes.shop.recycler.items;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.Item;
import com.guillaume.shoppingnotes.tools.ConnectivityHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image, checked;
    private final TextView name;
    private final TextView price;

    private Item item;

    public ItemViewHolder(@NonNull final View itemView, final ItemAdapterInterface mListener, boolean json) {
        super(itemView);
        checked = itemView.findViewById(R.id.imageViewCheck);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.txtName);
        price = itemView.findViewById(R.id.txtPrice);

        if (json)
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mListener.addItemToList(item); }
        });
        else
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { mListener.checkItem(item, checked); }
            });
    }

    @SuppressLint("SetTextI18n")
    public void display(Item item) {
        this.item = item;
        Picasso.get().load(item.getImage()).into(image);
        name.setText(item.getName().length() > 35 ? item.getName().substring(0, 35) + "..." : item.getName());
        price.setText(item.getPrice() + "$");
    }

    @SuppressLint("SetTextI18n")
    public void display(Item item, List<HasForItem> hasForItems, String listId) {
        this.item = item;
        Picasso.get().load(item.getImage()).into(image);
        name.setText(item.getName().length() > 35 ? item.getName().substring(0, 35) + "..." : item.getName());
        price.setText(item.getPrice() + "$");

        for (HasForItem hasForItem: hasForItems)
            if (item.getId().equals(hasForItem.getItemId()) && listId.equals(hasForItem.getListId()) && hasForItem.getChecked())
                checked.setImageResource(R.drawable.check_mark);
    }
}
