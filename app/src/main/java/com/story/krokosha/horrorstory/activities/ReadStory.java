package com.story.krokosha.horrorstory.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.story.krokosha.horrorstory.R;
import com.story.krokosha.horrorstory.tools.Adapter;
import com.story.krokosha.horrorstory.tools.AdapterForReadStory;
import com.story.krokosha.horrorstory.tools.Singleton;
import com.story.krokosha.horrorstory.tools.SingletonReadStory;

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
                    }
                });
    }
}

