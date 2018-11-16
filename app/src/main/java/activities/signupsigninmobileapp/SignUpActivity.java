package activities.signupsigninmobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import businessLogic.Logic;
import businessLogic.LogicFactory;
import exceptions.EmailNotUniqueException;
import exceptions.LoginExistingException;

/**
 *
 * @author Gaizka
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE = 0;
    ImageButton imgUserPhoto;
    EditText txtFullName;
    EditText txtLogin;
    EditText txtEmail;
    EditText pwdPassword;
    EditText pwdConfirmPassword;
    CheckBox chkAgree;
    TextView hpTermsConditions;
    Button btnSignUp;
    TextView txtClickHere;
    byte[] bArray;
    Blob my_blob = null;

    /**
     * Method to display de view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        imgUserPhoto = findViewById(R.id.imgUserPhoto);
        btnSignUp = findViewById(R.id.btnSignUp);
        hpTermsConditions = findViewById(R.id.hpTermsConditions);
        txtClickHere = findViewById(R.id.txtClickHere);

        imgUserPhoto.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        hpTermsConditions.setOnClickListener(this);
        txtClickHere.setOnClickListener(this);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bitmap oBitmap;
        InputStream oInputStream;

        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            if(data == null){
                Toast.makeText(this,getResources().getString(R.string.Error), Toast.LENGTH_LONG).show();
                return;
            }
            try {
                oInputStream = this.getApplicationContext().getContentResolver().openInputStream(data.getData());
                oBitmap = BitmapFactory.decodeStream( oInputStream );
                imgUserPhoto.setImageBitmap(oBitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                oBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bArray = bos.toByteArray();
                my_blob.setBytes(1,bArray);
            } catch (Exception e) {
                Toast.makeText( this, getResources().getString(R.string.Exception), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Logger object used to log messages for the app.
     */
    protected static final Logger LOGGER = Logger.getLogger("ui.controllers");

    /**
     * Maximum text fields lengths.
     */
    protected final int MAX_LENGTH = 255;
    protected final int MAX_LENGTH_FULLNAME = 50;
    protected final int MAX_LENGTH_EMAIL = 50;
    protected final int MAX_LENGTH_LOGIN = 20;
    protected final int MAX_LENGTH_PASSWORD = 15;

    /**
     * Method for validating fullname.
     *
     * @param fullname Fullname to validate
     * @throws IllegalArgumentException Fullname is not valid
     */
    private void validateFullname(String fullname) throws IllegalArgumentException {
        String FULLNAME_PATTERN = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(FULLNAME_PATTERN);

        if (fullname == null || fullname.trim().equals("")) {
            Toast.makeText(this,"Field can not be empty.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Field can not be empty.");
        }

        if (fullname.trim().length() >= MAX_LENGTH_FULLNAME) {
            Toast.makeText(this,"Fullname is too long", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Fullname is too long.");
        }

        if (!pattern.matcher(fullname).matches()) {
            Toast.makeText(this,"Full name can only be composed of uppercase and lowercase letters.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Fullname can only be composed of " + "uppercase or lowercase letters.");
        }
    }

    /**
     * Method for validating email.
     *
     * @param email Email to validate.
     * @throws IllegalArgumentException Email is not valid.
     */
    private void validateEmail(String email) throws IllegalArgumentException {
        //String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        if (email == null || email.trim().equals("")) {
            Toast.makeText(this,"Email is too long", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Field can not be empty");
        }

        if (email.trim().length() >= MAX_LENGTH_EMAIL) {
            Toast.makeText(this,"Email is too long", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Email is too long.");
        }

        if (!pattern.matcher(email).matches()) {
            Toast.makeText(this,"Enter a valid email", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Enter a valid email.");
        }
    }
    /**
     * Method for validating login.
     *
     * @param login Login to validate
     * @throws IllegalArgumentException Login is not valid.
     */
    private void validateLogin(String login) throws IllegalArgumentException {
        String LOGIN_PATTERN = "[a-zA-Z0-9]+";
        Pattern pattern = Pattern.compile(LOGIN_PATTERN);

        if (login == null || login.trim().equals("")) {
            Toast.makeText(this,"How are you sopposed to sign in?", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("How are you supposed to sign in?");
        }

        if (login.trim().length() >= MAX_LENGTH_LOGIN) {
            Toast.makeText(this,"Login is too long", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Login is too long.");
        }

        if (!pattern.matcher(login).matches()) {
            Toast.makeText(this,"Login can only be composed by letters and numbers", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Login can only be composed by letters and numbers");
        }
    }

    /**
     * Method for validating password.
     *
     * @param password Password to validate.
     * @param confirmPassword Password confirmation.
     * @throws IllegalArgumentException Password is not valid.
     */
    private void validatePassword(String password, String confirmPassword) throws IllegalArgumentException {
        String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        if (password == null || password.trim().equals("")) {
            Toast.makeText(this,"Security first, enter a password.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Security first, enter a password.");
        }

        if (password.trim().length() >= MAX_LENGTH_PASSWORD) {
            Toast.makeText(this,"Password is too long.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Password is too long.");
        }

        if (!pattern.matcher(password).matches()) {
            Toast.makeText(this,"Not secure enough! Try combining lowercase, uppercase and numbers.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Not secure enough! Try combining lowercase, uppercase and numbers.");
        }

        if (password.trim().length() < 6) {
            Toast.makeText(this,"Password is too short.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Password is too short.");
        }

        if (confirmPassword == null || (!confirmPassword.trim().equals(password))) {
            Toast.makeText(this,"Passwords must coincide.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Passwords must coincide.");
        }
    }


    /**
     * Opens Sign In window.
     */
    private void openSignInWindow() {
        try {
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "UI SignUpDesktopFxmlController: Error opening sign in window.",
                    e.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        if(imgUserPhoto.isPressed()){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        } else if(txtClickHere.isPressed()){
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        } else if(hpTermsConditions.isPressed()){
            Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse("/PruebaTermsOfUse.pdf"));
            startActivity(browser);
        }else if(btnSignUp.isPressed()){
           SignUp();
        }

    }

    private void SignUp() {
        // Boolean to check if all fields are filled correctly
        boolean validFields = true;

        // Validate terms of use
        if (!chkAgree.isSelected()) {
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: Terms of use not accepted.");

            validFields = false;
        }
        // Validate password
        try {
            validatePassword(pwdPassword.getText().toString(), pwdConfirmPassword.getText().toString());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            pwdPassword.requestFocus();

            validFields = false;
        }
        // Validate login
        try {
            validateLogin(txtLogin.getText().toString());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtLogin.requestFocus();

            validFields = false;
        }
        // Validate email
        try {
            validateEmail(txtEmail.getText().toString());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtEmail.requestFocus();

            validFields = false;
        }
        // Validate full name
        try {
            validateFullname(txtFullName.getText().toString());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtFullName.setBackgroundColor(Color.RED);
            txtFullName.requestFocus();

            validFields = false;
        }
        // If all fields are filled correctly sign up the user
        if (validFields) {
            UserBean user = new UserBean();
            user.setFullName(txtFullName.getText().toString());
            user.setEmail(txtEmail.getText().toString());
            user.setLogin(txtLogin.getText().toString());
            user.setPassword(pwdPassword.getText().toString());
            //user.setUserPhoto(my_blob);

            Logic logic = new LogicFactory().createLogicImplementation(LogicFactory.USER_CLIENT_TYPE);

            try {
                logic.signUp(user);
                // If user is signed up correctly open sign in window
                openSignInWindow();

            } catch (LoginExistingException e) {
                LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());
                Toast.makeText(this,"Login already exists.", Toast.LENGTH_LONG).show();
            } catch (EmailNotUniqueException e) {
                LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());
                Toast.makeText(this,"Email already exists.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());
            }
        }
    }
}
