package com.story.krokosha.horrorstory.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.story.krokosha.horrorstory.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tvSplash = (TextView) findViewById(R.id.tvSplash);
                            Animation animation = AnimationUtils.loadAnimation(
                                    getApplicationContext(), R.anim.alpha);
                            tvSplash.startAnimation(animation);
                        }
                    });

                    int logoTimer = 0;
                    while (logoTimer < 2500) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}


