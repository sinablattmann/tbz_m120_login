package com.sixgroup.m120.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sixgroup.m120.persistence.User;

import java.util.List;

import com.sixgroup.m120.R;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private static final String TAG = "UserAdapter";

    private List<User> userDataset;
    private OnListItemClickListener onItemClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(List<User> userDataset, OnListItemClickListener onItemClickListener) {
        this.userDataset = userDataset;
        this.onItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_entry, parent, false);

        @SuppressWarnings("Redundant variable can be used for debugging")
        UserViewHolder vh = new UserViewHolder(v, onItemClickListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // - get element from user dataset at this position
        // - replace the contents of the view with that element
        String firstName = userDataset.get(position).getFirstName();
        String lastName = userDataset.get(position).getLastName();
        String email = userDataset.get(position).getEmail();

        String initials = getInitials(firstName, lastName);

        holder.fullName.setText(firstName + " " + lastName);
        holder.email.setText(email);
        holder.initials.setText(initials);
    }

    public String getInitials(String firstName, String lastName) {
        char firstLetter = firstName.toUpperCase().charAt(0);
        char secondLetter = lastName.toUpperCase().charAt(0);

        return firstLetter + "" + secondLetter;
    }

    // Return the size of the user dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return userDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnListItemClickListener onItemClickListener;

        TextView fullName;
        TextView email;
        TextView initials;

        UserViewHolder(View v, final OnListItemClickListener onItemclickListener) {
            super(v);
            fullName = v.findViewById(R.id.list_user_fullNameTextView);
            email = v.findViewById(R.id.list_user_emailTextView);
            initials = v.findViewById(R.id.list_user_userPhoto);

            this.onItemClickListener = onItemclickListener;
            v.setOnClickListener(this);

            fullName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callItemClickListener();
                }
            });

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callItemClickListener();
                }
            });

            initials.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callItemClickListener();
                }
            });
        }

        @Override
        public void onClick(View v) {
            callItemClickListener();
        }

        private void callItemClickListener() {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener {

        void onItemClick(int position);
    }
}
