package com.muni.in.view.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.muni.in.R;
import com.muni.in.data.SharedPreference;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.service.Client;
import com.muni.in.service.listener.ResponseListener;
import com.muni.in.utils.AlertDialog;
import com.muni.in.view.login.LoginActivity;
import com.muni.in.view.restaurant_onboard.MapFragment;
import com.muni.in.view.restaurant_onboard.ResDetailsFragment;
import com.muni.in.view.restaurant_onboard.RestaurantRegFragment;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ResponseListener,AlertDialog.alertCallback {

    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    private SharedPreference sharedPreferences;
    private String TAG = getClass().getName();
    private RecyclerView restaurantRecycleView;
    public RestaurantAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = new SharedPreference(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        restaurantRecycleView = (RecyclerView) findViewById(R.id.restaurantRecycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        restaurantRecycleView.setLayoutManager(mLayoutManager);
        restaurantRecycleView.setHasFixedSize(true);
        restaurantRecycleView.setItemAnimator(new DefaultItemAnimator());

        //showProgress(true);
        new Client(this, this).getRestaurants(sharedPreferences.getAccessToken());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }


    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        if(frag instanceof RestaurantRegFragment){
            AlertDialog dialog = new AlertDialog(this,"Alert",
                    "Are you want to exit from on Board screen",this);
            dialog.show();
            return;
            //Toast.makeText(this,"home close onclick",Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

            if (backStackCount >= 1) {
                getSupportFragmentManager().popBackStack();
                // Change to hamburger icon if at bottom of stack
                if (backStackCount == 1) {
                    showUpButton(false,Toggle.CLOSE);
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        //invalidateOptionsMenu();
        //MenuItem item = menu.findItem(R.id.action_settings);
        //item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.action_search) {
            Toast.makeText(this, "Not Available", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.action_add) {
            //Toast.makeText(this, "Add Res", Toast.LENGTH_LONG).show();
            replaceFragment();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            sharedPreferences.clear();
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            //overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment() {
        /**
         * Your fragment replacement logic goes here
         * e.g.
         * FragmentTransaction ft = getFragmentManager().beginTransaction();
         * String tag = "MyFragment";
         * ft.replace(R.id.content, MyFragment.newInstance(tag), tag).addToBackStack(null).commit();
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_reverse_exit, R.anim.slide_reverse_enter);
        transaction.replace(R.id.frag_container, new RestaurantRegFragment(), "Myfrag").addToBackStack(null).commit();

        // The part that changes the hamburger icon to the up icon
        showUpButton(true,Toggle.CLOSE);
    }

    private void resolveUpButtonWithFragmentStack() {
        showUpButton(getSupportFragmentManager().getBackStackEntryCount() > 0,Toggle.BACK);
    }

    public void showUpButton(boolean show,Toggle value) {
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (show) {
            // Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(value.equals(Toggle.CLOSE))
                toggle.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
            else
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
    }


    @Override
    public void onSuccess(String listener, String response) {

    }

    @Override
    public void onSuccess(String listener, final List<Restaurant> response) {
        //showProgress(false);
        //Log.d(TAG, "" + response);
        adapter = new RestaurantAdapter(HomeActivity.this,response, new RecycleViewItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Restaurant res = response.get(position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_reverse_exit, R.anim.slide_reverse_enter);
                transaction.add(R.id.frag_container, ResDetailsFragment.newInstance(res,
                        ""+position), "ResDetails").addToBackStack(null).commit();
                //Toast.makeText(HomeActivity.this,"Adapter pos"+res.getRestaurantName(),Toast.LENGTH_SHORT).show();
            }
        });
        restaurantRecycleView.setAdapter(adapter);

    }



    @Override
    public void onFailure(String message) {
        //showProgress(false);

    }

    @Override
    public void alert(DialogInterface dialog) {
        dialog.dismiss();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

            if (backStackCount >= 1) {
                getSupportFragmentManager().popBackStack();
                // Change to hamburger icon if at bottom of stack
                if (backStackCount == 1) {
                    showUpButton(false,Toggle.CLOSE);
                }
            } else {
                super.onBackPressed();
            }
        }
    }
}
