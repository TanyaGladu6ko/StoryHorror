package com.story.krokosha.horrorstory.tools;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AdapterForReadStory extends BaseAdapter {
    private Context context;

    public AdapterForReadStory(Context context) {
        this.context = context;
       SingletonReadStory.getInstance().getStoryRead();
    }

    @Override
    public int getCount() {

        return  SingletonReadStory.getInstance().getStoryRead().size();
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
        tv.setText(SingletonReadStory.getInstance().getStoryRead().get(position));
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);
        return tv;
    }
}