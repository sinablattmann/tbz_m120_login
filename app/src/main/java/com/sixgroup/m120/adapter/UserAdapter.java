package com.sixgroup.m120.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sixgroup.m120.persistence.User;

import java.util.List;

import com.sixgroup.m120.R;
//import com.sixgroup.m120.manager.ApprenticeImageViewManager;
import com.sixgroup.m120.persistence.User;

/**
 *
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private static final String TAG = "ApprenticeAdapter";

    private List<User> apprenticeDataset;
    private OnListItemClickListener onItemClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(List<User> apprenticeDataset, OnListItemClickListener onItemClickListener) {
        this.apprenticeDataset = apprenticeDataset;
        this.onItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_entry, parent, false);

        @SuppressWarnings("Redundant variable can be used for debugging")
        UserViewHolder vh = new UserViewHolder(v, onItemClickListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // - get element from apprentice dataset at this position
        // - replace the contents of the view with that element
        String firstName = apprenticeDataset.get(position).getVorname();
        String lastName = apprenticeDataset.get(position).getNachname();
        String email = apprenticeDataset.get(position).getEmail();

        String initials = getInitials(firstName, lastName);

        holder.fullName.setText(firstName + " " + lastName);
        holder.email.setText(email);
        holder.initials.setText(initials);
    }

    public String getInitials(String firstName, String lastName){
        char firstLetter = firstName.toUpperCase().charAt(0);
        char secondLetter = lastName.toUpperCase().charAt(0);

        return firstLetter + "" + secondLetter;
    }

    // Return the size of the apprentice dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return apprenticeDataset.size();
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
            fullName = v.findViewById(R.id.list_apprentice_fullNameTextView);
            email = v.findViewById(R.id.list_apprentice_emailTextView);
            initials = v.findViewById(R.id.list_apprentice_userPhoto);

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

        private void callItemClickListener(){
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener {

        void onItemClick(int position);
    }
}
