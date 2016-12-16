package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by moham on 11/18/2016.
 */

public class RVContentSARAdapter extends RVContentAdapter {


    private Context context;
    private ArrayList<CurrencyModel> currencyList;
    private Intent webIntent;


    public RVContentSARAdapter(Context context, ArrayList<CurrencyModel> currencyList) {
        super(context, currencyList);
        this.context = context;
        this.currencyList = currencyList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CurrencyModel currencyModel = currencyList.get(position);
        holder.btnBankValue.setText(currencyModel.getBankName());
        holder.btnSellvalue.setText(currencyModel.getSarSell() + "");
        holder.btnBuyValue.setText(currencyModel.getSarBuy() + "");
        changeCurrencyListner.onChangeCurrencyListener(currencyModel.getSarSell(), currencyModel.getSarBuy(), holder.btnSellvalue, holder.btnBuyValue);
//        webIntent = new Intent(context, WebActivity.class);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currencyModel.getRef()));
        holder.btnBankValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet(context)) {
//                    webIntent.putExtra("link", currencyModel.getRef());
                    context.startActivity(browserIntent);
                } else {
                    Toast.makeText(context, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
