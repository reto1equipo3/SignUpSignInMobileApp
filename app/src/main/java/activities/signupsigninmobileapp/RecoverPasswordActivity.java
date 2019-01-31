package activities.signupsigninmobileapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import activities.signupsigninmobileapp.R;

public class RecoverPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final Logger LOGGER = Logger.getLogger("ui.controllers");

    private EditText eTxtLogin;
    private Button btnRecoverPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        //Ventana siempre en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        eTxtLogin = findViewById(R.id.eTxtLogin);
        btnRecoverPwd = findViewById(R.id.btnRecoverPwd);
        btnRecoverPwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Boolean isValid = true;
        if(btnRecoverPwd.isPressed()){

            //TODO Comprobar que el login metido por el usuario está en la base de datos.

            if(isValid){

                //TODO Generar contraseña aleatoria de como máximo 10 caracteres en el lado servidor y que se envíe por correo

            }

        }
    }

}
