package com.guillaume.shoppingnotes.shop.recycler.items;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.Item;
import com.squareup.picasso.Picasso;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private final TextView price;

    private Item item;

    public ItemViewHolder(@NonNull final View itemView, final ItemAdapterInterface mListener) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.txtName);
        price = itemView.findViewById(R.id.txtPrice);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mListener.addItemToList(item); }
        });
    }

    @SuppressLint("SetTextI18n")
    public void display(Item item) {
        this.item = item;
        Picasso.get().load(item.getImage()).into(image);
        name.setText(item.getName());
        if (item.getName().length() > 35)
            name.setText(item.getName().substring(0, 35) + "...");
        price.setText(item.getPrice() + "$");
    }
}
