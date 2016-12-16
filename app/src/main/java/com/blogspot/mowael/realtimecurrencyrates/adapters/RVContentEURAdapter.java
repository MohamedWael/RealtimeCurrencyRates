package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.blogspot.mowael.realtimecurrencyrates.models.CurrencyModel;

import java.util.ArrayList;

/**
 * Created by moham on 11/18/2016.
 */

public class RVContentEURAdapter extends RVContentAdapter {

    private Context context;
    private ArrayList<CurrencyModel> currencyList;


    public RVContentEURAdapter(Context context, ArrayList<CurrencyModel> currencyList) {
        super(context, currencyList);
        this.context = context;
        this.currencyList = currencyList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CurrencyModel currencyModel = currencyList.get(position);
        holder.btnBankValue.setText(currencyModel.getBankName());
        holder.btnSellvalue.setText(currencyModel.getEurSell() + "");
        holder.btnBuyValue.setText(currencyModel.getEurBuy() + "");
        changeCurrencyListner.onChangeCurrencyListener(currencyModel.getEurSell(), currencyModel.getEurBuy(), holder.btnSellvalue, holder.btnBuyValue);

        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currencyModel.getRef()));
//        webIntent = new Intent(context, WebActivity.class);
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
