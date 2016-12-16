package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.activities.WebActivity;
import com.blogspot.mowael.realtimecurrencyrates.models.CurrencyModel;

import java.util.ArrayList;

/**
 * Created by moham on 11/19/2016.
 */

public class RVContentUSDAdapter extends RVContentAdapter {


    private ArrayList<CurrencyModel> currencyList;
    private Context mContext;
    private Intent webIntent;

    public RVContentUSDAdapter(Context context, ArrayList<CurrencyModel> currencyList) {
        super(context, currencyList);
        this.currencyList = currencyList;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CurrencyModel currencyModel = currencyList.get(position);
        holder.btnBankValue.setText(currencyModel.getBankName());
        holder.btnBuyValue.setText(currencyModel.getUsdBuy() + "");
        holder.btnSellvalue.setText(currencyModel.getUsdSell() + "");

        changeCurrencyListner.onChangeCurrencyListener(currencyModel.getUsdSell(), currencyModel.getUsdBuy(), holder.btnSellvalue, holder.btnBuyValue);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currencyModel.getRef()));
//        webIntent = new Intent(mContext, WebActivity.class);
        holder.btnBankValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet(mContext)) {
//                    webIntent.putExtra("link", currencyModel.getRef());
                    mContext.startActivity(browserIntent);
                } else {
                    Toast.makeText(mContext, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
