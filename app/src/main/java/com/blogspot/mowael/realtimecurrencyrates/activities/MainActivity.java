package com.blogspot.mowael.realtimecurrencyrates.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.fragments.CurrenciesFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        CurrenciesFragment currenciesFragment = CurrenciesFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.flCurrenciesFragment, currenciesFragment).commit();
    }

}

