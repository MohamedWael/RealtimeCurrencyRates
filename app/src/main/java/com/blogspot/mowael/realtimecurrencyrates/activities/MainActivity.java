package com.blogspot.mowael.realtimecurrencyrates.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.adapters.DialogAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.DotLoadlerDialogAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentEURAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentGBPAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentSARAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentUSDAdapter;
import com.blogspot.mowael.realtimecurrencyrates.fragments.CurrenciesFragment;
import com.blogspot.mowael.realtimecurrencyrates.models.CurrencyModel;
import com.blogspot.mowael.realtimecurrencyrates.utilites.NetworkStateReceiver;
import com.orhanobut.dialogplus.DialogPlus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrenciesFragment currenciesFragment = CurrenciesFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.flCurrenciesFragment, currenciesFragment).commit();



    }

}

