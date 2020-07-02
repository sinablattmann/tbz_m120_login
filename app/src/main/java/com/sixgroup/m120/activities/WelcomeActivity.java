package com.sixgroup.m120.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.UserDao;
import com.sixgroup.m120.R;


//Class that starts, if the User is logged in
//Welcome Screen with name
public class WelcomeActivity extends AppCompatActivity {


    //creates activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView imageViewPicture = findViewById(R.id.imageView);
        UserDao userDao;

        //set Dao
        userDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        //get the User that logged in
        Intent intent = getIntent();
        String email = intent.getStringExtra(getString(R.string.editTextEmail));
        User user = userDao.getByEmail(email);

        //set TextViews to the name of the logged in User
        if(user.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            imageViewPicture.setImageBitmap(bitmap);
        }
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
}