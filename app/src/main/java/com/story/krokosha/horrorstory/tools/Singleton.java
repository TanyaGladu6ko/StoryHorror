package com.story.krokosha.horrorstory.tools;

import java.util.ArrayList;

public class Singleton {

    private ArrayList<String> topic;
    private static volatile Singleton instanceForTopic;

    public Singleton() {
        topic = new ArrayList<>();
    }

    public ArrayList<String> getTopic() {
        return topic;
    }

    public static Singleton getInstance() {
        if (instanceForTopic == null) {
            instanceForTopic = new Singleton();
        }
        return instanceForTopic;
    }

}
