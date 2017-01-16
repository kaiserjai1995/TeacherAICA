package edu.its.solveexponents.teacheraica.content;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.TeacherAICADB;
import edu.its.solveexponents.teacheraica.model.UsersDB;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jairus on 1/14/17.
 */

public class LoginActivity extends AppCompatActivity {
    public static UsersDB usersdb;
    public static TeacherAICADB teacheraicadb;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    String uname = "";

    @InjectView(R.id.input_username)
    EditText _usernameText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    FancyButton _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + "/teacheraica");

        if (!fileDir.exists()) fileDir.mkdir();

        usersdb = UsersDB.getInstance(getApplicationContext());

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Bold.ttf");

        TextView teacher_aica_title = (TextView) findViewById(R.id.teacher_aica_title);
        teacher_aica_title.setTypeface(font);

        TextView login_title = (TextView) findViewById(R.id.login_title);
        login_title.setTypeface(font);

        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        uname = LoginActivity.usersdb.getUsername(username, password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (uname.isEmpty()) {
                            onLoginFailed();
                            progressDialog.dismiss();
                        } else {
                            teacheraicadb = TeacherAICADB.getInstance(getApplicationContext(), uname);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            onLoginSuccess();
                            startActivity(i);
                            progressDialog.dismiss();
                        }
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // By default we just finish the Activity and log them in automatically
                teacheraicadb = TeacherAICADB.getInstance(getApplicationContext(), uname);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Wrong username and/or password", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        Matcher m = Pattern.compile("[a-zA-Z0-9\\\\._\\\\-]{3,}").matcher(username);

        if (username.isEmpty() || !m.find()) {
            _usernameText.setError("Enter a Valid Username");
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

        return valid;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

}
