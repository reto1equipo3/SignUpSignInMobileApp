package activities.signupsigninmobileapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import businessLogic.Logic;
import businessLogic.LogicFactory;
import exceptions.EmailNotUniqueException;
import exceptions.LoginExistingException;
import message.MessageType;
import model.UserBean;

/**
 * @author Gaizka
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, Thread.UncaughtExceptionHandler {

    EditText txtFullName;
    EditText txtLogin;
    EditText txtEmail;
    EditText pwdPassword;
    EditText pwdConfirmPassword;
    EditText txtPhoneNumber;
    EditText txtPostalCode;
    EditText txtTown;
    EditText txtAddress;
    EditText txtCreditCard;
    EditText txtCVV;
    EditText txtCardholderName;
    CheckBox chkAgree;
    TextView hpTermsConditions;
    Button btnSignUp;
    TextView txtClickHere;

    /**
     * Method to display de view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Ventana siempre en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        btnSignUp = findViewById(R.id.btnSignUp);
        hpTermsConditions = findViewById(R.id.hpTermsConditions);

        txtClickHere = findViewById(R.id.txtClickHere);
        btnSignUp.setOnClickListener(this);
        hpTermsConditions.setOnClickListener(this);
        txtClickHere.setOnClickListener(this);


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
    protected final int MAX_LENGTH_CREDIT_CARD = 16;
    protected final int MAX_LENGTH_CVV = 3;
    protected final int MAX_LENGTH_CARDHOLDER_NAME = 100;

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
     * Method for validating the phone number
     *
     * @param phoneNumber Phone number to validate
     * @throws IllegalArgumentException The phone number is not valid
     */
    private void validatePhoneNumber(String phoneNumber){
        String PHONE_PATTERN = "([6][0-9]{8})";
        Pattern pattern = Pattern.compile(PHONE_PATTERN);

        if(!pattern.matcher(phoneNumber).matches() || phoneNumber.trim().length() > 9){
            Toast.makeText(this, "Invalid phone number format.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Invalid phone number format.");
        }
    }

    /**
     * Method for validating the postal code
     *
     * @param postalCode Postal code to validate
     * @throws IllegalArgumentException The postal code is not valid
     */
    private void validatePostalCode(String postalCode){
        String CODE_PATTERN = "[4][0-9]{4}";
        Pattern pattern = Pattern.compile(CODE_PATTERN);

        if(!pattern.matcher(postalCode).matches() || postalCode.trim().length() > 5){
            Toast.makeText(this, "Invalid postal code format.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Invalid postal code format.");
        }
    }

    /**
     * Method for valldating the town
     * @param town Town to validate
     * @throws IllegalArgumentException The town is not valid
     */
    private void validateTown(String town){
        String TOWN_PATTERN = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(TOWN_PATTERN);

        if (town == null || town.trim().equals("")) {
            Toast.makeText(this,"Field can not be empty.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Field can not be empty.");
        }

        if (town.trim().length() >= MAX_LENGTH_FULLNAME) {
            Toast.makeText(this,"Town is too long", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Town is too long.");
        }

        if (!pattern.matcher(town).matches()) {
            Toast.makeText(this,"Town can only be composed of uppercase and lowercase letters.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Town can only be composed of " + "uppercase or lowercase letters.");
        }
    }

    /**
     * Method for validating the address
     * @param address Address to validate
     * @throws IllegalArgumentException Address is not valid
     */
    private void validateAddress(String address){
        String ADDRESS_PATTERN = "[a-zA-Z]+ [0-9]{2} [0-9][a-zA-Z]";
        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);

        if(!pattern.matcher(address).matches() || address.trim().length() > MAX_LENGTH_FULLNAME){
            Toast.makeText(this, "Invalid address format.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Invalid address format.");
        }
    }

    /**
     * Method for validating the credit card
     * @param creditCard
     * @throws IllegalArgumentException Credit card is not valid
     */
    private void validateCreditCard(String creditCard){
        String CARD_PATTERN = "[0-9]{16}";
        Pattern pattern = Pattern.compile(CARD_PATTERN);

        if(!pattern.matcher(creditCard).matches() && creditCard.trim().length() > MAX_LENGTH_CREDIT_CARD){
            Toast.makeText(this, "Invalid credit card format.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Invalid credit card format.");
        }
    }

    /**
     * Method for validating the CVV
     * @param cvv
     * @throws IllegalArgumentException CVV is not valid
     */
    private void validateCVV(String cvv){
        String CVV_PATTERN = "[0-9]{3}";
        Pattern pattern = Pattern.compile(CVV_PATTERN);

        if(!pattern.matcher(cvv).matches() && cvv.trim().length() > MAX_LENGTH_CVV){
            Toast.makeText(this, "Invalid CVV format.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Invalid CVV format.");
        }
    }

    private void validateCardholderName(String cardholderName){
        String CARDHOLDER_PATTERN = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(CARDHOLDER_PATTERN);

        if (cardholderName == null || cardholderName.trim().equals("")) {
            Toast.makeText(this,"Field can not be empty.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Field can not be empty.");
        }

        if (cardholderName.trim().length() >= MAX_LENGTH_CARDHOLDER_NAME) {
            Toast.makeText(this,"Cardholder name is too long", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Cardholder name is too long.");
        }

        if (!pattern.matcher(cardholderName).matches()) {
            Toast.makeText(this,"Cardholder name can only be composed of uppercase and lowercase letters.", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Cardholder name can only be composed of " + "uppercase or lowercase letters.");
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
        if(txtClickHere.isPressed()){
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();
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
        //  Validate phone number
        try{
            validatePhoneNumber(txtPhoneNumber.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtPhoneNumber.setBackgroundColor(Color.RED);
            txtPhoneNumber.requestFocus();

            validFields = false;
        }
        //  Validate postal code
        try{
            validatePostalCode(txtPostalCode.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtPostalCode.setBackgroundColor(Color.RED);
            txtPostalCode.requestFocus();

            validFields = false;
        }
        //  Validate town
        try{
            validateTown(txtTown.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtTown.setBackgroundColor(Color.RED);
            txtTown.requestFocus();

            validFields = false;
        }
        //  Validate address
        try{
            validateAddress(txtAddress.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtAddress.setBackgroundColor(Color.RED);
            txtAddress.requestFocus();

            validFields = false;
        }
        //  Validate credit card
        try{
            validateCreditCard(txtCreditCard.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtCreditCard.setBackgroundColor(Color.RED);
            txtCreditCard.requestFocus();

            validFields = false;
        }
        //  Validate CVV
        try{
            validateCVV(txtCVV.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtCVV.setBackgroundColor(Color.RED);
            txtCVV.requestFocus();

            validFields = false;
        }
        //  Validate cardholder name
        try{
            validateCardholderName(txtCardholderName.getText().toString());
        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

            txtCardholderName.setBackgroundColor(Color.RED);
            txtCardholderName.requestFocus();

            validFields = false;
        }
        // If all fields are filled correctly sign up the user
        if (validFields) {
            UserBean user = new UserBean();
            user.setFullName(txtFullName.getText().toString());
            user.setEmail(txtEmail.getText().toString());
            user.setLogin(txtLogin.getText().toString());
            user.setPassword(pwdPassword.getText().toString());
            /*user.setPhoneNumber(txtPhoneNumber.getText().toString());
            user.setPostalCode(txtPostalCode.getText().toString());
            user.setTown(txtTown.getText().toString());
            user.setAddress(txtAddress.getText().toString());
            user.setCreditCard(txtCreditCard.getText().toString());
            user.setCvv(txtCVV.getText().toString());
            user.setCardholderName(txtCardholderName.getText().toString());*/

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

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
