package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.models.CurrencyModel;
import com.blogspot.mowael.realtimecurrencyrates.utilites.ChangeCurrencyListner;

import java.util.ArrayList;

/**
 * Created by moham on 12/3/2016.
 */

public abstract class RVContentAdapter extends RecyclerView.Adapter<RVContentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CurrencyModel> currencyList;
    protected ChangeCurrencyListner changeCurrencyListner;

    public RVContentAdapter(Context context, ArrayList<CurrencyModel> currencyList) {
        this.context = context;
        this.currencyList = currencyList;
    }

    @Override
    public RVContentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_bank, parent, false);
        return new RVContentAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
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

    public void setChangeCurrencyListner(ChangeCurrencyListner changeCurrencyListner) {
        this.changeCurrencyListner = changeCurrencyListner;
    }
}
