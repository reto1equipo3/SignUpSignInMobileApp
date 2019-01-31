package activities.signupsigninmobileapp;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.logging.Logger;

import Beans.UserBean;
import activities.signupsigninmobileapp.R;


public class PasswordChangeActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final Logger LOGGER = Logger.getLogger("ui.controllers");

    protected final int MAX_LENGTH_PASSWORD = 15;

    private EditText currentPassword;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button btnSave;
    private UserBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        //Ventana siempre en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        currentPassword = findViewById(R.id.eTxtCurrentPassword);
        newPassword = findViewById(R.id.eTxtNewPassword);
        confirmNewPassword = findViewById(R.id.eTxtConfirmNewPassword);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        Boolean validFields = true;
        if(btnSave.isPressed()){
            /*try{
                validatePassword(newPassword.getText().toString(), confirmNewPassword.getText().toString());
            }catch(IllegalArgumentException e){
                LOGGER.log(Level.SEVERE, "Sign Up controller::handleSignUpAction: {0}", e.getMessage());

                newPassword.requestFocus();

                validFields = false;
            }*/
            /*if(!currentPassword.equals(user.getPassword().toString())){
                Toast.makeText(this.getActivity(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                validFields = false;
            }*/
            /*if(validFields){
                user.setPassword(newPassword.getText().toString());
                Timestamp lastPwdChange = new Timestamp(System.currentTimeMillis());
                user.setLastPasswordChange(lastPwdChange);
            }*/

            //TODO Cambiar esto

            FragmentManager fragmentManager;
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = fragmentManager.beginTransaction();

            UserViewFragment userViewFragment = new UserViewFragment();
            fragmentTransaction.replace(R.id.fragment, userViewFragment);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }
}
