package com.sixgroup.m120.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sixgroup.m120.R;
import com.sixgroup.m120.adapter.UserAdapter;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserAdapter.OnListItemClickListener {

    public static final String EXTRA_APPRENTICE_ID = "ch.noseryoung.lernendeverwaltung.activity.EXTRA_APPRENTICE_ID";

    //Database connection
    public static UserDao userDao;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        userDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        loadList();

        /*Button createApprenticeButton = findViewById(R.id.apprenticelist_createApprenticeButton);
        createApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewApprentice();
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList(){
        // Loads list from database
        users = userDao.getAll();

        RecyclerView recyclerView = findViewById(R.id.apprenticelist_apprenticesList);

        // Uses this setting to improve performance if the changes are known
        // in content does not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        // uses a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specifies an adapter
        UserAdapter mAdapter = new UserAdapter(users, this/*, apprenticeImageViewManager*/);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        User user = users.get(position);
        Intent intent = new Intent(this, UserdataActivity.class);
        intent.putExtra(EXTRA_APPRENTICE_ID, user.getId());
        startActivity(intent);
    }

    /**
     * Opens Activity with form for creating a new apprentice
     *
    private void openNewApprentice() {
        Intent intend = new Intent(this, NewApprenticeActivity.class);
        startActivity(intend);
    }*/
}
