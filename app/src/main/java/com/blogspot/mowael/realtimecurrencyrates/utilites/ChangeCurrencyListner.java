package com.blogspot.mowael.realtimecurrencyrates.utilites;

import android.widget.Button;

/**
 * Created by moham on 12/16/2016.
 */

public interface ChangeCurrencyListner {
    public void onChangeCurrencyListener(double currencySell, double currencyBuy, Button btnSellvalue, Button btnBuyValue);
}
