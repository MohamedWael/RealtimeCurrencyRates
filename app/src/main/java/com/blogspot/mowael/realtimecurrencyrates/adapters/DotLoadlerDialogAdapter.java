package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bhargavms.dotloader.DotLoader;
import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.activities.ContactUsActivity;

/**
 * Created by moham on 11/20/2016.
 */

public class DotLoadlerDialogAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public DotLoadlerDialogAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.dot_loader_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.dotLoader = (DotLoader) view.findViewById(R.id.text_dot_loader);
        return view;
    }

    static class ViewHolder {
        DotLoader dotLoader;
    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
