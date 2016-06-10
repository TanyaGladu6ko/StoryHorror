package com.story.krokosha.horrorstory.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdView;
import com.story.krokosha.horrorstory.R;
import com.story.krokosha.horrorstory.tools.Adapter;
import com.story.krokosha.horrorstory.tools.Singleton;
import com.story.krokosha.horrorstory.tools.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Field[] raw = R.raw.class.getFields();
        go(raw);
        initToolBar();
        AdView adView = (AdView) findViewById(R.id.adView);
        Utils.startBanner(adView);
    }

    private void go(Field[] raw) {
        new AsyncTask<Field, Void, Void>() {
            @Override
            protected Void doInBackground(Field... params1) {
                String line;

                for (Field aRaw : params1) {
                    try {
                        InputStream inputStream = getResources().openRawResource(aRaw.getInt(0));

                        InputStreamReader inputreader = new InputStreamReader(inputStream);
                        BufferedReader bufferedreader = new BufferedReader(inputreader);
                        line = bufferedreader.readLine();
                        Singleton.getInstance().getTopic().add(line);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter = new Adapter(getApplicationContext());
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
                listView.setDividerHeight(3);
                int n = 0; // прокручиваем до начала
                listView.smoothScrollToPosition(n);
                listView.setOnItemClickListener(new NextPage());

            }
        }.execute(raw);
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.dead_man_hand);
    }

    class NextPage implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), Content.class);
            intent.putExtra("id", position);
            startActivity(intent);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), Setting.class));
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                return true;
            case R.id.aboutProgramm:
                startActivity(new Intent(getApplicationContext(), AboutProgramm.class));
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                return true;
            case R.id.read:
                startActivity(new Intent(getApplicationContext(), ReadStory.class));
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }
}