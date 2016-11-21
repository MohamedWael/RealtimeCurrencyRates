package com.blogspot.mowael.realtimecurrencyrates.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.mowael.realtimecurrencyrates.activities.ContactUsActivity;
import com.blogspot.mowael.realtimecurrencyrates.R;

/**
 * Created by moham on 11/20/2016.
 */

public class DialogAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private Intent intent;

    public DialogAdapter(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
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
        view = layoutInflater.inflate(R.layout.dialog_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.btnRESTfulAPI = (Button) view.findViewById(R.id.btnRESTfulAPI);
        viewHolder.btnAndroidApp = (Button) view.findViewById(R.id.btnAndroidApp);

        viewHolder.btnRESTfulAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet(context)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.alash3al.xyz/"));
//                    intent.putExtra("link", "https://www.alash3al.xyz/");
                    context.startActivity(browserIntent);
                } else {
                    Toast.makeText(context, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.btnAndroidApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ContactUsActivity.class));
            }
        });
        return view;
    }

    static class ViewHolder {
        Button btnRESTfulAPI, btnAndroidApp;
    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
