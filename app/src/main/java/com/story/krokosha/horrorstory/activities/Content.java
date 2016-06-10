package com.story.krokosha.horrorstory.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.story.krokosha.horrorstory.R;
import com.story.krokosha.horrorstory.tools.CallBackDialog;
import com.story.krokosha.horrorstory.tools.Singleton;
import com.story.krokosha.horrorstory.tools.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Content extends AppCompatActivity {
    private Context context;
    private String data;
    private WebView mWebView;
    private WebSettings settings;
    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        context = getApplicationContext();

        position = getIntent().getIntExtra("id", 0);
        Field[] ID_Fields = R.raw.class.getFields();
        int[] resArray = new int[ID_Fields.length];

        for (int i = 0; i < ID_Fields.length; i++) {
            try {
                resArray[i] = ID_Fields[i].getInt(null);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int result = resArray[position];
        data = readTextFile(context, result);
        mWebView = (WebView) findViewById(R.id.textView);
        String htmlText = "<html><body style=\"text-align:justify\">" + data + "</body></Html>";
        settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        mWebView.loadDataWithBaseURL(data, htmlText, "text/html", "en_US", null);
        initToolBar();

        new Utils.syncShowAd(context).execute();
    }

    // reading text of raw folder
    public String readTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(inputreader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            boolean firstLine = true;
            while ((line = bufferedreader.readLine()) != null) {
                // doesn't read the first line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return stringBuilder.toString();
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Singleton.getInstance().getTopic().get(position));
        toolbar.setLogo(R.drawable.reaper);
        toolbar.setNavigationIcon(R.drawable.toolbar_back);
        toolbar.setNavigationOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.showDialog(Content.this, callBackDialog);
        }
    };

    CallBackDialog callBackDialog = new CallBackDialog(){
        @Override
        public void onSuccess() {

        }

        @Override
        public void onRefused() {
            finish();
            overridePendingTransition(R.anim.open_main, R.anim.close_next);
        }
    };

//    private void showDialog(final Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(context.getResources().getString(R.string.mark_as_read))
//                .setIcon(R.drawable.dead_man_hand)
//                .setCancelable(false)
//                .setNegativeButton(context.getResources().getString(R.string.yes),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                String a = Singleton.getInstance().getTopic().get(position);
//                                SingletonReadStory.getInstance().getStoryRead().add(a);
//                                deleteInArray();
//                                saveArray(SingletonReadStory.getInstance().getStoryRead(), context);
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                overridePendingTransition(R.anim.open_next, R.anim.close_main);
//                            }
//                        })
//                .setPositiveButton(context.getResources().getString(R.string.no),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                                finish();
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), Setting.class));
                return true;
            case R.id.aboutProgramm:
                startActivity(new Intent(getApplicationContext(), AboutProgramm.class));
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
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
        }

        String regular = prefs.getString(getString(R.string.pref_style), "");
        settings.setTextSize(WebSettings.TextSize.NORMAL);

        if (regular.contains("Крошечный")) {
            settings.setTextSize(WebSettings.TextSize.SMALLER);
        }
        if (regular.contains("Нормальный")) {
            settings.setTextSize(WebSettings.TextSize.NORMAL);
        }
        if (regular.contains("Крупный")) {
            settings.setTextSize(WebSettings.TextSize.LARGER);
        }
        if (regular.contains("Очень крупный")) {
            settings.setTextSize(WebSettings.TextSize.LARGEST);
        }

        String theme = prefs.getString(getString(R.string.theme_style), "");
        String text;
        if (theme.contains("День")) {
            text = "<html><head>"
                    + "<style type=\"text/css\">body{text-align:justify; color: #000000; " +
                    "background-color: #FFFFFF;}"
                    + "</style></head>"
                    + "<body>"
                    + data
                    + "</body></html>";
            mWebView.loadDataWithBaseURL(data, text, "text/html", "en_US", null);

        }
        if (theme.contains("Серость")) {
            text = "<html><head>"
                    + "<style type=\"text/css\">body{text-align:justify; color: #000000;" +
                    " background-color: #A9A9A9;}"
                    + "</style></head>"
                    + "<body>"
                    + data
                    + "</body></html>";
            mWebView.loadDataWithBaseURL(data, text, "text/html", "en_US", null);
        }
        if (theme.contains("Ночь")) {
            text = "<html><head>"
                    + "<style type=\"text/css\">body{text-align:justify; color: #FFFFFF; " +
                    "background-color: #000000;}"
                    + "</style></head>"
                    + "<body>"
                    + data
                    + "</body></html>";

            mWebView.loadDataWithBaseURL(data, text, "text/html", "en_US", null);
        }
        if (theme.contains("Оливковый")) {
            text = "<html><head>"
                    + "<style type=\"text/css\">body{text-align:justify; color: #000000; " +
                    "background-color: #808000;}"
                    + "</style></head>"
                    + "<body>"
                    + data
                    + "</body></html>";
            mWebView.loadDataWithBaseURL(data, text, "text/html", "en_US", null);
        }
    }

    @Override
    public void onBackPressed() {
        Utils.showDialog(Content.this, callBackDialog);
    }

    private void deleteInArray() {
        Singleton.getInstance().getTopic().remove(position);
        finish();
    }

    public boolean saveArray(ArrayList<String> array, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("_size", array.size());
        for (int i = 0; i < array.size(); i++)
            editor.putString("_" + i, array.get(i));
        return editor.commit();
    }
}