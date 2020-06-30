package com.sixgroup.m120.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sixgroup.m120.User.User;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.UserDao;
import com.sixgroup.m120.R;


//Class that opens first activity, which is the Login
public class LoginActivity extends AppCompatActivity {

    //Database connection
    public static UserDao userDao;

    //creates activity and shows the Login to the User
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();
    }

    //changes activity to "activity_register"
    public void goToRegisterActivity(View view){
        Intent intent = new Intent (this, RegisterActivity.class);
        startActivity(intent);
    }
    //changes activity to "activity_welcome"
    public void goToWelcomeActivity(Intent intent){
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent (this, WelcomeActivity.class);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPasswort);

        String password = editTextPassword.getText().toString();
        User user = userDao.getByEmail(editTextEmail.getText().toString());
        if(user != null) {
            if (("" + password.hashCode()).equals(user.getPasswort())) {
                intent.putExtra(getString(R.string.editTextEmail), user.getEmail());
                goToWelcomeActivity(intent);
            }
        }else{
            editTextEmail.setError("Passwort oder Email ist falsch");
        }
    }
}
