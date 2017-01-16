package edu.its.solveexponents.teacheraica.content;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.TeacherAICADB;
import fr.ganfra.materialspinner.MaterialSpinner;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 1/14/17.
 */

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    public static TeacherAICADB teacheraicadb;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_confirm_password) EditText _confirmPasswordText;
    @InjectView(R.id.input_lastname) EditText _lastnameText;
    @InjectView(R.id.input_firstname) EditText _firstnameText;
    @InjectView(R.id.input_middlename) EditText _middlenameText;
    @InjectView(R.id.input_section) EditText _sectionText;
    @InjectView(R.id.input_age) EditText _ageText;
    @InjectView(R.id.spinner_gender) MaterialSpinner _genderText;
    @InjectView(R.id.btn_signup) FancyButton _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Bold.ttf");

        TextView teacher_aica_title = (TextView) findViewById(R.id.teacher_aica_title);
        teacher_aica_title.setTypeface(font);

        TextView signup_title = (TextView) findViewById(R.id.signup_title);
        signup_title.setTypeface(font);

        String[] GENDER = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GENDER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _genderText.setAdapter(adapter);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });


    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String username = _usernameText.getText().toString();
                        String password = _passwordText.getText().toString();
                        String lastname = _lastnameText.getText().toString();
                        String firstname = _firstnameText.getText().toString();
                        String middlename = _middlenameText.getText().toString();
                        String section = _sectionText.getText().toString();
                        String age = _ageText.getText().toString();
                        String gender = _genderText.getSelectedItem().toString();

                        if (LoginActivity.usersdb.checkUsernameIfExists(username).isEmpty()) {
                            LoginActivity.usersdb.addUser(username, password, lastname, firstname, middlename, section, age, gender);

                            try {
                                teacheraicadb = TeacherAICADB.getInstance(getApplicationContext(), username);
                            } catch (Exception e) {
                                LoginActivity.teacheraicadb.logSystemError("TEACHERAICADB \n" + e.toString());

                                new AlertDialog.Builder(getApplicationContext())
                                        .setTitle("Oooops!")
                                        .setMessage("Something went wrong. We will fix this as soon as possible.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }

                            onSignupSuccess();
                            progressDialog.dismiss();
                        } else {
                            onSignupFailed();
                            progressDialog.dismiss();
                        }

                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
//        setResult(RESULT_OK, null);
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Account Creation failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirmPasswordText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String firstname = _firstnameText.getText().toString();
        String middlename = _middlenameText.getText().toString();
        String section = _sectionText.getText().toString();
        String age = _ageText.getText().toString();
        String gender = _genderText.getSelectedItem().toString();

        Matcher m = Pattern.compile("[a-zA-Z0-9\\\\._\\\\-]{3,}").matcher(username);

        if (username.isEmpty() || username.length() < 3) {
            _usernameText.setError("Username should have at least 3 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (!m.find()) {
            _usernameText.setError("Username should only contain letters, numbers, underscores, dashes, should not be empty and should not be less than 3 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Password should be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (!confirmPassword.equals(password)) {
            _passwordText.setError("Password Field and Confirm Password Field do not match!");
            _confirmPasswordText.setError("Password Field and Confirm Password Field do not match!");
            valid = false;
        }

        if (lastname.matches(".*\\\\d.*")) {
            _lastnameText.setError("Field should only contain letters");
            valid = false;
        } else {
            _lastnameText.setError(null);
        }

        if (firstname.matches(".*\\\\d.*")) {
            _firstnameText.setError("Field should only contain letters");
            valid = false;
        } else {
            _firstnameText.setError(null);
        }

        if (middlename.matches(".*\\\\d.*")) {
            _middlenameText.setError("Field should only contain letters");
            valid = false;
        } else {
            _middlenameText.setError(null);
        }

        if (section.matches(".*\\\\d.*")) {
            _sectionText.setError("Field should only contain letters");
            valid = false;
        } else {
            _sectionText.setError(null);
        }

        if (!age.matches("[0-9]+")) {
            _ageText.setError("Field should only contain numbers");
            valid = false;
        } else {
            _ageText.setError(null);
        }

        if (gender.matches(".*\\\\d.*")) {
            _genderText.setError("Field should only contain letters");
            valid = false;
        } else {
            _genderText.setError(null);
        }

        return valid;
    }
}
