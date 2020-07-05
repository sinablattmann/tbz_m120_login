package com.sixgroup.m120.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sixgroup.m120.R;
import com.sixgroup.m120.persistence.AppDatabase;
import com.sixgroup.m120.persistence.DataAccess;
import com.sixgroup.m120.persistence.User;
import com.sixgroup.m120.persistence.UserDao;

public class RegisterActivity extends AppCompatActivity implements DataAccess {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText passwordConfirm;
    Button registerButton;

    private UserDao userDao;

    //on create method starts activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao = getDataAccess();

        registerButton = findViewById(R.id.buttonRegister);
        registerButton.setEnabled(true);

    }


    //changes activity to "activity_uploadpicture"
    public void goToWelcomeActivity(View view) {
        if (dataIsValid()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            saveToDatabase();
            intent.putExtra(getString(R.string.editTextEmail), email.getText().toString().trim());

            startActivity(intent);
        }
    }


    public void saveToDatabase() {

        String firstName = this.firstName.getText().toString().trim();
        String lastName = this.lastName.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String password = "" + passwordConfirm.getText().toString().trim().hashCode();

        userDao.insertUser(new User(firstName, lastName, email, password));
        finish();
    }


    //validates the data
    public boolean dataIsValid() {
        boolean isValid = true;

        //validates the firstname and gives an error
        firstName = findViewById(R.id.editTextFirstName);
        String firstName = this.firstName.getText().toString().trim();
        if (!nameIsValid(firstName)) {
            this.firstName.setError("Der Vorname muss zwischen 1 und 50 Zeichen sein.");
            isValid = false;
        }

        //validates the lastname and gives an error
        lastName = findViewById(R.id.editTextLastname);
        String lastName = this.lastName.getText().toString().trim();
        if (!nameIsValid(lastName)) {
            this.lastName.setError("Der Nachname muss zwischen 1 und 50 Zeichen sein.");
            isValid = false;
        }

        //validates the email and gives an error
        email = findViewById(R.id.editTextEmailRegister);
        String email = this.email.getText().toString().trim();
        if (!emailIsValid(email)) {
            this.email.setError("Email muss ein korrektes Format enthalten (max.muster@test.com)");
            isValid = false;
        }

        //validates the password and gives an error
        password = findViewById(R.id.editTextPasswortRegister);
        passwordConfirm = findViewById(R.id.editTextConfirmPassword);
        String password = this.password.getText().toString().trim();
        String passwordConfirm = this.passwordConfirm.getText().toString().trim();
        if (!checkPassword(password, passwordConfirm)) {
            this.passwordConfirm.setError("Die beiden Passwörter stimmen nicht überein");
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
    public boolean checkPassword(String password1, String password2) {
        if (password1.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDao getDataAccess() {
        return AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();
    }

}
