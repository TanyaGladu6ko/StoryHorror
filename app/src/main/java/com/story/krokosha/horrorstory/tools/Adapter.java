package com.story.krokosha.horrorstory.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.story.krokosha.horrorstory.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    String[] arrayList;

    public Adapter(Context context) {
        this.context = context;
        Singleton.getInstance().getTopic();
    }

    @Override
    public int getCount() {

        return Singleton.getInstance().getTopic().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);

        tv.setText(Singleton.getInstance().getTopic().get(position));
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);

        return tv;
    }

}

