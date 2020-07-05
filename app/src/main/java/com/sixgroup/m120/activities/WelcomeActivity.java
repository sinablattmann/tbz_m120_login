package com.sixgroup.m120.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.m120.persistence.DataAccess;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.UserDao;
import com.sixgroup.m120.R;


//Class that starts, if the User is logged in
//Welcome Screen with name
public class WelcomeActivity extends AppCompatActivity implements DataAccess {


    //creates activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        UserDao userDao = getDataAccess();



        //get the User that logged in
        Intent intent = getIntent();
        String email = intent.getStringExtra(getString(R.string.editTextEmail));
        User user = userDao.getByEmail(email);


        ((TextView) findViewById(R.id.willkommen2)).setText(user.getFirstName() + " " + user.getLastName());
    }

    //changes activity to "activity_login"
    public void goToLogin (View view){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToList (View view) {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public UserDao getDataAccess() {
        return AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();
    }
}