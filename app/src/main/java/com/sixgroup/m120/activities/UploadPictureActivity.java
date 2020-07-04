package com.sixgroup.m120.activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.UserDao;
import com.sixgroup.m120.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

//Class that opens the activity in which you can upload photos
public class UploadPictureActivity extends AppCompatActivity {

    // Define the pic id
    private static final int pic_id = 123;

    //the user Dao
    private UserDao userDao;

    // Define the button and imageview type variable
    private static final String PROVIDER_PATH = "ch.noseryoung.lernendeverwaltung.provider";
    private static final String TAG = "NewApprenticeActivity";
    private String currentPhotoName;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView click_image_id;
    private Uri currentPhotoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpicture);

        //set Dao
        userDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        // OnClickListener that opens camera
        Button photoButton = findViewById(R.id.newApprentice_photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        // OnClickListener if picture was taken, the picture opens up in fullscreen, else the camera is opened
        CircleImageView apprenticePhoto = findViewById(R.id.newApprentice_apprenticePhoto);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        }
        apprenticePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPhotoUri == null) {
                    openCamera();
                } else {
                    //openFullscreenApprenticePhoto();
                }
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File imageFile = null;
            try {
                // Image file is created with the createImageFile() method
                imageFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Error occurred while creating the File");
            }

            if (imageFile != null) {
                Context context = getApplicationContext();
                currentPhotoUri = FileProvider.getUriForFile(
                        context, PROVIDER_PATH, imageFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView photo = findViewById(R.id.newApprentice_apprenticePhoto);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                photo.setImageURI(currentPhotoUri);
            }
        } else {
            // When the picture hasn't been taken, the apprentice has no picture
            currentPhotoUri = null;
            currentPhotoName = null;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_LernendeFoto_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,        // Filename without extension
                ".jpg",         // File extension
                storageDir      // Directory, where file should be saved
        );
        currentPhotoName = image.getName();
        return image;
    }

    public void goToWelcomeActivity(View view){
        Intent intent = new Intent (this, WelcomeActivity.class);
        saveToDatabase(intent);

        startActivity(intent);
    }

    //saves data into database with no image
    public void saveToDatabase(Intent intent) {

        String firstName = getIntent().getStringExtra(getString(R.string.editTextVorname));
        String lastName = getIntent().getStringExtra(getString(R.string.editTextNachname));
        String email = getIntent().getStringExtra(getString(R.string.editTextEmail));
        String password = getIntent().getStringExtra(getString(R.string.editTextPassword));

        User user;

        if (currentPhotoName == null || currentPhotoName.trim().length() == 0) {
            currentPhotoName = "none";
        }
        userDao.insertUser(new User(firstName, lastName, email, password, currentPhotoName));
        finish();
    }

}