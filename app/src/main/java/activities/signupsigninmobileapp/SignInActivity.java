package activities.signupsigninmobileapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import businessLogic.Logic;
import businessLogic.LogicFactory;
import exceptions.BadPasswordException;
import exceptions.DatabaseException;
import exceptions.LoginNotExistingException;

/**
 * This is the SignIn Activity. in this activity we start session on our app.
 *
 * @author Leticia e Igor
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener,Thread.UncaughtExceptionHandler{

    private EditText login;
    private EditText password;
    private Button btnSignIn;
    private TextView hpNotRegister;
    private CheckBox remember;
    private UserBean user;
    private Logic logicController;


    /**
     * Method to display the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        login = findViewById(R.id.eTxtLogin);
        password = findViewById(R.id.eTxtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        hpNotRegister = findViewById(R.id.txtClickhere);
        remember = findViewById(R.id.chkRememberLogin);
        btnSignIn.setOnClickListener(this);
        hpNotRegister.setOnClickListener(this);

        LoadLogin();
        logicController = LogicFactory.createLogicImplementation("USER_CLIENT_TYPE");
    }

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "\n" + "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    //Metodo para coger lo que tengas escrito y lo ponga en el campo de login.
    /**
     * Method to load the login saved in a file and put it in the field Login .
     */
    public void LoadLogin() {
        SharedPreferences log= getSharedPreferences("Login", Context.MODE_PRIVATE); //Sirve para guardar de una manera encriptada
        remember.setChecked(log.getBoolean("Checked", false)); // Para cuando inicie sesion el check no este pulsado
        login.setText(log.getString("login", "")); //Coloca en el campo del login lo que haya guardado. En principio esta vacio, y cuando lo guarda guarda con lo que esta escrito.
    }

    /**
     * Method to save the login in a file.
     */
    public void SaveLogin(){
        SharedPreferences log= getSharedPreferences("Login", Context.MODE_PRIVATE);
        //Tenemmos que abrir un "editor" para que escriba el Login
        SharedPreferences.Editor editor= log.edit(); //para escribir el Login
        boolean r = remember.isChecked();  // Si el checkBox esta pulsado se declara el boolean
        String l = login.getText().toString(); //Lo que coge del campo login
        editor.putBoolean("checked" , r); //Asi pone el campo del checbox pulsado
        editor.putString("login", l); //Para coger lo que eta escrito en el login y lo escribe
        editor.commit(); //para guardar. Si no no guarda nada.

    }




    /**
     * Method to go to the UserViewActivity when you check the data entered when you press the SignIn button.
     * also when we click in the hpNotRegister, SignUpActivity opens.
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (btnSignIn.isPressed()) {


            if(login.getText().toString().equals("") && password.getText().toString().equals("")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Warning").setMessage("Login or password are empty").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast t = Toast.makeText(SignInActivity.this, "Login and password are empty", Toast.LENGTH_SHORT);
                        t.show();

                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeOptionsMenu();
                    }
                });
                builder.show();
            }

            validateUser();

            if(remember.isChecked()){
                SaveLogin();
            }
            Intent intent = new Intent(SignInActivity.this, UserViewActivity.class);
            startActivity(intent);

        }

        if (hpNotRegister.isPressed()) {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);

        }

    }

    private UserBean validateUser(){
        UserBean returnUser = null;
        try{
            UserBean u = new UserBean(login.getText().toString(),password.getText().toString());

            logicController.signIn(user);
            ConnectionThread thread = new ConnectionThread(u,1, logicController);
            thread.setUncaughtExceptionHandler(this::uncaughtException);
            thread.start();
            thread.join();
            user = thread.getUser();



        }catch(LoginNotExistingException e){
            login.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorError));
            Toast.makeText(this, "Login is wrong", Toast.LENGTH_LONG).show();

        }catch(BadPasswordException e){
            password.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorError));
            Toast.makeText(this, " Password is wrong", Toast.LENGTH_LONG).show();

        } catch (InterruptedException ex) {
            Toast.makeText(this,"Error connecting with the dataBase", Toast.LENGTH_LONG).show();

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return returnUser;
    }


    /**
     * method that catches exceptions
     *  @param
     * @param
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e.getCause() instanceof LoginNotExistingException) {

        } else if (e.getCause() instanceof BadPasswordException) {

        } else {

        }
    }

}
