package com.blogspot.mowael.realtimecurrencyrates.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.adapters.DialogAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentEURAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentGBPAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentUSDAdapter;
import com.blogspot.mowael.realtimecurrencyrates.models.CurrencyModel;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnAuthor;
    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<CurrencyModel> currencyList;
    private RecyclerView rvBankEURDetailes, rvBankGBPDetailes, rvBankUSDDetailes;
    private RVContentEURAdapter eurAdapter;
    private RVContentGBPAdapter gbpAdapter;
    private RVContentUSDAdapter usdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencyList = new ArrayList<>();
        rvBankEURDetailes = (RecyclerView) findViewById(R.id.rvBankEURDetailes);
        rvBankGBPDetailes = (RecyclerView) findViewById(R.id.rvBankGBPDetailes);
        rvBankUSDDetailes = (RecyclerView) findViewById(R.id.rvBankUSDDetailes);
        rvBankEURDetailes.setLayoutManager(new LinearLayoutManager(this));
        rvBankGBPDetailes.setLayoutManager(new LinearLayoutManager(this));
        rvBankUSDDetailes.setLayoutManager(new LinearLayoutManager(this));
        btnAuthor = (Button) findViewById(R.id.tvAuthor);

        if (isConnectingToInternet(this)) {
            final Request request = new Request.Builder().url("https://api.curates.club/").build();
            new AsyncTask<String, Void, ArrayList<CurrencyModel>>() {


                @Override
                protected ArrayList<CurrencyModel> doInBackground(String... strings) {
                    try {
                        Response response = client.newCall(request).execute();
                        String jsonResponse = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            for (int i = 0; i < jsonObject.names().length(); i++) {
                                String bankNamk = jsonObject.names().get(i).toString();
                                JSONObject bankCurrencyRate = jsonObject.getJSONObject(bankNamk).getJSONObject("currency_rate");
                                CurrencyModel currencyModel = new CurrencyModel(bankNamk.toUpperCase(), jsonObject.getJSONObject(bankNamk).get("ref").toString(), jsonObject.getJSONObject(bankNamk).get("title").toString(),
                                        bankCurrencyRate.getJSONObject("eur").getDouble("sell"), bankCurrencyRate.getJSONObject("eur").getDouble("buy"),
                                        bankCurrencyRate.getJSONObject("gbp").getDouble("sell"), bankCurrencyRate.getJSONObject("gbp").getDouble("buy"),
                                        bankCurrencyRate.getJSONObject("usd").getDouble("sell"), bankCurrencyRate.getJSONObject("usd").getDouble("buy"));
                                currencyList.add(currencyModel);
                            }

                            Log.d("response", jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return currencyList;
                }

                @Override
                protected void onPostExecute(ArrayList<CurrencyModel> currencyModels) {
                    super.onPostExecute(currencyModels);
                    eurAdapter = new RVContentEURAdapter(MainActivity.this, currencyList);
                    gbpAdapter = new RVContentGBPAdapter(MainActivity.this, currencyList);
                    usdAdapter = new RVContentUSDAdapter(MainActivity.this, currencyList);
                    rvBankEURDetailes.setAdapter(eurAdapter);
                    rvBankGBPDetailes.setAdapter(gbpAdapter);
                    rvBankUSDDetailes.setAdapter(usdAdapter);

                }
            }.execute("");

        } else {
            Toast.makeText(this, "please! check the internet connection", Toast.LENGTH_SHORT).show();
        }

        btnAuthor.setOnClickListener(new View.OnClickListener() {
            Intent webIntent = new Intent(MainActivity.this, WebActivity.class);
            DialogAdapter adapter = new DialogAdapter(MainActivity.this, webIntent);

            @Override
            public void onClick(View view) {
                DialogPlus dialog = DialogPlus.newDialog(MainActivity.this)
                        .setAdapter(adapter)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            }
                        })
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();
            }
        });

    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
