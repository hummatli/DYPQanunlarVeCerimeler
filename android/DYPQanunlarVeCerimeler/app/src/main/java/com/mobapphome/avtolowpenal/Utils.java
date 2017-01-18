package com.mobapphome.avtolowpenal;

import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mobapphome.avtolowpenal.other.Constants;

public class Utils {

    //Progress bar  -------------------------------
    public static ProgressDialog showProgressDlg(Context context) {
        ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        return progressDialog;
    }

    public static void closeProgressDlg(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }


    //My DBHelper ------------------------
    public static SqlLiteHelper getMyDbHelper(Context context, SqlLiteHelper myDbHelper) {
        if (myDbHelper == null) {
            myDbHelper = new SqlLiteHelper(context);
        }
        return myDbHelper;
    }

    public static void closeMyDbHelper(SqlLiteHelper myDbHelper){
        if (myDbHelper != null) {
            myDbHelper.closeDB();
        }
    }

    //Interstitial ------------------------------
    public static InterstitialAd requestNewInterstitial(Context context, InterstitialAd mInterstitialAd) {
        Log.i(Constants.TAG_DYP_PENAL_LOG, "Request new interstatial");

        //Create an interstitial
        if(mInterstitialAd == null){
            Log.i(Constants.TAG_DYP_PENAL_LOG, "Interstatial created");
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(context.getString(R.string.admob_interstitial_unit_id));
        }

        if (!mInterstitialAd.isLoaded()) {
            Log.i(Constants.TAG_DYP_PENAL_LOG, "Interstatial loaded");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("D4B5D733140370B86154E86D1D54E7F7") //galaxy s4
                    .addTestDevice("154222674FFA1A7378D5E13BD636F597")//768 Genimotion Emuliator
                    .addTestDevice("D76C969E1CDC600B5CDDC34A67F7E148")//600 Genimotion emuliator
                    .addTestDevice("7D74307340040DDA1D9A190FFCD6D510")//480 Genimotion
                    .addTestDevice("ACD7FEE7E5F36FFCDCCCF3852177E4D4")//768 Genimotion

                    .build();

            mInterstitialAd.loadAd(adRequest);
        }
        return mInterstitialAd;
    }


    public static void showInterstitial( final Context context, final InterstitialAd mInterstitialAd, int freeSteps, final MAHInterstitialListiner interstitialListiner) {
        final SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        int currentIntersitailCount = prefs.getInt(Constants.PREF_KEY_INTERSTITIAL_COUNT, 0);

        Log.i(Constants.TAG_DYP_PENAL_LOG, "showInterstitial interstitial count = " + currentIntersitailCount);

        if (mInterstitialAd.isLoaded()) {
            Log.i(Constants.TAG_DYP_PENAL_LOG, " interstatial is loaded");

            if (currentIntersitailCount < freeSteps) {
                if (interstitialListiner != null) {
                    interstitialListiner.onDoActionNotOppened();
                }
                increaseInterstatialCount(prefs, currentIntersitailCount);
                return;
            } else {
                Log.i(Constants.TAG_DYP_PENAL_LOG, " interstatial is opened");
                mInterstitialAd.setAdListener(new AdListener() {

                    @Override
                    public void onAdOpened() {
                        //increase shared value
                        Log.i(Constants.TAG_DYP_PENAL_LOG, " interstatial is opened");
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdClosed() {
                        requestNewInterstitial(context, mInterstitialAd);
                        Log.i(Constants.TAG_DYP_PENAL_LOG, " interstatial is closed");
                        if (interstitialListiner != null) {
                            interstitialListiner.onDoActionAfterClosed();
                        }
                        //currentIntersitailCount = 0;
                        setInterstatialCount(prefs, 0);
                    }
                });
                mInterstitialAd.show();
            }
        } else {
            Log.i(Constants.TAG_DYP_PENAL_LOG, " interstatial is not loaded");
            if (interstitialListiner != null) {
                interstitialListiner.onDoActionNotOppened();
            }
            requestNewInterstitial(context, mInterstitialAd);
            increaseInterstatialCount(prefs, currentIntersitailCount);
        }
    }

    static private void increaseInterstatialCount(SharedPreferences prefs, int interstitialCount) {
        setInterstatialCount(prefs, interstitialCount+ 1);
    }

    static private void setInterstatialCount(SharedPreferences prefs, int interstitialCount) {
        prefs.edit().putInt(Constants.PREF_KEY_INTERSTITIAL_COUNT, interstitialCount).apply();
    }


    public static String htmlToText(String str){
        String retStr = Pattern.compile("\\<.*?>", Pattern.DOTALL).matcher(str).replaceAll("");
        retStr = NumericCharacterReference.decode(retStr, ' ');
        return retStr;
	}
}
