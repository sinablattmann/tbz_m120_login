package com.sixgroup.appaminsina.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.appaminsina.User.User;
import com.sixgroup.appaminsina.persistence.AppDatabase;
import com.sixgroup.appaminsina.persistence.UserDao;
import com.sixgroup.appaminsina.R;


//Class that starts, if the User is logged in
//Welcome Screen with name
public class WelcomeActivity extends AppCompatActivity {


    //creates activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //creating TextViews for the first- and lastname
        TextView textViewFirstnameWelcome = findViewById(R.id.textViewFirstnameWelcome);
        TextView textViewLastnameWelcome = findViewById(R.id.textViewLastnameWelcome);
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
        textViewFirstnameWelcome.setText(user.getVorname());
        textViewLastnameWelcome.setText(user.getNachname());
    }

    //changes activity to "activity_login"
    public void goToLogin (View view){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }
}