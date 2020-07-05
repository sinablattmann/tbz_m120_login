package com.sixgroup.m120.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sixgroup.m120.R;
import com.sixgroup.m120.adapter.UserAdapter;
import com.sixgroup.m120.persistence.DataAccess;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserAdapter.OnListItemClickListener, DataAccess {


    public static final String EXTRA_USER_ID = "ch.noseryoung.lernendeverwaltung.activity.EXTRA_USER_ID";

    //Database connection
    public static UserDao userDao;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        userDao = getDataAccess();

        loadList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList(){
        // Loads list from database
        users = userDao.getAll();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_userList);

        // Uses this setting to improve performance if the changes are known
        // in content does not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        // uses a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specifies an adapter
        UserAdapter mAdapter = new UserAdapter(users, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        User user = users.get(position);
        Intent intent = new Intent(this, UserdataActivity.class);
        intent.putExtra(EXTRA_USER_ID, user.getId());
        startActivity(intent);
    }

    @Override
    public UserDao getDataAccess() {
        return AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();
    }
}
