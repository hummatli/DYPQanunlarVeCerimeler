package com.mobapphome.avtolowpenal.penal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mobapphome.avtolowpenal.ActivityAbout;
import com.mobapphome.avtolowpenal.CustomStatusBarDecorator;
import com.mobapphome.avtolowpenal.R;
import com.mobapphome.avtolowpenal.other.PSubArticle;

public class ActivityPSubArtInfo extends AppCompatActivity {


    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psub_art_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_drawer);
            //toolbar.setTitle("Title");
            //toolbar.setLogo(R.drawable.ic_launcher);
            CustomStatusBarDecorator.decorateStatusBar(this);
        }

        //For enabling up button -----------------------------------------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon(R.drawable.ic_action_bar_icon);

        PSubArticle art = (PSubArticle) getIntent().getSerializableExtra("article");

        TextView tvName = (TextView) findViewById(R.id.tvActPSubArtInfoName);
        TextView tvBal = (TextView) findViewById(R.id.tvActPSubArtInfoPenalBal);

        StringBuffer descStr = new StringBuffer();
        String[] descParts = art.getDesc().split("\n");
        for (int i = 0; i < descParts.length; ++i) {
            if (i > 0) {
                descStr.append("<br>");
            }
            descStr.append(descParts[i]);
        }

        tvName.setText(Html.fromHtml(
                "<span><font color=\"#046EF7\">Məlumat: </font></span><br>"
                        + "<span><font color=\"#046EF7\">" + art.getName() + "</font></span>"
                        + "<span>" + descStr.toString() + "</span>"));
        tvBal.setText(Html.fromHtml("<span><font color=\"red\"> Tənbeh: <br>" +
                art.toString() + "</font></span>"));

        actionBar.setTitle(art.getName());

        //For AdMob--------------------------------------------------------------------------------
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner_unit_id));

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
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.psub_art_info, menu);
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
                Intent intAboutAct = new Intent(ActivityPSubArtInfo.this, ActivityAbout.class);
                startActivity(intAboutAct);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
