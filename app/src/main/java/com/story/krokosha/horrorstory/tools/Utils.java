package com.story.krokosha.horrorstory.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.story.krokosha.horrorstory.R;

public class Utils {
    public static final String ARG_TRIGGER = "trigger";
    public static final int ARG_TRIGGER_VALUE = 2;
    public static final boolean IS_ADMOB_VISIBLE = true;
    public static final boolean IS_ADMOB_IN_DEBUG = false;
    public static final String ARG_ADMOB_PREFERENCE = "admobPreference";

    public static void saveIntPreferences(Context ctx, String key, String param, int value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(param, value);
        editor.apply();
    }

    public static int loadIntPreferences(Context ctx, String key, String param) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(key, Context.MODE_PRIVATE);
        if (key.equals(ARG_ADMOB_PREFERENCE)) {
            return sharedPreferences.getInt(param, 1);
        } else {
            return sharedPreferences.getInt(param, 1000);
        }
    }

    // Method to check admob visibility
    public static boolean admobVisibility(AdView ad, boolean isInDebugMode) {
        if (isInDebugMode) {
            ad.setVisibility(View.VISIBLE);
            return true;
        } else {
            ad.setVisibility(View.GONE);
            return false;
        }
    }

    public static void startBanner(AdView ad) {
        AdRequest adRequest;
        if (admobVisibility(ad, IS_ADMOB_VISIBLE)) {
            if (IS_ADMOB_IN_DEBUG) {
                adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
            } else {
                adRequest = new AdRequest.Builder().build();
            }
            ad.loadAd(adRequest);
        }
    }

    public static class SyncShowAd extends AsyncTask<Void, Void, Void> {

        private AdRequest interstitialAdRequest;
        private InterstitialAd interstitialAd;
        private int interstitialTrigger;
        private Context context;

        public SyncShowAd(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(context.getResources().getString(R.string.adFullId));
            interstitialTrigger = loadIntPreferences(context,
                    ARG_ADMOB_PREFERENCE, ARG_TRIGGER);

            if (interstitialTrigger == ARG_TRIGGER_VALUE) {
                if (IS_ADMOB_IN_DEBUG) {
                    interstitialAdRequest = new AdRequest.Builder()
                            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                            .build();
                } else {
                    interstitialAdRequest = new AdRequest.Builder().build();
                }
                saveIntPreferences(context, ARG_ADMOB_PREFERENCE, ARG_TRIGGER, 1);
            } else {
                saveIntPreferences(context, ARG_ADMOB_PREFERENCE, ARG_TRIGGER,
                        (interstitialTrigger + 1));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (interstitialTrigger == ARG_TRIGGER_VALUE) {
                interstitialAd.loadAd(interstitialAdRequest);
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                    }

                    @Override
                    public void onAdLoaded() {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        }
                    }
                });
            }
        }
    }
}
