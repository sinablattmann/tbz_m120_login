package com.sixgroup.appaminsina.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.appaminsina.User.User;
import com.sixgroup.appaminsina.persistence.AppDatabase;
import com.sixgroup.appaminsina.persistence.UserDao;
import com.sixgroup.appaminsina.R;

import java.io.ByteArrayOutputStream;

//Class that opens the activity in which you can upload photos
public class UploadPictureActivity extends AppCompatActivity {

    // Define the pic id
    private static final int pic_id = 123;

    //the user Dao
    private UserDao userDao;

    // Define the button and imageview type variable
    ImageView click_image_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpicture);

        //set Dao
        userDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        // By ID we can get each component
        // which id is assigned in XML file
        // get Buttons and imageview.
        click_image_id = findViewById(R.id.profilePic);


        // Camera_open button is for open the camera
        // and add the setOnClickListener in this button
        click_image_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, pic_id);
                }

            }
        });
    }

    public void goToWelcomeActivity(View view){
        Intent intent = new Intent (this, WelcomeActivity.class);
        saveToDatabase(intent);

        startActivity(intent);
    }

    // This method will help to retrieve the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            click_image_id.setImageBitmap(imageBitmap);
        }
    }

    //saves data into database with no image
    public void saveToDatabase(Intent intent) {

        String firstName = getIntent().getStringExtra(getString(R.string.editTextVorname));
        String lastName = getIntent().getStringExtra(getString(R.string.editTextNachname));
        String email = getIntent().getStringExtra(getString(R.string.editTextEmail));
        String password = getIntent().getStringExtra(getString(R.string.editTextPassword));

        ImageView image = findViewById(R.id.profilePic);
        Bitmap imageBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        byte[] img;
        User user;

        if(imageBitmap != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();
            user = new User(firstName, lastName, email, password, img);
        } else {
            user = new User(firstName, lastName, email, password, null);
        }

        intent.putExtra(getString(R.string.editTextEmail), email);

        userDao.insertUser(user);


    }

}