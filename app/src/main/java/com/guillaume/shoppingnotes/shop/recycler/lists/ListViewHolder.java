package com.guillaume.shoppingnotes.shop.recycler.lists;

import android.annotation.SuppressLint;
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
import com.guillaume.shoppingnotes.model.List;

public class ListViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private final TextView checked;
    private final AppCompatImageView toolbar;

    private List list;

    public ListViewHolder(@NonNull final View itemView, final ListAdapterInterface listener) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.txtName);
        checked = itemView.findViewById(R.id.txtChecked);
        toolbar = itemView.findViewById(R.id.toolbar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(toolbar.getContext(), toolbar);
                popupMenu.inflate(R.menu.options_menu_lists);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.add_items:
                                Log.d("debug", "onMenuItemClick: 1 " + list.getId());
                                break;
                            case R.id.add_history:
                                listener.historyList(list);
                                break;
                            case R.id.rename:
                                listener.initAlert(list);
                                break;
                            case R.id.delete:
                                listener.removeList(list);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void display(List list, int nbItems) {
        this.list = list;
        if (list.getChecked() == nbItems && nbItems != 0)
            image.setImageResource(android.R.drawable.checkbox_on_background);
        name.setText(list.getName());
        checked.setText(list.getChecked() + "/" + nbItems);
    }
}
