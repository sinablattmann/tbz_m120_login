package com.sixgroup.m120.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.m120.R;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.UserDao;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextFirstname;
    EditText editTextLastname;
    EditText editTextEmailRegister;
    EditText editTextPasswordRegister;
    EditText editTextPasswordConfirm;
    Button buttonRegistrieren;

    private UserDao userDao;

    //on create method starts activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        buttonRegistrieren = findViewById(R.id.buttonRegistrieren);
        buttonRegistrieren.setEnabled(true);

    }


    //changes activity to "activity_uploadpicture"
    public void goToWelcomeActivity(View view) {
        if (dataIsValid()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            saveToDatabase();

            startActivity(intent);
        }
    }


    public void saveToDatabase() {

        String firstName = editTextFirstname.getText().toString().trim();
        String lastName = editTextLastname.getText().toString().trim();
        String email = editTextEmailRegister.getText().toString().trim();
        String password = "" + editTextPasswordConfirm.getText().toString().trim().hashCode();

        userDao.insertUser(new User(firstName, lastName, email, password));
        finish();
    }


    //validates the data
    public boolean dataIsValid() {
        boolean isValid = true;

        //validates the firstname and gives an error
        editTextFirstname = findViewById(R.id.editTextVorname);
        String firstName = editTextFirstname.getText().toString().trim();
        if (!nameIsValid(firstName)) {
            editTextFirstname.setError("Der Vorname muss zwischen 1 und 50 Zeichen sein.");
            isValid = false;
        }

        //validates the lastname and gives an error
        editTextLastname = findViewById(R.id.editTextNachname);
        String lastName = editTextLastname.getText().toString().trim();
        if (!nameIsValid(lastName)) {
            editTextLastname.setError("Der Nachname muss zwischen 1 und 50 Zeichen sein.");
            isValid = false;
        }

        //validates the email and gives an error
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        String email = editTextEmailRegister.getText().toString().trim();
        if (!emailIsValid(email)) {
            editTextEmailRegister.setError("Email muss ein korrektes Format enthalten (max.muster@test.com)");
            isValid = false;
        }

        //validates the password and gives an error
        editTextPasswordRegister = findViewById(R.id.editTextPasswortRegister);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswortBestaetigen);
        String password = editTextPasswordRegister.getText().toString().trim();
        String passwordConfirm = editTextPasswordConfirm.getText().toString().trim();
        if (!controllPassword(password, passwordConfirm)) {
            editTextPasswordConfirm.setError("Die beiden Passwörter stimmen nicht überein");
            isValid = false;
        }

        //if everything is valid, isValid is true;
        return isValid;
    }

    //changes activity to "activity_login"
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //checks if name is valid
    public boolean nameIsValid(String name) {
        if (name.length() > 0 && name.length() < 51) {
            return true;
        } else {
            return false;
        }
    }

    //checks if email is valid
    public boolean emailIsValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.length() < 100 && email.matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }

    //checks if the passwords are the same
    public boolean controllPassword(String password1, String password2) {
        if (password1.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

}
