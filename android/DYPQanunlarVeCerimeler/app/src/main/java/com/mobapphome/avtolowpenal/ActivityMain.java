package com.mobapphome.avtolowpenal;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobapphome.avtolowpenal.avtolow.ActivityALStart;
import com.mobapphome.avtolowpenal.other.Constants;
import com.mobapphome.avtolowpenal.penal.ActivityPStart;
import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.tools.LocaleUpdater;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;

import io.fabric.sdk.android.Fabric;

//Eslinde activity ActionBarActivity-ni extend etmelidir. Butun versiyalarda Appcompat v7 -ni 
//support etmek ucun. Amma Android 2.x -da action bar invisible ede bilmediyim ucun mecbur olub
//Activity-ni extend etmisem.

//Bunu islemesi ucun Android 4.x larda islemek ucun gerek styllara action bar invisible yazasan
//Amma Android 2.x a ehtiyyac yoxdur.
//Vaxt tapib Android 2.x - de appcompat ile actionbari invisible etmeyi tapmaq lazimdir

public class ActivityMain extends FragmentActivity implements View.OnClickListener, MAHAdsDlgExit.MAHAdsDlgExitListener  {

    AdView adView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fabric------------------------------------------------
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());


        //Localization for mah
        LocaleUpdater.updateLocale(this, "az");

        Log.i(Constants.TAG_DYP_PENAL_LOG, "savedInstanceState = " + savedInstanceState);

        if (savedInstanceState == null) {
            // For MAHAds init
            MAHAdsController.init(this,
                    "https://project-943403214286171762.firebaseapp.com/mah_ads_dir/",
                    "aze_gen_prg_version.json",
                    "aze_gen_prg_list.json");
            // METHOD 1

            // For MAHUpdater init
            MAHUpdaterController.init(this,
                    "https://project-943403214286171762.firebaseapp.com/mah_android_updater_dir/mah_android_updater_dyp_qanunlar_ve_cerimeler.json");
            //MAHUpdaterController.testRestricterDlg(this);
            //MAHUpdaterController.testUpdaterDlg(this);
            // METHOD 1
        }

        findViewById(R.id.btnLow).setOnClickListener(this);
        findViewById(R.id.btnPenal).setOnClickListener(this);


        //For AdMob--------------------------------------------------------------------------------
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner_unit_id));

        final LinearLayout layout = (LinearLayout) findViewById(R.id.admob);
        layout.setVisibility(View.GONE);
        layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("D4B5D733140370B86154E86D1D54E7F7") //galaxy s4
                .addTestDevice("154222674FFA1A7378D5E13BD636F597")//768 Genimotion Emuliator
                .addTestDevice("D76C969E1CDC600B5CDDC34A67F7E148")//600 Genimotion emuliator
                .addTestDevice("7D74307340040DDA1D9A190FFCD6D510")//480 Genimotion
                .addTestDevice("ACD7FEE7E5F36FFCDCCCF3852177E4D4")//768 Genimotion
                .build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
                Log.i("Test", "Ad loaded");
                layout.setVisibility(View.VISIBLE);
            }
        });


        mInterstitialAd = Utils.requestNewInterstitial(this, mInterstitialAd);


        //For caution view
        ((TextView) findViewById(R.id.tvCautionDetailed)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.tvAuthor)).setMovementMethod(LinkMovementMethod.getInstance());
        findViewById(R.id.ivForkMeOnGithub).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.btnLow) {
            Intent intSignAct = new Intent(ActivityMain.this, ActivityALStart.class);
            startActivity(intSignAct);
        } else if (v.getId() == R.id.btnPenal) {
            Intent intSignAct = new Intent(ActivityMain.this, ActivityPStart.class);
            startActivity(intSignAct);
        } else if (v.getId() == R.id.ivForkMeOnGithub) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.lib_github_url)));
            startActivity(browserIntent);
        }

        Log.i(Constants.TAG_DYP_PENAL_LOG, " interstatial is loaded in out = " + mInterstitialAd.isLoaded());
        Utils.showInterstitial(ActivityMain.this, mInterstitialAd, Constants.INTERSTITAIL_FREE_STEPS_MAIN, null);

    }

    @Override
    public void onBackPressed() {
        MAHAdsController.callExitDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MAHUpdaterController.end();

        if (adView != null) {
            adView.destroy();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                Intent intAboutAct = new Intent(ActivityMain.this, ActivityAbout.class);
                startActivity(intAboutAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onYes() {

    }

    @Override
    public void onNo() {

    }

    @Override
    public void onExitWithoutExitDlg() {

    }

    @Override
    public void onEventHappened(String eventStr) {

    }

}
