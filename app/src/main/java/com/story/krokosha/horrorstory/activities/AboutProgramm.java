package com.story.krokosha.horrorstory.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.story.krokosha.horrorstory.R;

public class AboutProgramm extends AppCompatActivity {
    Context context;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_programm);
        initToolBar();
    }

    public void openPlayMarket(View view) {
        Intent rateReviewIntent = new Intent(Intent.ACTION_VIEW);
        rateReviewIntent.setData(Uri.parse(getString(R.string.googleplay_url)));
        startActivity(rateReviewIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), Setting.class));
                return true;
            case R.id.read:
                startActivity(new Intent(getApplicationContext(), ReadStory.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.spider_);
        toolbar.setNavigationIcon(R.drawable.toolbar_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
        );

    }

    public void share(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message) +
                " " + getString(R.string.googleplay_url));
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
    }
}
