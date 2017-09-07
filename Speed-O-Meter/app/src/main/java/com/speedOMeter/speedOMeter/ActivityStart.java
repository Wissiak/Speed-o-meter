package com.speedOMeter.speedOMeter;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

public class ActivityStart extends AppCompatActivity {
    @Override
    public void onStart() {
        super.onStart();

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivityStart.this, ActivityTracking.class);
                startActivity(intent);
                finish();
            }
        }, 2500);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_layout);

        new SharedPreferenceHandler(getApplicationContext()).reset();
    }
}
