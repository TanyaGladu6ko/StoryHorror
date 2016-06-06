package com.story.krokosha.horrorstory.tools;

import java.util.ArrayList;

/**
 * Created by Tanya on 005 05.06.16.
 */
public class SingletonReadStory {

    private ArrayList<String> storyRead;
    private static volatile SingletonReadStory readStory;

    public SingletonReadStory() {
        storyRead = new ArrayList<>();
    }

    public ArrayList<String> getStoryRead() {
        return storyRead;
    }

    public static SingletonReadStory getInstance() {
        if (readStory == null) {
            readStory = new SingletonReadStory();
        }
        return readStory;
    }

}
