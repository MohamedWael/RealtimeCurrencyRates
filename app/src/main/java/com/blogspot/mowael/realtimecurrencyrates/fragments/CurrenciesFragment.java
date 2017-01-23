package com.blogspot.mowael.realtimecurrencyrates.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.mowael.realtimecurrencyrates.R;
import com.blogspot.mowael.realtimecurrencyrates.activities.WebActivity;
import com.blogspot.mowael.realtimecurrencyrates.adapters.DialogAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.DotLoadlerDialogAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentEURAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentGBPAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentSARAdapter;
import com.blogspot.mowael.realtimecurrencyrates.adapters.RVContentUSDAdapter;
import com.blogspot.mowael.realtimecurrencyrates.models.CurrencyModel;
import com.blogspot.mowael.realtimecurrencyrates.utilites.ChangeCurrencyListner;
import com.blogspot.mowael.realtimecurrencyrates.utilites.NetworkStateReceiver;
import com.orhanobut.dialogplus.DialogPlus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CurrenciesFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener, ChangeCurrencyListner {

    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<CurrencyModel> currencyList;
    private RecyclerView rvBankEURDetailes, rvBankGBPDetailes, rvBankUSDDetailes;
    private RVContentEURAdapter eurAdapter;
    private RVContentGBPAdapter gbpAdapter;
    private RVContentUSDAdapter usdAdapter;
    private RVContentSARAdapter sarAdapter;
    private DialogPlus dotLoaderDialog;
    private RecyclerView rvBankSARDetailes;
    private NetworkStateReceiver networkStateReceiver;
    private EditText etChangeCurrency;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnAuthor;
    private Request request;
    private Response response;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CurrenciesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrenciesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrenciesFragment newInstance(String param1, String param2) {
        CurrenciesFragment fragment = new CurrenciesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currencies, container, false);
        // Control whether a fragment instance is retained across Activity re-creation
        // (such as from a configuration change). This can only be used with fragments not in the back stack.
        setRetainInstance(true);
        currencyList = new ArrayList<>();
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        getAndPopulateData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initResources();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                currencyList.clear();
                requestData();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void getAndPopulateData() {
        if (isConnectingToInternet(getContext())) {
            getActivity().unregisterReceiver(networkStateReceiver);
            requestData();
        } else {
            getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void requestData() {
        request = new Request.Builder().url("https://api.curates.club/").build();
        new AsyncTask<String, Void, ArrayList<CurrencyModel>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                DotLoadlerDialogAdapter dotLoaderAdapter = new DotLoadlerDialogAdapter(getActivity());
                dotLoaderDialog = DialogPlus.newDialog(getActivity())
                        .setAdapter(dotLoaderAdapter).setExpanded(false).setGravity(Gravity.CENTER)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dotLoaderDialog.show();
            }

            @Override
            protected ArrayList<CurrencyModel> doInBackground(String... strings) {
                try {
                    response = client.newCall(request).execute();
                    String jsonResponse = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    for (int i = 0; i < jsonObject.names().length(); i++) {
                        String bankNamk = jsonObject.names().get(i).toString();
                        JSONObject bankCurrencyRate = jsonObject.getJSONObject(bankNamk).getJSONObject("currency_rate");
                        CurrencyModel currencyModel = new CurrencyModel(bankNamk.toUpperCase(), jsonObject.getJSONObject(bankNamk).get("ref").toString(), jsonObject.getJSONObject(bankNamk).get("title").toString(),
                                bankCurrencyRate.getJSONObject("eur").getDouble("sell"), bankCurrencyRate.getJSONObject("eur").getDouble("buy"),
                                bankCurrencyRate.getJSONObject("gbp").getDouble("sell"), bankCurrencyRate.getJSONObject("gbp").getDouble("buy"),
                                bankCurrencyRate.getJSONObject("usd").getDouble("sell"), bankCurrencyRate.getJSONObject("usd").getDouble("buy"),
                                bankCurrencyRate.getJSONObject("sar").getDouble("sell"), bankCurrencyRate.getJSONObject("sar").getDouble("buy"));
                        currencyList.add(currencyModel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return currencyList;
            }

            @Override
            protected void onPostExecute(ArrayList<CurrencyModel> currencyModels) {
                super.onPostExecute(currencyModels);
                dotLoaderDialog.dismiss();
                eurAdapter = new RVContentEURAdapter(getActivity(), currencyList);
                gbpAdapter = new RVContentGBPAdapter(getActivity(), currencyList);
                usdAdapter = new RVContentUSDAdapter(getActivity(), currencyList);
                sarAdapter = new RVContentSARAdapter(getActivity(), currencyList);
                pluginCurrencyChangeListener();
                rvBankEURDetailes.setAdapter(eurAdapter);
                rvBankGBPDetailes.setAdapter(gbpAdapter);
                rvBankUSDDetailes.setAdapter(usdAdapter);
                rvBankSARDetailes.setAdapter(sarAdapter);

            }
        }.execute("");
    }

    private boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void networkAvailable() {
        getAndPopulateData();
    }

    @Override
    public void networkUnavailable() {
        toastMsg("please! make sure that you are connected to the internet");
    }

    private void initResources() {
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
        etChangeCurrency = (EditText) getActivity().findViewById(R.id.etChangeCurrency);
        rvBankEURDetailes = (RecyclerView) getActivity().findViewById(R.id.rvBankEURDetailes);
        rvBankGBPDetailes = (RecyclerView) getActivity().findViewById(R.id.rvBankGBPDetailes);
        rvBankUSDDetailes = (RecyclerView) getActivity().findViewById(R.id.rvBankUSDDetailes);
        rvBankSARDetailes = (RecyclerView) getActivity().findViewById(R.id.rvBankSARDetailes);
        rvBankEURDetailes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBankGBPDetailes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBankUSDDetailes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBankSARDetailes.setLayoutManager(new LinearLayoutManager(getContext()));
        btnAuthor = (Button) getActivity().findViewById(R.id.tvAuthor);
        btnAuthor.setOnClickListener(new View.OnClickListener() {
            Intent webIntent = new Intent(getContext(), WebActivity.class);
            DialogAdapter adapter = new DialogAdapter(getContext(), webIntent);

            @Override
            public void onClick(View view) {
                DialogPlus dialog = DialogPlus.newDialog(getContext())
                        .setAdapter(adapter).setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();
            }
        });
    }

    private void pluginCurrencyChangeListener() {
        eurAdapter.setChangeCurrencyListner(CurrenciesFragment.this);
        gbpAdapter.setChangeCurrencyListner(CurrenciesFragment.this);
        usdAdapter.setChangeCurrencyListner(CurrenciesFragment.this);
        sarAdapter.setChangeCurrencyListner(CurrenciesFragment.this);
    }

    @Override
    public void onChangeCurrencyListener(final double currencySell, final double currencyBuy, final Button btnSellvalue, final Button btnBuyValue) {
        etChangeCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!etChangeCurrency.getText().toString().equals("")) {
                        double amount = Double.parseDouble(etChangeCurrency.getText().toString());
                        DecimalFormat format = new DecimalFormat("0.00");
                        btnSellvalue.setText(format.format(currencySell * amount).toString());
                        btnBuyValue.setText(format.format(currencyBuy * amount).toString());
                    } else {
                        btnSellvalue.setText(currencySell * 1 + "");
                        btnBuyValue.setText(currencySell * 1 + "");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!etChangeCurrency.getText().toString().equals("")) {
                        double amount = Double.parseDouble(etChangeCurrency.getText().toString());
                        DecimalFormat format = new DecimalFormat("0.00");
                        btnSellvalue.setText(format.format(currencySell * amount).toString());
                        btnBuyValue.setText(format.format(currencyBuy * amount).toString());
                    } else {
                        btnSellvalue.setText(currencySell * 1 + "");
                        btnBuyValue.setText(currencySell * 1 + "");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isConnectingToInternet(getContext())) {
                    toastMsg("please! make sure that you are connected to the internet");
                }
            }
        });
    }

    private void toastMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
