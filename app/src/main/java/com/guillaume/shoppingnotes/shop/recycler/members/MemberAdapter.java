package com.guillaume.shoppingnotes.shop.recycler.members;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaume.shoppingnotes.R;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.User;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    private MemberAdapterInterface listener;
    private List<HasForGroup> hasForGroups;
    private com.guillaume.shoppingnotes.model.List list;
    private List<User> users;
    private Boolean admin;

    public MemberAdapter(List<HasForGroup> hasForGroups, List<User> users, MemberAdapterInterface listener, com.guillaume.shoppingnotes.model.List list, User user) {
        this.listener = listener;
        this.hasForGroups = hasForGroups;
        this.list = list;
        sortList(users);

        admin = false;
        for (HasForGroup hasForGroup: hasForGroups)
            if (hasForGroup.getUserId().equals(user.getId()) && hasForGroup.getListId().equals(list.getId()) && hasForGroup.isOwner())
                admin = true;
    }

    @NonNull
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_recycler, viewGroup, false);
        return new MemberViewHolder(view, listener, list, hasForGroups, admin);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int i) {
        for (HasForGroup hasForGroup: hasForGroups)
            if (hasForGroup.getUserId().equals(users.get(i).getId()) && hasForGroup.getListId().equals(list.getId()))
                memberViewHolder.display(users.get(i), hasForGroup.isOwner(), hasForGroup.isStatus());
    }

    @Override
    public int getItemCount() { return users.size(); }

    public void updateData(List<User> users, List<HasForGroup> hasForGroups) {
        this.hasForGroups.clear();
        this.hasForGroups.addAll(hasForGroups);
        this.users.clear();
        sortList(users);
        notifyDataSetChanged();
    }

    private void sortList(List<User> users) {
        this.users = new ArrayList<>();
        for (HasForGroup hasForGroup : hasForGroups)
            if (hasForGroup.isOwner() && hasForGroup.getListId().equals(list.getId()))
                for (User user: users)
                    if (hasForGroup.getUserId().equals(user.getId()))
                        this.users.add(user);
        for (HasForGroup hasForGroup : hasForGroups)
            if (hasForGroup.isStatus() && !hasForGroup.isOwner() && hasForGroup.getListId().equals(list.getId()))
                for (User user: users)
                    if (hasForGroup.getUserId().equals(user.getId()))
                        this.users.add(user);
        for (HasForGroup hasForGroup : hasForGroups)
            if (!hasForGroup.isStatus() && hasForGroup.getListId().equals(list.getId()))
                for (User user: users)
                    if (hasForGroup.getUserId().equals(user.getId()))
                        this.users.add(user);
    }
}
