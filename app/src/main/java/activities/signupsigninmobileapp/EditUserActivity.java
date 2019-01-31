package activities.signupsigninmobileapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Beans.UserBean;


public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {

    protected final int MAX_LENGTH = 255;
    protected final int MAX_LENGTH_FULLNAME = 50;
    protected final int MAX_LENGTH_EMAIL = 50;
    protected final int MAX_LENGTH_LOGIN = 20;

    private Button btnSave;
    private EditText login;
    private EditText email;
    private EditText name;
    private EditText phoneNumber;
    private EditText postalCode;
    private EditText address;
    private EditText town;
    private UserBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //Ventana siempre en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        login = findViewById(R.id.eTxtLoginEdit);
        email = findViewById(R.id.eTxtEmailEdit);
        name = findViewById(R.id.eTxtNameEdit);
        phoneNumber = findViewById(R.id.eTxtPhoneNumberEdit);
        postalCode = findViewById(R.id.eTxtPostalCodeEdit);
        //town = findViewById(R.id.eTxtTownEdit);
        //address = findViewById(R.id.eTxtAddressEdit);
        btnSave = findViewById(R.id.btnSaveEdit);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(btnSave.isPressed()) {

            Boolean validFields = true;

            if (validFields) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Save changes");
                builder.setMessage("Do you want to save the changes?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO Guardar cambios enviandole los datos nuevos a la base de datos para que abra el siguiente fragment con los datos ya actualizados

                        /*user.setLogin(login.getText().toString());
                        user.setFullName(name.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setPhoneNumber(phoneNumber.getText().toString());
                        user.setPostalCode(postalCode.getText().toString());
                        user.setTown(address.getText().toString());
                        user.setAddress(address.getText().toString());
                        user.setPostalCode(postalCode.getText().toString());*/

                        //TODO Actualizar los datos del usuario

                        FragmentManager fragmentManager;
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction;
                        fragmentTransaction = fragmentManager.beginTransaction();

                        UserViewFragment userViewFragment = new UserViewFragment();
                        fragmentTransaction.replace(R.id.fragment, userViewFragment);

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // No pasa nada. Se cierra la ventana emergente de confirmación
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    //TODO Validar todos los datos metidos por el usuario antes de guardar los cambios

}