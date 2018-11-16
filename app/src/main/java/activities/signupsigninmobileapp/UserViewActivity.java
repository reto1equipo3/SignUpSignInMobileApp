package activities.signupsigninmobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class UserViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserViewFragment.OnFragmentInteractionListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(false);
        Fragment f = new UserViewFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main , f).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Warning").setMessage("Are you sure that you want to exit?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(UserViewActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    closeOptionsMenu();
                }
            });
            builder.show();
            //super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        boolean fragmentSelect = false;

        switch (item.getItemId()) {
            case R.id.logOutOption:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Warning").setMessage("Are you sure that you want to exit?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UserViewActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeOptionsMenu();
                    }
                });
                builder.show();
                break;

            case R.id.userDataOption:
                fragment = new UserViewFragment();
                fragmentSelect = true;

                break;

            case R.id.nav_share:
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/pain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"My app");
                startActivity(Intent.createChooser(shareIntent,"Share via"));
                break;
        }

        if(fragmentSelect==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment ).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
