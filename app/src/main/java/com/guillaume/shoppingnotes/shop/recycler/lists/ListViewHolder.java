package com.guillaume.shoppingnotes.shop.recycler.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.List;

class ListViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private final TextView checked;
    private final AppCompatImageView toolbar;

    private HasForGroup hasForGroup;
    private List list;
    private boolean group;

    ListViewHolder(@NonNull final View itemView, final ListAdapterInterface mListener, final boolean history) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.txtName);
        checked = itemView.findViewById(R.id.txtChecked);
        toolbar = itemView.findViewById(R.id.toolbar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(toolbar.getContext(), toolbar);
            if (history)
                popupMenu.inflate(R.menu.options_menu_history);
            else if (group) {
                if (hasForGroup.isOwner())
                    popupMenu.inflate(R.menu.options_menu_group_owner);
                else if (hasForGroup.isStatus())
                    popupMenu.inflate(R.menu.options_menu_group_member);
                else
                    popupMenu.inflate(R.menu.options_menu_group_invitation);
            } else
                popupMenu.inflate(R.menu.options_menu_lists);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.see_items:
                            mListener.seeItems(list);
                            break;
                        case R.id.add_items:
                            mListener.addItemsToList(list);
                            break;
                        case R.id.add_history:
                            mListener.historyList(list);
                            break;
                        case R.id.back_list:
                            mListener.noHistoryList(list);
                            break;
                        case R.id.rename:
                            mListener.initAlert(list, false);
                            break;
                        case R.id.delete:
                            mListener.removeList(list);
                            break;
                        case R.id.leave_group:
                            mListener.refuseInvit(list);
                            break;
                        case R.id.see_members:
                            mListener.seeMembers(list);
                            break;
                        case R.id.add_member:
                            mListener.initAlert(list, true);
                            break;
                        case R.id.invitation_accept:
                            mListener.acceptInvit(list);
                            break;
                        case R.id.invit_refuse:
                            mListener.refuseInvit(list);
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
    void display(List list, int nbItems, int nbChecked, boolean group, HasForGroup hasForGroup) {
        this.list = list;
        this.hasForGroup = hasForGroup;
        this.group = group;
        name.setText(list.getName().length() > 15 ? list.getName().substring(0, 15) + "..." : list.getName());
        checked.setText(nbChecked + "/" + nbItems);

        if (group) {
            if (hasForGroup.isOwner())
                image.setImageResource(R.drawable.admin_group);
            else if (hasForGroup.isStatus())
                image.setImageResource(R.drawable.user_group);
            else {
                image.setImageResource(R.drawable.invitation);
                checked.setText("");
            }
        } else {
            image.setImageResource(R.drawable.checkbox_unchecked);
            if (nbChecked == nbItems && nbItems != 0)
                image.setImageResource(R.drawable.checkbox_checked);
        }
    }
}
