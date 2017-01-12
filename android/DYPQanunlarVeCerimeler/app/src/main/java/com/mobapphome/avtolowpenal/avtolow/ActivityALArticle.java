package com.mobapphome.avtolowpenal.avtolow;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mobapphome.avtolowpenal.ActivityAbout;
import com.mobapphome.avtolowpenal.CustomStatusBarDecorator;
import com.mobapphome.avtolowpenal.R;
import com.mobapphome.avtolowpenal.other.ALArticle;
import com.mobapphome.avtolowpenal.other.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActivityALArticle extends ActionBarActivity {

    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_al_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_drawer);
            //toolbar.setTitle("Title");
            //toolbar.setLogo(R.drawable.ic_launcher);
            CustomStatusBarDecorator.decorateStatusBar(this);
        }

        //For enabling up button -----------------------------------------------------------------
        ALArticle artice = (ALArticle) getIntent().getSerializableExtra("article");

        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(artice.getName());
        } catch (NullPointerException e) {
            Log.d(Constants.TAG_DYP_PENAL_LOG, "NullPointerException", e);
        }


        WebView wv = (WebView) findViewById(R.id.wvArticle);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.txt_size_middle);
        Log.i(Constants.TAG_DYP_PENAL_LOG, "Font size = " + fontSize);

        float scaledPixelSize = fontSize /dm.scaledDensity;
        Log.i(Constants.TAG_DYP_PENAL_LOG, "Scaled pix = " + scaledPixelSize);

        final WebSettings webSettings = wv.getSettings();
        webSettings.setDefaultFontSize((int)scaledPixelSize);

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBlockNetworkImage(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(false);

        wv.loadDataWithBaseURL("file:///android_asset/Maddeler/", artice.getDesc_url(), "text/html", "utf-8", null);


        //For AdMob--------------------------------------------------------------------------------
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(Constants.AD_BANNER_UNIT_ID);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.admobAA);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_about:
                Intent intAboutAct = new Intent(ActivityALArticle.this, ActivityAbout.class);
                startActivity(intAboutAct);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

}
