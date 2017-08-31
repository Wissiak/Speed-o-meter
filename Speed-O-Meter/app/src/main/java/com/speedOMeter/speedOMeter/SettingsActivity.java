package com.speedOMeter.speedOMeter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        this.createDrawer();
    }
}
