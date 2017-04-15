package com.story.krokosha.horrorstory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.ads.AdView;
import com.story.krokosha.horrorstory.R;
import com.story.krokosha.horrorstory.tools.AdapterForReadStory;
import com.story.krokosha.horrorstory.tools.Utils;

public class ReadStory extends AppCompatActivity {

    ListView listview;
    AdapterForReadStory adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);

        adapter = new AdapterForReadStory(getApplicationContext());
        listview = (ListView) findViewById(R.id.listView2);
        listview.setAdapter(adapter);
        initToolBar();

        AdView adView2 = (AdView) findViewById(R.id.adView2);
        Utils.startBanner(adView2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);

    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.reaper);
        toolbar.setNavigationIcon(R.drawable.toolbar_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(R.anim.open_next, R.anim.close_main);

                    }
                });
    }
}

