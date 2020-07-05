package com.sixgroup.m120.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.DataAccess;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.R;
import com.sixgroup.m120.persistence.UserDao;

public class UserdataActivity extends AppCompatActivity implements DataAccess {
    private static final String TAG = "Userdataactivity";

    // Userdate which are shown
    private User selectedUser;

    /**
     * Creates the view and displays it to the user.
     * Gets user data out of bundle and displays it on the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        getSupportActionBar().setTitle(R.string.information);

        // Database connection
        UserDao userDao = getDataAccess();

        TextView firstnameTextView = findViewById(R.id.userdata_firstname);
        TextView lastnameTextView = findViewById(R.id.userdata_lastname);
        TextView emailTextView = findViewById(R.id.userdata_email);

        try {
            // Fetch user by given user id in bundle
            Bundle bundle = getIntent().getExtras();
            int userID = bundle.getInt(UserListActivity.EXTRA_USER_ID);
            selectedUser = userDao.getById(userID);

            firstnameTextView.setText(selectedUser.getFirstName());
            lastnameTextView.setText(selectedUser.getLastName());
            emailTextView.setText(selectedUser.getEmail());

        } catch (NullPointerException nullex) {
            Log.e(TAG, nullex.getMessage() + " --- On getting bundle user id");
        }
    }

    @Override
    public UserDao getDataAccess() {
        return AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();
    }

    public void finish(View view) {
        finish();
    }
}
