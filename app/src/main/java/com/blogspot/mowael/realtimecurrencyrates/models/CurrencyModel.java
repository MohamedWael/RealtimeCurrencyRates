package com.blogspot.mowael.realtimecurrencyrates.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moham on 11/18/2016.
 */

public class CurrencyModel  {


    private String bankName;

    private String ref;

    private String title;

    private double eurSell;

    private double eurBuy;

    private double gbpSell;

    private double gbpBuy;

    private double usdSell;

    private double usdBuy;


    public CurrencyModel(String bankName, String ref, String title,
                         double eurSell, double eurBuy,
                         double gbpSell, double gbpBuy,
                         double usdSell, double usdBuy) {
        this.bankName = bankName;
        this.ref = ref;
        this.title = title;
        this.eurSell = eurSell;
        this.eurBuy = eurBuy;
        this.gbpSell = gbpSell;
        this.gbpBuy = gbpBuy;
        this.usdSell = usdSell;
        this.usdBuy = usdBuy;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getEurSell() {
        return eurSell;
    }

    public void setEurSell(double eurSell) {
        this.eurSell = eurSell;
    }

    public double getEurBuy() {
        return eurBuy;
    }

    public void setEurBuy(double eurBuy) {
        this.eurBuy = eurBuy;
    }

    public double getGbpSell() {
        return gbpSell;
    }

    public void setGbpSell(double gbpSell) {
        this.gbpSell = gbpSell;
    }

    public double getGbpBuy() {
        return gbpBuy;
    }

    public void setGbpBuy(double gbpBuy) {
        this.gbpBuy = gbpBuy;
    }

    public double getUsdSell() {
        return usdSell;
    }

    public void setUsdSell(double usdSell) {
        this.usdSell = usdSell;
    }

    public double getUsdBuy() {
        return usdBuy;
    }

    public void setUsdBuy(double usdBuy) {
        this.usdBuy = usdBuy;
    }

}
