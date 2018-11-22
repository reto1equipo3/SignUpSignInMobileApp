package activities.signupsigninmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

import businessLogic.Logic;
import businessLogic.LogicFactory;
import exceptions.*;
import message.MessageType;
import model.UserBean;

/**
 * This is the SignIn Activity. in this activity we start session on our app.
 *
 * @author Leticia e Igor
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener, Thread.UncaughtExceptionHandler {
    public static final Logger LOGGER = Logger.getLogger("activities.signupsigninmobileapp");

    private EditText login;
    private EditText password;
    private Button btnSignIn;
    private TextView hpSignUp;
    private CheckBox remember;
    private Logic logic;
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    /**
     * Method to display the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOGGER.info("SignInActivity::onCreate: Creating activity");
        super.onCreate(savedInstanceState);
        // Set xml view
        setContentView(R.layout.activity_sign_in);

        // Set controls
        login = findViewById(R.id.eTxtLogin);
        password = findViewById(R.id.eTxtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        hpSignUp = findViewById(R.id.txtClickhere);
        remember = findViewById(R.id.chkRememberLogin);
        btnSignIn.setOnClickListener(this);
        hpSignUp.setOnClickListener(this);

        // If remembered user
        LoadLogin();
    }

    @Override
    public void onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "\n" + "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    //Metodo para coger lo que tengas escrito y lo ponga en el campo de login.

    /**
     * Method to load the login saved in a file and put it in the field Login .
     */
    public void LoadLogin() {
        SharedPreferences log = getSharedPreferences("Login", Context.MODE_PRIVATE); //Sirve para guardar de una manera encriptada
        remember.setChecked(log.getBoolean("Checked", false)); // Para cuando inicie sesion el check no este pulsado
        login.setText(log.getString("login", "")); //Coloca en el campo del login lo que haya guardado. En principio esta vacio, y cuando lo guarda guarda con lo que esta escrito.
    }

    /**
     * Method to save the login in a file.
     */
    public void SaveLogin() {
        SharedPreferences log = getSharedPreferences("Login", Context.MODE_PRIVATE);
        //Tenemmos que abrir un "editor" para que escriba el Login
        SharedPreferences.Editor editor = log.edit(); //para escribir el Login
        boolean r = remember.isChecked();  // Si el checkBox esta pulsado se declara el boolean
        String l = login.getText().toString(); //Lo que coge del campo login
        editor.putBoolean("checked", r); //Asi pone el campo del checbox pulsado
        editor.putString("login", l); //Para coger lo que eta escrito en el login y lo escribe
        editor.commit(); //para guardar. Si no no guarda nada.

    }

    /**
     * Method to go to the UserViewActivity when you check the data entered when you press the SignIn button.
     * also when we click in the hpNotRegister, SignUpActivity opens.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        // Sign in button
        if (btnSignIn.isPressed()) {
            LOGGER.info("SignInActivity::onClick: Sign in button pressed.");
            try {
                // Set the user to sign in
                UserBean user = new UserBean();
                user.setLogin(login.getText().toString());
                user.setPassword(password.getText().toString());

                // Get the logic
                LogicFactory logicFactory = new LogicFactory();
                logic = logicFactory.createImplementation();

                // Create a connection thread
                ConnectionThread connectionThread = new ConnectionThread();
                connectionThread.setUser(user);
                connectionThread.setMessage(MessageType.SIGN_IN);
                connectionThread.setLogic(logic);

                // Set custom exception handler
                connectionThread.setUncaughtExceptionHandler(this::uncaughtException);

                // Start the thread
                connectionThread.start();
                connectionThread.join();

                // Get the sign in user
                user = connectionThread.getUser();

                Intent intentUserView = new Intent(SignInActivity.this, UserViewActivity.class);
                intentUserView.putExtra("User", user);
                startActivity(intentUserView);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, "SignInActivity::onClick: {0}", ex.getCause());
                Toast.makeText(this, "Connections problems. Please try again.", Toast.LENGTH_LONG).show();
            }
        }
        // Sign up hyperlink
        if (hpSignUp.isPressed()) {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e.getCause() instanceof LoginNotExistingException) {
            Toast.makeText(this, "Login does not exist.", Toast.LENGTH_LONG).show();
        } else if (e.getCause() instanceof BadPasswordException) {
            Toast.makeText(this, "Password is wrong.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong. Try again", Toast.LENGTH_LONG).show();
        }
    }

}
