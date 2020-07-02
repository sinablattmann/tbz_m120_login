package com.sixgroup.m120.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.R;
import com.sixgroup.m120.persistence.UserDao;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserdataActivity extends AppCompatActivity {
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
        UserDao apprenticeDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        // Returns picture by name depending on the string given to it
        //pprenticeImageViewManager apprenticeImageViewManager = new ApprenticeImageViewManager(this);

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

            //Bitmap apprenticePhotoBitmap = apprenticeImageViewManager.getApprenticePhotoAsBitmap(selectedApprentice.getPicture());
            //apprenticePhoto.setImageBitmap(apprenticePhotoBitmap);

            // OnClickListener opens image in fullscreen
            /*apprenticePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFullscreenApprenticePhoto();
                }
            });*/
        } catch (NullPointerException nullex) {
            Log.e(TAG, nullex.getMessage() + " --- On getting bundle apprentice id");
        }
    }
}
