package com.mobapphome.avtolowpenal.avtolow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobapphome.avtolowpenal.ActivityAbout;
import com.mobapphome.avtolowpenal.CustomStatusBarDecorator;
import com.mobapphome.avtolowpenal.R;
import com.mobapphome.avtolowpenal.SqlLiteHelper;
import com.mobapphome.avtolowpenal.SqlMethods;
import com.mobapphome.avtolowpenal.Utils;
import com.mobapphome.avtolowpenal.other.ALChapterArticle;
import com.mobapphome.avtolowpenal.other.Constants;

import java.util.LinkedList;
import java.util.List;

public class ActivityALSearchRes extends ActionBarActivity {

    ListView listView;
    List<ALChapterArticle> items = new LinkedList<>();

    AdView adView;

    SqlLiteHelper myDbHelper;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_drawer);
            //toolbar.setTitle("Title");
            //toolbar.setLogo(R.drawable.ic_launcher);
            CustomStatusBarDecorator.decorateStatusBar(this);
        }


        listView = (ListView) findViewById(R.id.asListView);

        //For enabling up button -----------------------------------------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon(R.drawable.ic_action_bar_icon);


        //For AdMob--------------------------------------------------------------------------------
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(Constants.AD_BANNER_UNIT_ID);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.admobSA);
        layout.addView(adView);
        layout.setVisibility(View.GONE);

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
                layout.setVisibility(View.VISIBLE);
            }
        });


        mInterstitialAd = Utils.requestNewInterstitial(this, mInterstitialAd);

        final ProgressDialog progressDialog = Utils.showProgressDlg(this);

        //For questions-------------------------------------------------

        String queryStr = getIntent().getStringExtra("query");
        actionBar.setTitle(queryStr);

        new AsyncTask<String, Void, List<ALChapterArticle>>() {

            @Override
            protected List<ALChapterArticle> doInBackground(String... voids) {

                return SqlMethods.searchALChapterArticles(Utils.getMyDbHelper(ActivityALSearchRes.this, myDbHelper), voids[0]);
            }

            @Override
            protected void onPostExecute(final List<ALChapterArticle> items) {
                super.onPostExecute(items);

                ActivityALSearchRes.this.items = items;


                ItmALChapterArticleAdpt adapter = new ItmALChapterArticleAdpt(ActivityALSearchRes.this, items);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Intent intSignAct = new Intent(ActivityALSearchRes.this, ActivityALArticle.class);
                        ALChapterArticle ca = items.get(arg2);
                        if (ca.getArticle() != null) {
                            intSignAct.putExtra("article", ca.getArticle());
                            startActivity(intSignAct);
                            Utils.showInterstitial(ActivityALSearchRes.this, mInterstitialAd, Constants.INTERSTITAIL_FREE_STEPS_MAIN, null);
                        }
                    }
                });

                Log.i("Test", "categories size =  " + items.size());
                Utils.closeProgressDlg(progressDialog);
            }
        }.execute(queryStr);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }

        Utils.closeMyDbHelper(myDbHelper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign, menu);
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
                Intent intAboutAct = new Intent(ActivityALSearchRes.this, ActivityAbout.class);
                startActivity(intAboutAct);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
