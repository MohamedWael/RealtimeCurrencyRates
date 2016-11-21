package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class RVContentSARAdapter extends RecyclerView.Adapter<RVContentSARAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<CurrencyModel> currencyList;
    private Intent webIntent;


    public RVContentSARAdapter(Context context, ArrayList<CurrencyModel> currencyList) {
        this.context = context;
        this.currencyList = currencyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_bank, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CurrencyModel currencyModel = currencyList.get(position);
        holder.btnBankValue.setText(currencyModel.getBankName());
        holder.btnSellvalue.setText(currencyModel.getSarSell() + "");
        holder.btnBuyValue.setText(currencyModel.getSarBuy() + "");

        webIntent = new Intent(context, WebActivity.class);
        holder.btnBankValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet(context)) {

                    webIntent.putExtra("link", currencyModel.getRef());
                    context.startActivity(webIntent);
                } else {
                    Toast.makeText(context, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("currencyList.size000", currencyList.size() + "");
        return currencyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public Button btnBankValue, btnSellvalue, btnBuyValue;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnBankValue = (Button) itemView.findViewById(R.id.btnBankValue);
            btnSellvalue = (Button) itemView.findViewById(R.id.btnSellvalue);
            btnBuyValue = (Button) itemView.findViewById(R.id.btnBuyValue);
        }
    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
