package com.guillaume.shoppingnotes.shop.recycler.members;

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
import com.guillaume.shoppingnotes.model.User;

class MemberViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private final TextView checked;
    private final AppCompatImageView toolbar;

    private User user;

    MemberViewHolder(@NonNull final View itemView, final MemberAdapterInterface mListener, final List list, final java.util.List<HasForGroup> hasForGroups, final boolean admin) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.txtName);
        checked = itemView.findViewById(R.id.txtChecked);
        toolbar = itemView.findViewById(R.id.toolbar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(toolbar.getContext(), toolbar);
            for (HasForGroup hasForGroup: hasForGroups)
                if (admin && hasForGroup.getUserId().equals(user.getId()) && hasForGroup.getListId().equals(list.getId()) && !hasForGroup.isOwner())
                    popupMenu.inflate(R.menu.options_menu_members);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.delete)
                        mListener.deleteMember(user, list);
                    return false;
                }
            });
            popupMenu.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void display(User user, boolean admin, boolean status) {
        this.user = user;
        String textName = user.getFirstname().charAt(0) + ". " + user.getLastname() + " (" + user.getEmail() + ")";
        name.setText(textName.length() > 50 ? textName.substring(0, 50) + "..." : textName);
        checked.setVisibility(View.GONE);

        if (admin)
            image.setImageResource(R.drawable.admin_group);
        else if (status)
            image.setImageResource(R.drawable.user_group);
        else
            image.setImageResource(R.drawable.invitation);
    }
}
