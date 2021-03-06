package com.speedOMeter.speedOMeter;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

/**
 * To use this function you have to do the following steps:
 * - extend this class
 * - execute createDrawer and pass as parameter the id of the layout
 */

public abstract class ActivityDrawer extends AppCompatActivity {
    protected void createDrawer(int contentLayoutId) {
        setContentView(R.layout.activity_drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout content = (FrameLayout) findViewById(R.id.drawer_layout_content);
        getLayoutInflater().inflate(contentLayoutId, content);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                Intent intent = null;
                if (id == R.id.nav_track_speed) {
                    intent = new Intent(getApplicationContext(), ActivityTracking.class);
                } else if (id == R.id.nav_settings) {
                    intent = new Intent(getApplicationContext(), ActivitySettings.class);
                } else if (id == R.id.nav_graph) {
                    intent = new Intent(getApplicationContext(), ActivityGraph.class);
                }
                if(intent != null) {
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
