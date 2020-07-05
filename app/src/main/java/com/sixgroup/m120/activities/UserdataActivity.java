package com.sixgroup.m120.activities;

import android.graphics.Bitmap;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserdataActivity extends AppCompatActivity implements DataAccess {
    private static final String TAG = "ApprenticedataActivity";

    // Apprenticedata which are shown
    private User selectedUser;

    /**
     * Creates the view and displays it to the apprentice.
     * Gets apprentice data out of bundle and displays it on the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentice_data);
        getSupportActionBar().setTitle(R.string.informationen);

        // Database connection
        UserDao apprenticeDao = getDataAccess();

        TextView firstnameTextView = findViewById(R.id.apprenticedata_firstname);
        TextView lastnameTextView = findViewById(R.id.apprenticedata_lastname);
        CircleImageView apprenticePhoto = findViewById(R.id.apprenticedata_apprenticePhoto);

        try {
            // Fetch apprentice by given apprentice id in bundle
            Bundle bundle = getIntent().getExtras();
            int apprenticeID = bundle.getInt(UserListActivity.EXTRA_APPRENTICE_ID);
            selectedUser = apprenticeDao.getById(apprenticeID);

            firstnameTextView.setText(selectedUser.getVorname());
            lastnameTextView.setText(selectedUser.getNachname());

        } catch (NullPointerException nullex) {
            Log.e(TAG, nullex.getMessage() + " --- On getting bundle apprentice id");
        }
    }

    @Override
    public UserDao getDataAccess() {
        return AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();
    }
}
