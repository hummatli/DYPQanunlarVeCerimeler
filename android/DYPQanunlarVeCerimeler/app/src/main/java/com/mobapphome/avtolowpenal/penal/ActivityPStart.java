package com.mobapphome.avtolowpenal.penal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mobapphome.appcrosspromoter.ACPController;
import com.mobapphome.appcrosspromoter.tools.LocaleUpdater;
import com.mobapphome.avtolowpenal.ActivityAbout;
import com.mobapphome.avtolowpenal.CustomStatusBarDecorator;
import com.mobapphome.avtolowpenal.PrivacyPolicyFragment;
import com.mobapphome.avtolowpenal.R;
import com.mobapphome.avtolowpenal.SqlLiteHelper;
import com.mobapphome.avtolowpenal.SqlMethods;
import com.mobapphome.avtolowpenal.Utils;
import com.mobapphome.avtolowpenal.other.Constants;
import com.mobapphome.avtolowpenal.other.PParentArticle;
import com.mobapphome.avtolowpenal.other.PSubArticle;

import java.util.LinkedList;
import java.util.List;

public class ActivityPStart extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<PParentArticle> items = new LinkedList<>();
    ListView listView;


    AdView adView;

    SqlLiteHelper myDbHelper;
    InterstitialAd mInterstitialAd;

    ACPController mahAdsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penal_start);

        //Localization for mah
        LocaleUpdater.updateLocale(this, "az");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setNavigationIcon(R.drawable.ic_drawer);
            //toolbar.setTitle("Title");
            //toolbar.setLogo(R.drawable.ic_launcher);
            CustomStatusBarDecorator.decorateStatusBar(this);
        }


        listView = (ListView) findViewById(R.id.amListView);

        //For enabling up button -----------------------------------------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //For AdMob--------------------------------------------------------------------------------
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner_unit_id));

        final LinearLayout layout = (LinearLayout) findViewById(R.id.admobSSA);
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


        // For MAHAds init
        mahAdsController = ACPController.getInstance();
        mahAdsController.init(this,
                savedInstanceState,
                "https://project-943403214286171762.firebaseapp.com/mah_ads_dir/",
                "aze_gen_prg_version.json",
                "aze_gen_prg_list.json");
        // METHOD 1

        mInterstitialAd = Utils.requestNewInterstitial(this, mInterstitialAd);


        final ProgressDialog progressDialog = Utils.showProgressDlg(this);
        new AsyncTask<Void, Void, List<PParentArticle>>() {

            @Override
            protected List<PParentArticle> doInBackground(Void... voids) {
                return SqlMethods.readPParentArticles(Utils.getMyDbHelper(ActivityPStart.this, myDbHelper));
            }

            @Override
            protected void onPostExecute(final List<PParentArticle> items) {
                super.onPostExecute(items);

                Log.i("Test", "Items = count" + items.size());

                ActivityPStart.this.items = items;

                ItmPArticleAdpt adapter = new ItmPArticleAdpt(ActivityPStart.this, items);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


                        List<PSubArticle> arts = new LinkedList<>();
                        int pArtId = items.get(arg2).getId();

                        String nameItem = items.get(arg2).getName();
                        String name1 = "";
                        String name2 = "";
                        int index = nameItem.indexOf('|');
                        if (index > 0) {
                            name1 = nameItem.substring(0, index);
                            name2 = nameItem.substring(index + 1);
                        } else {
                            name2 = nameItem;
                        }

                        String pArtName = name1 + name2;


                        arts = SqlMethods.readPenalSubArts(Utils.getMyDbHelper(ActivityPStart.this, myDbHelper), pArtId);

                        PSubArticle[] subArtArray = new PSubArticle[arts.size()];
                        for (int i = 0; i < arts.size(); ++i) {
                            subArtArray[i] = arts.get(i);
                        }

                        Intent intSignAct = new Intent(ActivityPStart.this, ActivityPSubArt.class);
                        intSignAct.putExtra("mode", "simple");
                        intSignAct.putExtra("title", pArtName);
                        intSignAct.putExtra("subArtArray", subArtArray);
                        startActivity(intSignAct);
                        Utils.showInterstitial(ActivityPStart.this, mInterstitialAd, Constants.INTERSTITAIL_FREE_STEPS_MAIN, null);

                    }
                });
                Utils.closeProgressDlg(progressDialog);
            }
        }.execute();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mahAdsController.onSaveInstanceState(outState);
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
        getMenuInflater().inflate(R.menu.sign_start, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    MenuItemCompat.collapseActionView(searchItem);
                    searchView.setQuery("", false);
                }
            }
        });


        EditText et = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        et.setTextColor(getResources().getColor(R.color.action_bar_menu_text_color));
        et.setHintTextColor(getResources().getColor(R.color.action_bar_menu_hint_text_color));
        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.i("Test", "expand---");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.i("Test", "collapse");
                return true;
            }
        });

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
                Intent intAboutAct = new Intent(ActivityPStart.this, ActivityAbout.class);
                startActivity(intAboutAct);
                return true;
            case R.id.action_privacy_policy:
                showDlgFragment(PrivacyPolicyFragment.newInstance("", ""), "PrivacyPolicyFragment");
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_mahads:
                mahAdsController.callProgramsDialog(this);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showDlgFragment(Fragment frag, String fragTag) {

        if (!isFinishing()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fr = fragmentManager.findFragmentByTag(fragTag);
            if (fr != null && !fr.isHidden()) {
                ((DialogFragment)fr).dismissAllowingStateLoss();
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frag, fragTag);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Do something
        Log.i("Test", "Change---------!!");
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intSignAct = new Intent(ActivityPStart.this, ActivityPSubArt.class);
        intSignAct.putExtra("mode", "search");
        intSignAct.putExtra("query", query);
        startActivity(intSignAct);
        Utils.showInterstitial(ActivityPStart.this, mInterstitialAd, Constants.INTERSTITAIL_FREE_STEPS_MAIN, null);
        return true;
    }


}
