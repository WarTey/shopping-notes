package com.guillaume.shoppingnotes.shop.recycler.items;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
    private final AppCompatImageView toolbar;

    private Item item;

    public ItemViewHolder(@NonNull final View itemView, final ItemAdapterInterface mListener, boolean json) {
        super(itemView);
        checked = itemView.findViewById(R.id.imageViewCheck);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.txtName);
        price = itemView.findViewById(R.id.txtPrice);
        toolbar = itemView.findViewById(R.id.toolbar);

        if (json) {
            toolbar.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                mListener.addItemToList(item);
                }
            });
        } else {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(toolbar.getContext(), toolbar);
                    popupMenu.inflate(R.menu.options_menu_items);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.purchased_item:
                                    mListener.checkItem(item, checked);
                                    break;
                                case R.id.delete:
                                    mListener.removeItem(item);
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
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
            if (item.getId().equals(hasForItem.getItemId()) && listId.equals(hasForItem.getListId())) {
                if (hasForItem.getChecked())
                    checked.setVisibility(View.VISIBLE);
                else
                    checked.setVisibility(View.GONE);
            }
    }
}
